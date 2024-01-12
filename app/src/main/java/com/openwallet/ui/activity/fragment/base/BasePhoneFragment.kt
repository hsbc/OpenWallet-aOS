package com.openwallet.ui.activity.fragment.base

import android.os.Bundle
import android.view.View
import com.openwallet.app.OpenWalletApplication.Companion.appViewModel
import com.openwallet.base.BaseFragment
import com.openwallet.constants.ACCEPTED_PHONE_STRING
import com.openwallet.databinding.FragmentRegisterPhoneBinding
import com.openwallet.ext.hideKeyboard
import com.openwallet.ext.setInputConstraint
import com.openwallet.ui.activity.fragment.register.CountryCodeDialogFragment
import com.openwallet.ui.activity.fragment.sms.model.SmsViewModel
import com.openwallet.util.FastClickCheckUtil


abstract class BasePhoneFragment : BaseFragment(), PhoneNumberInputField.PhoneNumberTextChangeListener {

    val binding by viewBinding<FragmentRegisterPhoneBinding>()

    val smsViewModel by viewModelFragment<SmsViewModel>()
    var isComplete: Boolean? = false

    abstract fun getParameters()
    abstract fun getTitle(): String
    abstract fun getStepBar()
    abstract fun submit()
    abstract fun submitFail(errorMessage: String?)
    abstract fun navigate()
    abstract fun getButtonText(): String
    abstract fun initToolBar()

    override fun initViewAndData(view: View, savedInstanceStat: Bundle?) {
        getParameters()
        initToolBar()
        getStepBar()

        activity?.supportFragmentManager?.setFragmentResultListener(
            "passCountryCode",
            this
        ) { _, bundle ->
            val result = bundle.getString("countryCode")
            binding.pnifPhonenum.setPhoneNumberCountryCode(result!!)
            appViewModel.userInput?.countryCode = result
        }

        binding.pnifPhonenum.apply {
            this.setInputConstraint(ACCEPTED_PHONE_STRING)
            setPhoneNumberCountryCodeOnClickListener {
                if (!FastClickCheckUtil.isCanClickIn600MS()) {
                    return@setPhoneNumberCountryCodeOnClickListener
                }
                //every time create new fragment
                val countryCodeDialogFragment = CountryCodeDialogFragment()
                activity?.supportFragmentManager?.let { it1 ->
                    if (!countryCodeDialogFragment.isAdded) {
                        countryCodeDialogFragment.show(it1, "countrycode")
                    }
                }
            }
            bindPhoneNumberTextChangeListener(this@BasePhoneFragment)
        }
        // button click
        binding.btnSendCode.text = getButtonText()
        binding.btnSendCode.apply {
            setOnClickListener {
                activity?.hideKeyboard()
                submit()
//                registerViewModel.checkUserEmail(binding.sifEmail.text)
//                    .observe(this@BaseEmailFragment) {
//                        parseState(
//                            it,
//                            onLoading = {
//                                binding.sifEmail.state = StandardInputField.State.INPUT
//                                binding.sifEmail.clearInlineMessageView()
//                                binding.btnVerifyEmail.state = PrimaryButton.State.LOADING
//                            },
//                            onSuccess = {
//                                submit()
//                            },
//                            onError = {
//                                submitFail(it.errorMessage)
//                            }
//                        )
//                    }
            }
            setOnCompleteListener {
                if (isComplete == true) {
                    navigate()
                    binding.btnSendCode.state = PrimaryButton.State.ENABLED
                }
            }
        }
    }

    override fun notifyViewOnMaxLengthReached() {
    }

    override fun notifyViewOnMinLengthReached() {
    }

    override fun notifyViewOnTextTyped(typedText: String) {
        appViewModel.userInput?.phone = typedText
        binding.btnSendCode.isEnabled = typedText.isNotEmpty()
    }
}
