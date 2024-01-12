package com.openwallet.ui.activity.fragment.base

import android.app.Dialog
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import com.openwallet.R
import com.openwallet.app.OpenWalletApplication.Companion.appViewModel
import com.openwallet.base.BaseFragment
import com.openwallet.databinding.FragmentRegisterPasswordBinding
import com.openwallet.ext.hideKeyboard
import com.openwallet.ext.showLablewithIcon
import com.openwallet.ext.showPassword
import com.openwallet.util.CommonUtils

abstract class BasePasswordFragment : BaseFragment() {
    private lateinit var bottomSheetFragment: MobileDesignBottomSheetDialogFragment
    val binding by viewBinding<FragmentRegisterPasswordBinding>()
    private var isRuleVisible = false
    private var isPasswordVisible = false
    private var isConfirmPasswordVisible = false
    var isComplete: Boolean? = false

    abstract fun getParameters()
    abstract fun getTitle(): String
    abstract fun getStepBar()
    abstract fun submit()
    abstract fun navigate()
    abstract fun getButtonText(): String
    abstract fun initToolBar()

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if(hidden) {
            if(isPasswordVisible) {
                isPasswordVisible = false
                binding.sifPassword.showPassword(false)
            }
            if(isConfirmPasswordVisible) {
                isConfirmPasswordVisible = false
                binding.sifPasswordConfirm.showPassword(false)
            }
        }
    }

    override fun initViewAndData(view: View, savedInstanceStat: Bundle?) {

        appViewModel.userInput?.run {
            binding.sifPassword.text = password.orEmpty()
            binding.sifPasswordConfirm.text = confirmedPassword.orEmpty()
            binding.btnContinue.isEnabled = binding.sifPasswordConfirm.text.length > 7
        }

        val passwordSifText:String = if (CommonUtils.isLogin()) getString(R.string.new_password) else getString(R.string.register_step2_password_enter)
        binding.sifPassword.showLablewithIcon(
            passwordSifText,
            R.drawable.ic_help_circle
        ) {
            val fm = requireActivity().supportFragmentManager
            bottomSheetFragment = MobileDesignBottomSheetDialogFragment.newInstance(
                R.layout.bottom_sheet_register,
                isWrapContent = false,
                withCloseButton = true,
            )

            bottomSheetFragment.apply {
                show(fm, tag)
                setMobileDesignBottomSheetListener(object :
                    MobileDesignBottomSheetDialogFragment.MobileDesignBottomSheetClickListener {
                    override fun getMobileDesignBottomSheetClick(
                        view: View?,
                        dialog: Dialog
                    ) {
                        val tv_content = view?.findViewById<TextView>(R.id.contentTextView)
                        tv_content?.text = getString(R.string.password_alert)
                        setTitle(title = getString(R.string.register_password_bottomsheet_hint))

                    }
                })

            }
        }


        binding.sifPassword.apply {
            setEditTextFocusChangeListener { view, hasFocus ->

                if (hasFocus) {
                    clearInlineMessageView()
                    binding.sifPasswordConfirm.clearInlineMessageView()
                } else {
                    appViewModel.userInput?.password = text
                    if (!validatePassword(text)) {
                        infoMessage = getString(R.string.register_step2_password_info)
                        state = StandardInputField.State.INFO
                    }
                }
            }

            setIconOnClickListener {
                isPasswordVisible = !isPasswordVisible
                showPassword(isPasswordVisible)
            }
        }

        getParameters()
        initToolBar()
        getStepBar()

        binding.sifPasswordConfirm.apply {
            setIconOnClickListener {
                isConfirmPasswordVisible = !isConfirmPasswordVisible
                showPassword(isConfirmPasswordVisible)
            }
            doAfterTextChanged {
                binding.btnContinue.isEnabled = it.toString().length > 7
                appViewModel.userInput?.confirmedPassword = it.toString()
            }
        }
        // button click
        binding.btnContinue.text = getButtonText()
        binding.btnContinue.apply {
            setOnClickListener {
                activity?.hideKeyboard()
                if (binding.sifPassword.text != binding.sifPasswordConfirm.text) {
                    binding.sifPasswordConfirm.errorMessage =
                        getString(R.string.register_step2_password_not_match)
                    binding.sifPasswordConfirm.state = StandardInputField.State.ERROR
                    //isErrorVisible = true
                    return@setOnClickListener
                }
                if (validatePassword(binding.sifPassword.text)) {
                    submit()
                } else {
                    binding.sifPassword.infoMessage =
                        getString(R.string.register_step2_password_info)
                    binding.sifPassword.state = StandardInputField.State.INFO
                    isRuleVisible = true
                    binding.sifPasswordConfirm.state = StandardInputField.State.INPUT
                    binding.sifPasswordConfirm.clearInlineMessageView()
                }
            }
            setOnCompleteListener {
                if (isComplete == true) {
                    navigate()
                    binding.btnContinue.state = PrimaryButton.State.ENABLED
                }
            }
        }
    }

    private fun validatePassword(password: String): Boolean {
        val passRegex =
            "(?=.{8,20}$)((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(^[a-zA-Z0-9_@#$%&<(\\[{^\\-=$!|\\]})?*+.>]*$)" +
                    "|(?=.*\\d)(?=.*[a-zA-Z])(?=.*[\\W_])(^[a-zA-Z0-9_@#$%&<(\\[{^\\-=$!|\\]})?*+.>]*$)" +
                    "|(?=.*[a-z])(?=.*[A-Z])(?=.*[\\W_])(^[a-zA-Z0-9_@#$%&<(\\[{^\\-=$!|\\]})?*+.>]*$)).*"
        return !TextUtils.isEmpty(password) && password.matches(passRegex.toRegex())
    }
}
