package com.openwallet.ui.activity.fragment.findusername

import androidx.navigation.NavOptions
import com.openwallet.manager.VerificationCodeLockManager
import com.openwallet.R
import com.openwallet.app.OpenWalletApplication
import com.openwallet.constants.PARAMS_FIND_NAME_EMAIL
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

class FindUsernameEmailSmsFragment : BaseEmailSmsFragment() {
    private lateinit var email: String
    override fun getParameters() {
        arguments?.let { email = it.getString(PARAMS_FIND_NAME_EMAIL).orEmpty() }
    }

    override fun getStepBar() {
    }

    override fun getReceiverInfo(): String = CommonUtils.maskEmailaddress(email)

    override fun getSendCodeHint() = getString(R.string.sms_email_hint)

    override fun getVerificationCodeType(): VerificationCodeLockManager.CodeType =
        VerificationCodeLockManager.CodeType.CODE_EMAIL

    override fun submit() {
        val smsVerificationRequest = SmsVerificationRequest(
            captcha = binding.sifSms.text,
            token = OpenWalletApplication.appViewModel.secretToken.value,
            captchaScenarioEnum = CaptchaScenario.FORGOT_USERNAME,
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
            captchaScenarioEnum = CaptchaScenario.FORGOT_USERNAME,
            captchaTypeEnum = CaptchaType.MAIL_VERIFY
        )
    }


    override fun navigate() {
        nav().navigate(
            R.id.action_FindUsernameEmailSmsFragment_to_FindUsernamePhoneFragment,
            null,
            NavOptions.Builder().setPopUpTo(R.id.findUsernameEmailSms, true).build()
        )
    }
}