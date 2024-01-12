package com.openwallet.ui.activity.fragment.register

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import com.openwallet.R
import com.openwallet.app.OpenWalletApplication
import com.openwallet.constants.PARAMS_REGISTER_NAME
import com.openwallet.constants.PARAMS_REGISTER_PHONE
import com.openwallet.constants.PARAMS_REGISTER_PHONE_COOUNTRY_CODE
import com.openwallet.ext.gone
import com.openwallet.ext.nav
import com.openwallet.ext.safeNavigate
import com.openwallet.ext.visible
import com.openwallet.ui.activity.fragment.base.BasePhoneFragment
import com.openwallet.util.CommonUtils

class RegisterPhoneFragment : BasePhoneFragment() {

    private lateinit var registerUsername: String

    override fun initToolBar() {
        binding.toolbar.init(
            title = getTitle(),
            isShowBack = true,
            isShowCancel = true,
            onCancelClickListener = {
                nav().safeNavigate(R.id.startRegisterFragment, false)
                CommonUtils.clearInput()
            }
        )

    }

    override fun initViewAndData(view: View, savedInstanceStat: Bundle?) {
        super.initViewAndData(view, savedInstanceStat)
        initRegistrationView()
    }

    private fun initRegistrationView(){
        binding.tvLoggedInPhoneHint.gone()
        binding.tvMaskedPhone.gone()
        binding.pnifPhonenum.visible()
        binding.tvPhoneHint.visible()

        OpenWalletApplication.appViewModel.userInput?.run {
            binding.pnifPhonenum.setPhoneNumberCountryCode(countryCode.orEmpty())
            binding.pnifPhonenum.setPhoneNumber(phone.orEmpty())
            binding.btnSendCode.isEnabled = phone.orEmpty().isNotEmpty()
        }
    }

    override fun getParameters() {
        arguments?.let {
            registerUsername = it.getString(PARAMS_REGISTER_NAME).orEmpty()
        }
    }

    override fun getTitle(): String = getString(R.string.register)

    override fun getStepBar() {
        binding.stepBar.apply {
            stepCompleted = 4
            stepMax = 4
            title = String.format(
                getString(R.string.register_step_title_format), stepCompleted, stepMax,
                getString(R.string.register_step4_phone)
            )
        }
    }

    override fun submit() {
        isComplete = true
        binding.btnSendCode.state = PrimaryButton.State.SUCCESS
//        val phoneSmsRequest = SmsRequest(
//            username = registerUsername,
//            phoneCountryCode = binding.pnifPhonenum.getPhoneNumberCountryCode().replace("+", ""),
//            phoneNumber = binding.pnifPhonenum.getPhoneNumber(),
//            token = OpenWalletApplication.appViewModel.secretToken.value,
//            captchaScenarioEnum = CaptchaScenario.REGISTER,
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
            R.id.action_RegisterPhoneFragment_to_RegisterPhoneSmsFragment,
            bundleOf(
                PARAMS_REGISTER_PHONE_COOUNTRY_CODE to binding.pnifPhonenum.getPhoneNumberCountryCode(),
                PARAMS_REGISTER_PHONE to binding.pnifPhonenum.getPhoneNumber(),
                PARAMS_REGISTER_NAME to registerUsername
            )
        )
    }

    override fun getButtonText(): String = getString(R.string.sendCode)

}