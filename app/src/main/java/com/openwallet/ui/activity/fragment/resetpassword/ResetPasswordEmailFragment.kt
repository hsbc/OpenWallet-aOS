package com.openwallet.ui.activity.fragment.resetpassword

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import com.openwallet.R
import com.openwallet.app.OpenWalletApplication.Companion.appViewModel
import com.openwallet.constants.PARAMS_RESET_PASSWORD_EMAIL
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

class ResetPasswordEmailFragment : BaseEmailFragment() {

    private val smsViewModel by viewModelFragment<SmsViewModel>()
    private var emailAddress: String = ""
        get() = if (CommonUtils.isLogin()) appViewModel.profileInfo.value?.emailAddress.orEmpty() else binding.sifEmail.text


    override fun initViewAndData(view: View, savedInstanceStat: Bundle?) {
        super.initViewAndData(view, savedInstanceStat)

        if (CommonUtils.isLogin()) initResetPasswordViewWithLoginUser() else initResetPasswordViewWithLogoutUser()
    }

    private fun initResetPasswordViewWithLogoutUser(){
        binding.tvLoggedInEmailHint.gone()
        binding.tvMaskedEmail.gone()
        binding.tvEmailHint.gone()

        binding.sifEmail.visible()
        binding.sifEmail.text = appViewModel.userInput?.email.orEmpty()
        binding.btnVerifyEmail.isEnabled = binding.sifEmail.text.contains("@")

        binding.sifEmail.apply {
            doAfterTextChanged {
                binding.btnVerifyEmail.isEnabled = it.toString().contains("@")
                appViewModel.userInput?.email = it.toString()
            }
        }
    }

    private fun initResetPasswordViewWithLoginUser(){
        binding.tvLoggedInEmailHint.visible()
        binding.tvMaskedEmail.visible()
        binding.tvMaskedEmail.text =
            CommonUtils.maskEmailaddress(appViewModel.profileInfo.value?.emailAddress.orEmpty())
        binding.sifEmail.gone()
        binding.tvEmailHint.gone()
        binding.btnVerifyEmail.isEnabled = true
    }

    override fun initToolBar() {
        binding.toolbar.init(
            title = getTitle(),
            isShowBack = true, onBackClickListener = {
                CommonUtils.clearInput()
                nav().navigateUp()
            }
        )
    }

    override fun getParameters() {
    }

    override fun getTitle() = getString(R.string.reset_password_title)

    override fun getStepBar() {
        binding.stepBar.apply {
            stepCompleted = 1
            stepMax = 3
            title = String.format(
                getString(R.string.register_step_title_format), stepCompleted, stepMax,
                getString(R.string.reset_password_step1_email)
            )
        }
    }

    override fun submit() {
//        sendEmailSms()
        isComplete = true
        binding.btnVerifyEmail.state = PrimaryButton.State.SUCCESS
    }

    override fun submitFail(errorMessage: String?) {
    }

    override fun navigate() {
        nav().navigate(
            R.id.action_ResetPasswordEmailFragment_to_resetPasswordEmailSms, bundleOf(
                PARAMS_RESET_PASSWORD_EMAIL to emailAddress
            )
        )
    }

    override fun getButtonText() = getString(R.string.next)

    private fun sendEmailSms() {
        val emailSmsRequest = SmsRequest(
            emailAddress = emailAddress,
            captchaScenarioEnum = CaptchaScenario.RESET_PASSWORD,
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
                    if (it.status == true) {
                        //todo assgin secret token
                        appViewModel.secretToken.value = it.data?.token.orEmpty()
                        isComplete = true
                        binding.btnVerifyEmail.state = PrimaryButton.State.SUCCESS
                    } else {
                        binding.sifEmail.errorMessage = it.message.orEmpty()
                        binding.sifEmail.state = StandardInputField.State.ERROR
                        binding.btnVerifyEmail.state = PrimaryButton.State.ENABLED
                    }
                },
                onError = {
                    binding.sifEmail.errorMessage = it.errorMessage.orEmpty()
                    binding.sifEmail.state = StandardInputField.State.ERROR
                    binding.btnVerifyEmail.state = PrimaryButton.State.ENABLED
                },
            )
        }
    }


}