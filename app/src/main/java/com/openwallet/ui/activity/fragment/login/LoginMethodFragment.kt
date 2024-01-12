package com.openwallet.ui.activity.fragment.login

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import com.openwallet.R
import com.openwallet.app.OpenWalletApplication
import com.openwallet.app.OpenWalletApplication.Companion.appViewModel
import com.openwallet.base.BaseFragment
import com.openwallet.constants.*
import com.openwallet.databinding.FragmentLoginVerifyMethodBinding
import com.openwallet.ext.gone
import com.openwallet.ext.nav
import com.openwallet.ext.parseState
import com.openwallet.ext.visible
import com.openwallet.ui.activity.fragment.sms.model.CaptchaScenario
import com.openwallet.ui.activity.fragment.sms.model.CaptchaType
import com.openwallet.ui.activity.fragment.sms.model.SmsRequest
import com.openwallet.ui.activity.fragment.sms.model.SmsViewModel
import com.openwallet.util.CommonUtils

class LoginMethodFragment : BaseFragment() {
    private val binding by viewBinding<FragmentLoginVerifyMethodBinding>()
    private val smsViewModel by viewModelFragment<SmsViewModel>()
    private lateinit var maskedEmail: String
    private lateinit var maskedPhone: String
    private var loginUsername: String = ""
        get() = appViewModel.userInput?.loginName.orEmpty()
    private var isSelected = false

    override fun initViewAndData(view: View, savedInstanceStat: Bundle?) {

        arguments?.let {
            maskedEmail = it.getString(PARAMS_MASKED_EMAIL).orEmpty()
            maskedPhone = it.getString(PARAMS_MASKED_PHONE).orEmpty()
        }
        binding.toolbar.init(
            isShowBack = true,
            title = getString(R.string.login_methods_title)
        )
        binding.llByEmail.setOnClickListener {
            nav().navigate(
                R.id.action_LoginMethodFragment_to_LoginEmailSmsFragment, bundleOf(
                    PARAMS_LOGIN_EMAIL to maskedEmail,
                    PARAMS_LOGIN_NAME to loginUsername
                )
            )
        }

        binding.llByPhone.setOnClickListener {
            Log.d("xie",OpenWalletApplication.appViewModel.secretToken.value.toString())
            nav().navigate(
                R.id.action_LoginMethodFragment_to_LoginPhoneSmsFragment, bundleOf(
                    PARAMS_LOGIN_PHONE to maskedPhone,
                    PARAMS_LOGIN_NAME to loginUsername,
                    PARAMS_MASKED_EMAIL to maskedEmail
                )
            )
        }

    }

    private fun sendEmailSms() {
        val emailSmsRequest = SmsRequest(
            username = loginUsername,
            token = OpenWalletApplication.appViewModel.secretToken.value,
            captchaScenarioEnum = CaptchaScenario.SIGN_IN,
            captchaTypeEnum = CaptchaType.MAIL_VERIFY
        )
        smsViewModel.sendSms(emailSmsRequest).observe(this) {
            parseState(
                it,
                onLoading = {
                    binding.loading.visible()
                },
                onSuccess = {
                    binding.loading.gone()
                    isSelected = !isSelected
                    if (it.status == true)
                        nav().navigate(
                            R.id.action_LoginMethodFragment_to_LoginEmailSmsFragment, bundleOf(
                                PARAMS_LOGIN_EMAIL to maskedEmail,
                                PARAMS_LOGIN_NAME to loginUsername
                            )
                        )
                    else {
                        CommonUtils.showSystemError(binding.content)
                    }
                },
                onError = {
                    binding.loading.gone()
                    isSelected = !isSelected
                    CommonUtils.showSystemError(binding.content)
                }
            )
        }
    }

    private fun sendPhoneSms() {
        val phoneSmsRequest = SmsRequest(
            username = loginUsername,
            token = OpenWalletApplication.appViewModel.secretToken.value,
            captchaScenarioEnum = CaptchaScenario.SIGN_IN,
            captchaTypeEnum = CaptchaType.SMS_VERIFY
        )
        smsViewModel.sendSms(phoneSmsRequest).observe(this) {
            parseState(
                it,
                onLoading = {
                    binding.loading.visible()
                },
                onSuccess = {
                    binding.loading.gone()
                    isSelected = !isSelected
                    if (it.status == true)
                        nav().navigate(
                            R.id.action_LoginMethodFragment_to_LoginPhoneSmsFragment, bundleOf(
                                PARAMS_LOGIN_PHONE to maskedPhone,
                                PARAMS_LOGIN_NAME to loginUsername
                            )
                        )
                    else {
                        CommonUtils.showSystemError(binding.content)
                    }
                },
                onError = {
                    binding.loading.gone()
                    isSelected = !isSelected
                    CommonUtils.showSystemError(binding.content)
                }
            )
        }
    }

}