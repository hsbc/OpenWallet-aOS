package com.openwallet.ui.activity.fragment.register

import androidx.core.os.bundleOf
import androidx.navigation.NavOptions
import com.openwallet.manager.VerificationCodeLockManager
import com.openwallet.R
import com.openwallet.app.OpenWalletApplication
import com.openwallet.constants.PARAMS_REGISTER_EMAIL
import com.openwallet.constants.PARAMS_REGISTER_NAME
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

class RegisterEmailSmsFragment : BaseEmailSmsFragment() {

    private lateinit var email: String
    private lateinit var registerUsername: String

    override fun getParameters() {
        arguments?.let {
            email = it.getString(PARAMS_REGISTER_EMAIL).orEmpty()
            registerUsername = it.getString(PARAMS_REGISTER_NAME).orEmpty()
        }
    }

    override fun getSmsReuqest(): SmsRequest {
        return SmsRequest(
            username = registerUsername,
            emailAddress = email,
            token = OpenWalletApplication.appViewModel.secretToken.value,
            captchaScenarioEnum = CaptchaScenario.REGISTER,
            captchaTypeEnum = CaptchaType.MAIL_VERIFY
        )
    }

    override fun getStepBar() {
    }

    override fun getReceiverInfo(): String {
        return CommonUtils.maskEmailaddress(email)
    }

    override fun getVerificationCodeType(): VerificationCodeLockManager.CodeType =
        VerificationCodeLockManager.CodeType.CODE_EMAIL

    override fun submit() {
        val smsVerificationRequest = SmsVerificationRequest(
            username = registerUsername,
            captcha = binding.sifSms.text,
            token = OpenWalletApplication.appViewModel.secretToken.value,
            captchaScenarioEnum = CaptchaScenario.REGISTER,
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
                onNetworkOrExceptionError = {
                    binding.loading.gone()
                    CommonUtils.showSystemError(binding.content)
                },
                onInnerError = {
                    binding.loading.gone()
                    binding.sifSms.errorMessage = it.errorMessage.orEmpty()
                    binding.sifSms.state = StandardInputField.State.ERROR
                    changeVerifyCodeEditBoxState(it.errorMessage)
                }
            )
        }
    }

    override fun getSendCodeHint() = getString(R.string.sms_email_hint)

    override fun navigate() {
        nav().navigate(
            R.id.action_RegisterEmailSmsFragment_to_RegisterPhoneFragment, bundleOf(
                PARAMS_REGISTER_NAME to registerUsername
            ), NavOptions.Builder().setPopUpTo(R.id.registerEmailSms, true).build()
        )
    }
}