package com.openwallet.ui.activity.fragment.login

import androidx.core.os.bundleOf
import com.openwallet.manager.VerificationCodeLockManager
import com.openwallet.R
import com.openwallet.app.OpenWalletApplication
import com.openwallet.constants.PARAMS_ACTION_FROM
import com.openwallet.constants.PARAMS_ACTION_FROM_LOGIN_FAIL
import com.openwallet.constants.PARAMS_LOGIN_EMAIL
import com.openwallet.ext.*
import com.openwallet.manager.CacheManager
import com.openwallet.ui.activity.fragment.base.BaseEmailSmsFragment
import com.openwallet.ui.activity.fragment.sms.model.CaptchaScenario
import com.openwallet.ui.activity.fragment.sms.model.CaptchaType
import com.openwallet.ui.activity.fragment.sms.model.SmsRequest
import com.openwallet.ui.activity.fragment.sms.model.SmsVerificationRequest
import com.openwallet.util.CommonUtils

class LoginEmailSmsFragment : BaseEmailSmsFragment() {

    private lateinit var email: String
    private var loginUsername: String = ""
        get() = OpenWalletApplication.appViewModel.userInput?.loginName.orEmpty()

    override fun getParameters() {
        arguments?.let {
            email = it.getString(PARAMS_LOGIN_EMAIL).orEmpty()
        }

    }

    override fun getStepBar() {
    }

    override fun getSendCodeHint() = getString(R.string.sms_email_hint)

    override fun getReceiverInfo(): String {
        return email
    }

    override fun getSmsReuqest(): SmsRequest {
        return SmsRequest(
            emailAddress = email,
            username = loginUsername,
            token = OpenWalletApplication.appViewModel.secretToken.value,
            captchaScenarioEnum = CaptchaScenario.SIGN_IN,
            captchaTypeEnum = CaptchaType.MAIL_VERIFY
        )
    }

    override fun getVerificationCodeType(): VerificationCodeLockManager.CodeType =
        VerificationCodeLockManager.CodeType.CODE_EMAIL

    override fun submit() {
        val smsVerificationRequest = SmsVerificationRequest(
            username = loginUsername,
            captcha = binding.sifSms.text,
            token = OpenWalletApplication.appViewModel.secretToken.value,
            captchaScenarioEnum = CaptchaScenario.SIGN_IN,
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
        nav().safeNavigate(R.id.mainFragment, false)
    }

    private fun navigateOnFail() {
        nav().navigate(
            R.id.action_LoginEmailSmsFragment_to_StatusFragment,
            bundleOf(PARAMS_ACTION_FROM to PARAMS_ACTION_FROM_LOGIN_FAIL)
        )
    }
}