package com.openwallet.ui.activity.fragment.resetpassword

import androidx.navigation.NavOptions
import com.openwallet.manager.VerificationCodeLockManager
import com.openwallet.R
import com.openwallet.app.OpenWalletApplication
import com.openwallet.constants.PARAMS_RESET_PASSWORD_EMAIL
import com.openwallet.ext.gone
import com.openwallet.ext.nav
import com.openwallet.ext.parseState
import com.openwallet.ext.visible
import com.openwallet.ui.activity.fragment.base.BaseEmailSmsFragment
import com.openwallet.ui.activity.fragment.sms.model.CaptchaScenario
import com.openwallet.ui.activity.fragment.sms.model.CaptchaType
import com.openwallet.ui.activity.fragment.sms.model.SmsRequest
import com.openwallet.ui.activity.fragment.sms.model.SmsVerificationRequest
import com.openwallet.util.CommonUtils

class ResetPasswordEmailSmsFragment : BaseEmailSmsFragment() {

    private lateinit var email: String

    override fun getParameters() {
        arguments?.let { email = it.getString(PARAMS_RESET_PASSWORD_EMAIL).orEmpty() }
    }

    override fun getStepBar() {
    }

    override fun getReceiverInfo(): String = CommonUtils.maskEmailaddress(email)

    override fun getSendCodeHint(): String = getString(R.string.sms_email_hint)

    override fun getVerificationCodeType(): VerificationCodeLockManager.CodeType =
        VerificationCodeLockManager.CodeType.CODE_EMAIL

    override fun submit() {
        val smsVerificationRequest = SmsVerificationRequest(
            emailAddress = email,
            captcha = binding.sifSms.text,
            token = OpenWalletApplication.appViewModel.secretToken.value,
            captchaScenarioEnum = CaptchaScenario.RESET_PASSWORD,
            captchaTypeEnum = CaptchaType.MAIL_VERIFY
        )

        smsViewModel.verifySms(smsVerificationRequest).observe(this) {
            parseState(
                it,
                onLoading = {
                    binding.loading.visible()
                },
                onSuccess = {
                    binding.loading.gone()
                    navigate()
                    resetTimer()
                },
                onInnerError = {
                    binding.loading.gone()
                    binding.sifSms.errorMessage = it.errorMessage.orEmpty()
                    binding.sifSms.state = StandardInputField.State.ERROR
                },
                onNetworkOrExceptionError = {
                    changeVerifyCodeEditBoxState(it.errorMessage)
                    binding.loading.gone()
                    CommonUtils.showSystemError(binding.content)
                }
            )
        }

    }

    override fun getSmsReuqest(): SmsRequest {
        return SmsRequest(
            emailAddress = email,
            captchaScenarioEnum = CaptchaScenario.RESET_PASSWORD,
            captchaTypeEnum = CaptchaType.MAIL_VERIFY
        )
    }

    override fun navigate() {
        nav().navigate(
            R.id.action_ResetPasswordEmailSmsFragment_to_resetPasswordPhoneFragment,
            null,
            NavOptions.Builder().setPopUpTo(R.id.resetPasswordEmailSms, true).build()
        )
    }
}