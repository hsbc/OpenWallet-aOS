package com.openwallet.ui.activity.fragment.findusername

import androidx.core.os.bundleOf
import com.openwallet.manager.VerificationCodeLockManager
import com.openwallet.R
import com.openwallet.app.OpenWalletApplication
import com.openwallet.app.OpenWalletApplication.Companion.appViewModel
import com.openwallet.constants.*
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

class FindUsernamePhoneSmsFragment : BasePhoneSmsFragment() {
    private lateinit var phone: String
    private lateinit var phoneCountryCode: String
    private lateinit var findedName: String

    override fun getParameters() {
        arguments?.let {
            phone = it.getString(PARAMS_FIND_NAME_PHONE).orEmpty()
            phoneCountryCode = it.getString(PARAMS_FIND_NAME_PHONE_COUNTRY_CODE).orEmpty()
        }
    }

    override fun getSmsReuqest(): SmsRequest {
        return SmsRequest(
            phoneNumber = phone,
            phoneCountryCode = phoneCountryCode,
            token = OpenWalletApplication.appViewModel.secretToken.value,
            captchaScenarioEnum = CaptchaScenario.FORGOT_USERNAME,
            captchaTypeEnum = CaptchaType.SMS_VERIFY
        )
    }

    override fun getStepBar() {
    }

    override fun getReceiverInfo(): String = "$phoneCountryCode ${CommonUtils.maskPhoneNumber(phone)}"

    override fun getSendCodeHint(): String = getString(R.string.sms_phone_hint)

    override fun getVerificationCodeType(): VerificationCodeLockManager.CodeType =
        VerificationCodeLockManager.CodeType.CODE_SMS

    override fun submit() {
        val smsVerificationRequest = SmsVerificationRequest(
            captcha = binding.sifSms.text,
            token = OpenWalletApplication.appViewModel.secretToken.value,
            captchaScenarioEnum = CaptchaScenario.FORGOT_USERNAME,
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
                    CommonUtils.clearInput()
                    findedName = it.data?.username.orEmpty()
                    appViewModel.userInput?.loginName = findedName
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

    override fun navigate() {
        nav().navigate(
            R.id.action_FindUsernamePhoneSmsFragment_to_StatusFragment, bundleOf(
                PARAMS_ACTION_FROM to PARAMS_ACTION_FROM_FIND_NAME_SUCCESS,
                PARMAS_FINDED_NAME to findedName
            )
        )
    }

}