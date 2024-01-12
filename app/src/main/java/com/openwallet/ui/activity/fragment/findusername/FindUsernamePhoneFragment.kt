package com.openwallet.ui.activity.fragment.findusername

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import com.openwallet.R
import com.openwallet.app.OpenWalletApplication
import com.openwallet.constants.PARAMS_FIND_NAME_PHONE
import com.openwallet.constants.PARAMS_FIND_NAME_PHONE_COUNTRY_CODE
import com.openwallet.ext.gone
import com.openwallet.ext.nav
import com.openwallet.ext.safeNavigate
import com.openwallet.ext.visible
import com.openwallet.ui.activity.fragment.base.BasePhoneFragment
import com.openwallet.util.CommonUtils

class FindUsernamePhoneFragment : BasePhoneFragment() {

    override fun getParameters() {
    }

    override fun initViewAndData(view: View, savedInstanceStat: Bundle?) {
        super.initViewAndData(view, savedInstanceStat)
        initFindUsernameView()
    }

    private fun initFindUsernameView(){
        binding.tvLoggedInPhoneHint.gone()
        binding.tvMaskedPhone.gone()
        binding.tvPhoneHint.gone()
        binding.pnifPhonenum.visible()

        OpenWalletApplication.appViewModel.userInput?.run {
            binding.pnifPhonenum.setPhoneNumberCountryCode(countryCode.orEmpty())
            binding.pnifPhonenum.setPhoneNumber(phone.orEmpty())
            binding.btnSendCode.isEnabled = phone.orEmpty().isNotEmpty()
        }
    }

    override fun getTitle(): String = getString(R.string.find_username_title)

    override fun getStepBar() {
        binding.stepBar.apply {
            stepCompleted = 2
            stepMax = 2
            title = String.format(
                getString(R.string.register_step_title_format), stepCompleted, stepMax,
                getString(R.string.find_username_step2_phone)
            )
        }
    }

    override fun submit() {
        isComplete = true
        binding.btnSendCode.state = PrimaryButton.State.SUCCESS
//        val phoneSmsRequest = SmsRequest(
//            phoneCountryCode = binding.pnifPhonenum.getPhoneNumberCountryCode().replace("+", ""),
//            phoneNumber = binding.pnifPhonenum.getPhoneNumber(),
//            token = OpenWalletApplication.appViewModel.secretToken.value,
//            captchaScenarioEnum = CaptchaScenario.FORGOT_USERNAME,
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
            R.id.action_FindUsernamePhoneFragment_to_FindUsernamePhoneSmsFragment,
            bundleOf(
                PARAMS_FIND_NAME_PHONE_COUNTRY_CODE to binding.pnifPhonenum.getPhoneNumberCountryCode().replace("+", ""),
                PARAMS_FIND_NAME_PHONE to binding.pnifPhonenum.getPhoneNumber()
            )
        )
    }

    override fun getButtonText(): String = getString(R.string.sendCode)

    override fun initToolBar() {
        binding.toolbar.init(
            title = getTitle(),
            isShowBack = true,
            isShowCancel = true,
            onCancelClickListener = {
                nav().safeNavigate(R.id.loginStartFragment, false)
                CommonUtils.clearInput()
            }
        )

    }
}