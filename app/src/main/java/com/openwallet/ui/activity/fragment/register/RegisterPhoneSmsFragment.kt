package com.openwallet.ui.activity.fragment.register

import androidx.core.os.bundleOf
import androidx.navigation.NavOptions
import com.openwallet.manager.VerificationCodeLockManager
import com.openwallet.R
import com.openwallet.app.OpenWalletApplication
import com.openwallet.constants.PARAMS_REGISTER_NAME
import com.openwallet.constants.PARAMS_REGISTER_PHONE
import com.openwallet.constants.PARAMS_REGISTER_PHONE_COOUNTRY_CODE
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

class RegisterPhoneSmsFragment : BasePhoneSmsFragment() {
    private lateinit var phoneCountryCode: String
    private lateinit var phone: String
    private lateinit var registerUsername: String

    override fun getParameters() {
        arguments?.let {
            phoneCountryCode = it.getString(PARAMS_REGISTER_PHONE_COOUNTRY_CODE).orEmpty()
            phone = it.getString(PARAMS_REGISTER_PHONE).orEmpty()
            registerUsername = it.getString(PARAMS_REGISTER_NAME).orEmpty()
        }
    }

    override fun getStepBar() {
    }

    override fun getReceiverInfo(): String {
        return "$phoneCountryCode ${CommonUtils.maskPhoneNumber(phone)}"
    }

    override fun getSmsReuqest(): SmsRequest {
        return SmsRequest(
            username = registerUsername,
            phoneCountryCode = phoneCountryCode.replace("+", ""),
            phoneNumber = phone,
            token = OpenWalletApplication.appViewModel.secretToken.value,
            captchaScenarioEnum = CaptchaScenario.REGISTER,
            captchaTypeEnum = CaptchaType.SMS_VERIFY
        )
    }

    override fun getVerificationCodeType(): VerificationCodeLockManager.CodeType =
        VerificationCodeLockManager.CodeType.CODE_SMS

    override fun submit() {
        val smsVerificationRequest = SmsVerificationRequest(
            username = registerUsername,
            captcha = binding.sifSms.text,
            token = OpenWalletApplication.appViewModel.secretToken.value,
            captchaScenarioEnum = CaptchaScenario.REGISTER,
            captchaTypeEnum = CaptchaType.SMS_VERIFY
        )
        smsViewModel.verifySms(smsVerificationRequest).observe(this) {
            parseState(
                it,
                onLoading = {
                    binding.loading.visible()
                    binding.sifSms.state = StandardInputField.State.INPUT
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
                    changeVerifyCodeEditBoxState(it.errorMessage)
                    binding.sifSms.errorMessage = it.errorMessage.orEmpty()
                    binding.sifSms.state = StandardInputField.State.ERROR
                },
            )
        }
    }

    override fun getSendCodeHint() = getString(R.string.sms_phone_hint)

    override fun navigate() {
        nav().navigate(
            R.id.action_RegisterPhoneSmsFragment_to_RegisterConsent, bundleOf(PARAMS_REGISTER_NAME to registerUsername),
            NavOptions.Builder().setPopUpTo(R.id.registerPhoneSms, true).build()
        )
    }
}