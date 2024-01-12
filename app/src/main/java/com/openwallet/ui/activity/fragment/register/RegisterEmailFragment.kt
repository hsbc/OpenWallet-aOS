package com.openwallet.ui.activity.fragment.register

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import com.openwallet.R
import com.openwallet.app.OpenWalletApplication
import com.openwallet.constants.PARAMS_REGISTER_EMAIL
import com.openwallet.constants.PARAMS_REGISTER_NAME
import com.openwallet.ext.*
import com.openwallet.ui.activity.fragment.base.BaseEmailFragment
import com.openwallet.ui.activity.fragment.sms.model.CaptchaScenario
import com.openwallet.ui.activity.fragment.sms.model.CaptchaType
import com.openwallet.ui.activity.fragment.sms.model.SmsRequest
import com.openwallet.ui.activity.fragment.sms.model.SmsViewModel
import com.openwallet.util.CommonUtils

class RegisterEmailFragment : BaseEmailFragment() {

    private lateinit var registerUsername: String
    private val smsViewModel by viewModelFragment<SmsViewModel>()

    override fun getParameters() {
        arguments?.let {
            registerUsername = it.getString(PARAMS_REGISTER_NAME).orEmpty()
        }
    }

    override fun initViewAndData(view: View, savedInstanceStat: Bundle?) {
        super.initViewAndData(view, savedInstanceStat)
        initRegistrationView()

    }

    private fun initRegistrationView() {
        binding.tvLoggedInEmailHint.gone()
        binding.tvMaskedEmail.gone()

        binding.tvEmailHint.visible()
        binding.sifEmail.visible()
        binding.sifEmail.text = OpenWalletApplication.appViewModel.userInput?.email.orEmpty()
        binding.btnVerifyEmail.isEnabled = binding.sifEmail.text.contains("@")

        binding.sifEmail.apply {
            doAfterTextChanged {
                binding.btnVerifyEmail.isEnabled = it.toString().contains("@")
                OpenWalletApplication.appViewModel.userInput?.email = it.toString()
            }
        }
    }

    override fun initToolBar() {
        binding.toolbar.init(
            title = getTitle(),
            isShowBack = true,
            isShowCancel = true,
            onCancelClickListener = {
                nav().safeNavigate(R.id.startRegisterFragment)
                CommonUtils.clearInput()
            }
        )
    }

    override fun getTitle(): String = getString(R.string.login_register)

    override fun getStepBar() {

        binding.stepBar.apply {
            stepCompleted = 3
            stepMax = 4
            title = String.format(
                getString(R.string.register_step_title_format), stepCompleted, stepMax,
                getString(R.string.register_step3_email)
            )
        }
    }

    override fun submit() {
        //sendEmailSms()
        isComplete = true
        binding.btnVerifyEmail.state = PrimaryButton.State.SUCCESS
    }

    override fun submitFail(errorMessage: String?) {
    }

    override fun navigate() {
        nav().navigateAction(
            R.id.action_RegisterEmailFragment_to_RegisterEmailSmsFragment,
            bundleOf(PARAMS_REGISTER_NAME to registerUsername, PARAMS_REGISTER_EMAIL to binding.sifEmail.text)
        )
    }

    override fun getButtonText(): String = getString(R.string.sendCode)

    private fun sendEmailSms() {
        val emailSmsRequest = SmsRequest(
            username = registerUsername,
            emailAddress = binding.sifEmail.text,
            token = OpenWalletApplication.appViewModel.secretToken.value,
            captchaScenarioEnum = CaptchaScenario.REGISTER,
            captchaTypeEnum = CaptchaType.MAIL_VERIFY
        )
        smsViewModel.sendSms(emailSmsRequest).observe(this) {
            parseState(
                it,
                onLoading = {
                    binding.sifEmail.state = StandardInputField.State.INPUT
                    binding.sifEmail.clearInlineMessageView()
                    binding.btnVerifyEmail.state = PrimaryButton.State.LOADING
                },
                onSuccess = {
                    isComplete = true
                    binding.btnVerifyEmail.state = PrimaryButton.State.SUCCESS
                },
                onInnerError = {
                    isComplete = true
                    binding.btnVerifyEmail.state = PrimaryButton.State.SUCCESS
                },
                onNetworkOrExceptionError = {
                    CommonUtils.showSystemError(binding.content)
                    binding.btnVerifyEmail.state = PrimaryButton.State.ENABLED
                }
            )
        }
    }

}

