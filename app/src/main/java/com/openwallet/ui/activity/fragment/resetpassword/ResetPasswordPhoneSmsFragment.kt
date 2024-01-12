package com.openwallet.ui.activity.fragment.resetpassword

import androidx.navigation.NavOptions
import com.openwallet.manager.VerificationCodeLockManager
import com.openwallet.R
import com.openwallet.app.OpenWalletApplication
import com.openwallet.constants.AREA_CODE_PREFIX
import com.openwallet.constants.PARAMS_RESET_PASSWORD_PHONE
import com.openwallet.constants.PARAMS_RESET_PASSWORD_PHONE_COUNTRY_CODE
import com.openwallet.ext.gone
import com.openwallet.ext.nav
import com.openwallet.ext.parseState
import com.openwallet.ext.visible
import com.openwallet.ui.activity.fragment.base.BasePhoneSmsFragment
import com.openwallet.ui.activity.fragment.sms.model.CaptchaScenario
import com.openwallet.ui.activity.fragment.sms.model.CaptchaType
import com.openwallet.ui.activity.fragment.sms.model.SmsRequest
import com.openwallet.ui.activity.fragment.sms.model.SmsVerificationRequest
import com.openwallet.util.CommonUtils

class ResetPasswordPhoneSmsFragment : BasePhoneSmsFragment() {
    private lateinit var phoneCountryCode: String
    private lateinit var phone: String
    override fun getParameters() {
        arguments?.let {
            phone = it.getString(PARAMS_RESET_PASSWORD_PHONE).orEmpty()
            phoneCountryCode = it.getString(PARAMS_RESET_PASSWORD_PHONE_COUNTRY_CODE).orEmpty()
        }
    }


    override fun getStepBar() {
    }

    override fun getReceiverInfo(): String = "$AREA_CODE_PREFIX$phoneCountryCode ${CommonUtils.maskPhoneNumber(phone)}"

    override fun getSendCodeHint(): String = getString(R.string.sms_phone_hint)

    override fun getVerificationCodeType(): VerificationCodeLockManager.CodeType =
        VerificationCodeLockManager.CodeType.CODE_SMS

    override fun submit() {

        val smsVerificationRequest = SmsVerificationRequest(
            captcha = binding.sifSms.text,
            token = OpenWalletApplication.appViewModel.secretToken.value,
            captchaScenarioEnum = CaptchaScenario.RESET_PASSWORD,
            captchaTypeEnum = CaptchaType.SMS_VERIFY
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
            phoneCountryCode = phoneCountryCode,
            phoneNumber = phone,
            token = OpenWalletApplication.appViewModel.secretToken.value,
            captchaScenarioEnum = CaptchaScenario.RESET_PASSWORD,
            captchaTypeEnum = CaptchaType.SMS_VERIFY
        )
    }

    override fun navigate() {
        nav().navigate(
            R.id.action_ResetPasswordPhoneSmsFragment_to_ComfirmationFragment,
            null,
            NavOptions.Builder().setPopUpTo(R.id.resetPasswordPhoneSms, true).build()
        )

    }
}