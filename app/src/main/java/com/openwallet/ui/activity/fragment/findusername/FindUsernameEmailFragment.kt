package com.openwallet.ui.activity.fragment.findusername

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import com.openwallet.R
import com.openwallet.app.OpenWalletApplication
import com.openwallet.constants.PARAMS_FIND_NAME_EMAIL
import com.openwallet.ext.gone
import com.openwallet.ext.nav
import com.openwallet.ext.parseState
import com.openwallet.ext.visible
import com.openwallet.ui.activity.fragment.base.BaseEmailFragment
import com.openwallet.ui.activity.fragment.sms.model.CaptchaScenario
import com.openwallet.ui.activity.fragment.sms.model.CaptchaType
import com.openwallet.ui.activity.fragment.sms.model.SmsRequest
import com.openwallet.ui.activity.fragment.sms.model.SmsViewModel
import com.openwallet.util.CommonUtils

class FindUsernameEmailFragment : BaseEmailFragment() {

    private val smsViewModel by viewModelFragment<SmsViewModel>()

    override fun getParameters() {
    }

    override fun initViewAndData(view: View, savedInstanceStat: Bundle?) {
        super.initViewAndData(view, savedInstanceStat)
        initFindMyUserNameView()
    }

    private fun initFindMyUserNameView() {
        binding.tvLoggedInEmailHint.gone()
        binding.tvMaskedEmail.gone()
        binding.tvEmailHint.gone()

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

    override fun getTitle(): String = getString(R.string.find_username_title)

    override fun getStepBar() {
        binding.stepBar.apply {
            stepCompleted = 1
            stepMax = 2
            title = String.format(
                getString(R.string.register_step_title_format), stepCompleted, stepMax,
                getString(R.string.find_username_step1_email)
            )
        }
    }

    override fun submit() {
        isComplete = true
        binding.btnVerifyEmail.state = PrimaryButton.State.SUCCESS
    }

    override fun submitFail(errorMessage: String?) {
    }

    override fun navigate() {
        nav().navigate(
            R.id.action_FindUsernameEmailFragment_to_FindUsernameEmailSmsFragment, bundleOf(
                PARAMS_FIND_NAME_EMAIL to binding.sifEmail.text
            )
        )
    }

    override fun getButtonText(): String = getString(R.string.sendCode)

    override fun initToolBar() {
        binding.toolbar.init(
            isShowBack = true,
            onBackClickListener = {
                nav().navigateUp()
                CommonUtils.clearInput()
            },
            title = getTitle()
        )
    }

    private fun sendEmailSms() {
        val emailSmsRequest = SmsRequest(
            emailAddress = binding.sifEmail.text,
            captchaScenarioEnum = CaptchaScenario.FORGOT_USERNAME,
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
                    OpenWalletApplication.appViewModel.secretToken.value = it.data?.token.orEmpty()
                    isComplete = true
                    binding.btnVerifyEmail.state = PrimaryButton.State.SUCCESS

                },
                onInnerError = {
                    binding.sifEmail.state = StandardInputField.State.ERROR
                    binding.sifEmail.errorMessage = it.errorMessage.orEmpty()
                    binding.btnVerifyEmail.state = PrimaryButton.State.ENABLED
                },
                onNetworkOrExceptionError = {
                    CommonUtils.showSystemError(binding.content)
                    binding.btnVerifyEmail.state = PrimaryButton.State.ENABLED
                }
            )
        }
    }
}