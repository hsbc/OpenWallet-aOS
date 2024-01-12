package com.openwallet.ui.activity.fragment.base

import android.os.Bundle
import android.view.View
import com.openwallet.R
import com.openwallet.base.BaseFragment
import com.openwallet.databinding.FragmentRegisterEmailBinding
import com.openwallet.ext.hideKeyboard
import com.openwallet.util.CommonUtils


abstract class BaseEmailFragment : BaseFragment() {

    val binding by viewBinding<FragmentRegisterEmailBinding>()
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

        binding.btnVerifyEmail.text = getButtonText()
        binding.btnVerifyEmail.apply {
            setOnClickListener {
                if (!CommonUtils.isLogin()) {
                    if (binding.sifEmail.text.isEmpty()) return@setOnClickListener
                    if (!CommonUtils.isValidEmailAddress(binding.sifEmail.text)) {
                        binding.sifEmail.warningMessage = getString(R.string.enter_valid_email_address_hint)
                        binding.sifEmail.state = StandardInputField.State.WARNING
                        return@setOnClickListener
                    }
                }
                activity?.hideKeyboard()
                submit()
            }
            setOnCompleteListener {
                if (isComplete == true) {
                    navigate()
                    binding.btnVerifyEmail.state = PrimaryButton.State.ENABLED
                }
            }
        }
    }
}