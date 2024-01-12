package com.openwallet.ui.activity.fragment.resetpassword

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import com.openwallet.R
import com.openwallet.app.OpenWalletApplication.Companion.appViewModel
import com.openwallet.constants.AREA_CODE_PREFIX
import com.openwallet.constants.PARAMS_RESET_PASSWORD_PHONE
import com.openwallet.constants.PARAMS_RESET_PASSWORD_PHONE_COUNTRY_CODE
import com.openwallet.ext.gone
import com.openwallet.ext.nav
import com.openwallet.ext.safeNavigate
import com.openwallet.ext.visible
import com.openwallet.ui.activity.fragment.base.BasePhoneFragment
import com.openwallet.util.CommonUtils

class ResetPasswordPhoneFragment : BasePhoneFragment() {

    private var phone: String = ""
        get() = if (CommonUtils.isLogin()) appViewModel.profileInfo.value?.phoneNumber.orEmpty() else binding.pnifPhonenum.getPhoneNumber()
    private var countryCode: String = ""
        get() = if (CommonUtils.isLogin()) appViewModel.profileInfo.value?.phoneCountryCode.orEmpty() else binding.pnifPhonenum.getPhoneNumberCountryCode()
            .replace("+", "")

    override fun initToolBar() {
        binding.toolbar.init(
            title = getTitle(),
            isShowBack = true,
            isShowCancel = true,
            onCancelClickListener = {
                nav().safeNavigate(R.id.loginPasswordFragment, false)
                CommonUtils.clearInput()
            }
        )
    }

    override fun initViewAndData(view: View, savedInstanceStat: Bundle?) {
        super.initViewAndData(view, savedInstanceStat)
        if (CommonUtils.isLogin()) {
            initResetPasswordViewWithLogin()
        } else {
            initResetPasswordViewWithLogout()
        }
    }

    private fun initResetPasswordViewWithLogin() {
        binding.tvLoggedInPhoneHint.visible()
        binding.tvMaskedPhone.visible()
        binding.tvMaskedPhone.text =
            appViewModel.profileInfo.value?.run {
                "$AREA_CODE_PREFIX$phoneCountryCode ${
                    CommonUtils.maskPhoneNumber(
                        phoneNumber.orEmpty()
                    )
                }"
            }
        binding.pnifPhonenum.gone()
        binding.tvPhoneHint.gone()
        binding.btnSendCode.isEnabled = true
    }

    private fun initResetPasswordViewWithLogout() {

        binding.tvLoggedInPhoneHint.gone()
        binding.tvMaskedPhone.gone()
        binding.tvPhoneHint.gone()
        binding.pnifPhonenum.visible()

        appViewModel.userInput?.run {
            binding.pnifPhonenum.setPhoneNumberCountryCode(countryCode.orEmpty())
            binding.pnifPhonenum.setPhoneNumber(phone.orEmpty())
            binding.btnSendCode.isEnabled = phone.orEmpty().isNotEmpty()
        }
    }

    override fun getParameters() {
    }

    override fun getTitle(): String = getString(R.string.reset_password_title)

    override fun getStepBar() {
        binding.stepBar.apply {
            stepCompleted = 2
            stepMax = 3
            title = String.format(
                getString(R.string.register_step_title_format), stepCompleted, stepMax,
                getString(R.string.reset_password_step2_phone)
            )
        }
    }

    override fun submit() {

        isComplete = true
        binding.btnSendCode.state = PrimaryButton.State.SUCCESS
//        val phoneSmsRequest = SmsRequest(
//            phoneCountryCode = countryCode,
//            phoneNumber = phone,
//            token = appViewModel.secretToken.value,
//            captchaScenarioEnum = CaptchaScenario.RESET_PASSWORD,
//            captchaTypeEnum = CaptchaType.SMS_VERIFY
//        )
//        smsViewModel.sendSms(phoneSmsRequest).observe(this) {
//            parseState(it, onLoading = {
//                binding.btnSendCode.state = PrimaryButton.State.LOADING
//            }, onSuccess = {
//                if (it.status == true) {
//                    binding.btnSendCode.state = PrimaryButton.State.SUCCESS
//                    isComplete = true
//                } else {
//                    binding.btnSendCode.state = PrimaryButton.State.ENABLED
//                    CommonUtils.showSystemError(binding.content)
//                }
//            }, onError = {
//                binding.btnSendCode.state = PrimaryButton.State.ENABLED
//                CommonUtils.showSystemError(binding.content)
//            })
//        }
    }

    override fun submitFail(errorMessage: String?) {
    }

    override fun navigate() {

        nav().navigate(
            R.id.action_ResetPasswordPhoneFragment_to_ResetPasswordPhoneSmsFragment,
            bundleOf(
                PARAMS_RESET_PASSWORD_PHONE_COUNTRY_CODE to countryCode,
                PARAMS_RESET_PASSWORD_PHONE to phone,
            )
        )
    }

    override fun getButtonText(): String = getString(R.string.next)

}