package com.openwallet.ui.activity.fragment.login

import android.util.Log
import androidx.core.os.bundleOf
import com.openwallet.manager.VerificationCodeLockManager
import com.openwallet.R
import com.openwallet.app.OpenWalletApplication
import com.openwallet.constants.PARAMS_ACTION_FROM
import com.openwallet.constants.PARAMS_ACTION_FROM_LOGIN_FAIL
import com.openwallet.constants.PARAMS_LOGIN_PHONE
import com.openwallet.constants.PARAMS_MASKED_EMAIL
import com.openwallet.ext.*
import com.openwallet.manager.CacheManager
import com.openwallet.ui.activity.fragment.base.BasePhoneSmsFragment
import com.openwallet.ui.activity.fragment.sms.model.CaptchaScenario
import com.openwallet.ui.activity.fragment.sms.model.CaptchaType
import com.openwallet.ui.activity.fragment.sms.model.SmsRequest
import com.openwallet.ui.activity.fragment.sms.model.SmsVerificationRequest
import com.openwallet.util.CommonUtils

class LoginPhoneSmsFragment : BasePhoneSmsFragment() {
    private var loginUsername: String = ""
        get() = OpenWalletApplication.appViewModel.userInput?.loginName.orEmpty()
    private lateinit var phone: String
    private lateinit var maskEmail: String
    override fun getParameters() {
        arguments?.let {
            phone = it.getString(PARAMS_LOGIN_PHONE).orEmpty()
            maskEmail = it.getString(PARAMS_MASKED_EMAIL).orEmpty()
        }

    }

    override fun getStepBar() {
    }

    override fun getSmsReuqest(): SmsRequest {
        return SmsRequest(
            emailAddress = maskEmail,
            username = loginUsername,
            token = OpenWalletApplication.appViewModel.secretToken.value,
            captchaScenarioEnum = CaptchaScenario.SIGN_IN,
            captchaTypeEnum = CaptchaType.SMS_VERIFY
        )
    }


    override fun getReceiverInfo(): String = phone

    override fun getSendCodeHint() = getString(R.string.sms_phone_hint)

    override fun getVerificationCodeType(): VerificationCodeLockManager.CodeType =
        VerificationCodeLockManager.CodeType.CODE_SMS

    override fun submit() {
        Log.d("xiesubmit",OpenWalletApplication.appViewModel.secretToken.value.toString())
        val smsVerificationRequest = SmsVerificationRequest(
            username = loginUsername,
            captcha = binding.sifSms.text,
            token = OpenWalletApplication.appViewModel.secretToken.value,
            captchaScenarioEnum = CaptchaScenario.SIGN_IN,
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
                    OpenWalletApplication.appViewModel.loginToken = it.data?.token
                    OpenWalletApplication.appViewModel.refreshToken = it.data?.refreshToken
                    OpenWalletApplication.appViewModel.loginStatus.value = true
                    if (OpenWalletApplication.appViewModel.userInput?.isRememberMeChecked == true) {
                        CacheManager.setUser(it.data)
                        CacheManager.setIsRememberMe(true)
                    }
                    CommonUtils.clearInput()
                    navigate()
                    resetTimer()

                },
                onNetworkOrExceptionError = {
                    binding.loading.gone()
                    CommonUtils.showSystemError(binding.content)
                    navigateOnFail()
                    CommonUtils.clearInput()
                },
                onInnerError = {
                    binding.loading.gone()
                    binding.sifSms.errorMessage = it.errorMessage.orEmpty()
                    binding.sifSms.state = StandardInputField.State.ERROR
                    changeVerifyCodeEditBoxState(it.errorMessage)
                })
        }
    }


    override fun navigate() {
        nav().safeNavigate(R.id.mainFragment)
    }

    private fun navigateOnFail() {
        nav().navigate(
            R.id.action_LoginPhoneSmsFragment_to_StatusFragment,
            bundleOf(PARAMS_ACTION_FROM to PARAMS_ACTION_FROM_LOGIN_FAIL)
        )
    }
}