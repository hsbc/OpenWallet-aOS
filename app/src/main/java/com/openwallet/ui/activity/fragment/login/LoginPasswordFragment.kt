package com.openwallet.ui.activity.fragment.login

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.os.bundleOf
import com.openwallet.R
import com.openwallet.app.OpenWalletApplication.Companion.appViewModel
import com.openwallet.base.BaseFragment
import com.openwallet.constants.PARAMS_MASKED_EMAIL
import com.openwallet.constants.PARAMS_MASKED_PHONE
import com.openwallet.databinding.FragmentLoginPasswordBinding
import com.openwallet.ext.*
import com.openwallet.ui.activity.fragment.login.model.LoginViewModel
import com.openwallet.util.CommonUtils
import com.openwallet.util.CommonUtils.clearInput

class LoginPasswordFragment : BaseFragment() {

    private val binding by viewBinding<FragmentLoginPasswordBinding>()
    private val loginViewModel by viewModelFragment<LoginViewModel>()
    private var loginUsername = ""
        get() = if (CommonUtils.isRememberedMe()) CommonUtils.getRememberedUserName() else appViewModel.userInput?.loginName.orEmpty()
    private var loginPassword = ""
        get() =  appViewModel.userInput?.loginPassword.orEmpty()
    private var isComplete = false
    private var isPasswordVisible = false

    private lateinit var maskedEmail: String
    private lateinit var maskedPhone: String

    private lateinit var bottomSheetFragment: MobileDesignBottomSheetDialogFragment


    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (hidden) {
            if (isPasswordVisible) {
                isPasswordVisible = false
                binding.sifPassword.showPassword(false)
            }
        }
    }

    override fun initViewAndData(view: View, savedInstanceStat: Bundle?) {

        binding.tvAdTitle.text = getGreetings(CommonUtils.getLocalHour())

        binding.toolbar.init(
            isShowBack = true,
            onBackClickListener = { navigateBackInLoginPasswordPage() },
            isShowCenterIcon = true
        )

        binding.sifPassword.apply {
            appViewModel.userInput?.loginPassword.orEmpty()
            setIconOnClickListener {
                isPasswordVisible = !isPasswordVisible
                showPassword(isPasswordVisible)
            }
            doAfterTextChanged {
                appViewModel.userInput?.loginPassword = it.toString()
                binding.btnLogin.isEnabled = it.toString().length > 7
            }
        }

        binding.sifPassword.showLablewithIcon(
            getString(R.string.login_password), R.drawable.ic_help_circle
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
                        val tvContent = view?.findViewById<TextView>(R.id.contentTextView)
                        tvContent?.text = getString(R.string.password_alert)
                        setTitle(title = getString(R.string.register_password_bottomsheet_hint))
                    }
                })

            }
        }

        if (CommonUtils.isRememberedMe()) {
            showRememberMeLayout()
        } else {
            showNotRememberMeLayout()
        }

        binding.tvSwitchUser.setOnClickListener {
            CommonUtils.clearRememberMeState()
            binding.sifPassword.text = ""
            showNotRememberMeLayout()
            nav().safeNavigate(R.id.loginStartFragment)
        }

        binding.btnLogin.apply {
            setOnClickListener {
                loginByPassword(loginUsername, binding.sifPassword.text)
            }
            setOnCompleteListener {
                if (isComplete) {
                    nav().navigate(
                        R.id.action_LoginPasswordFragment_to_LoginMethodFragment,
                        bundleOf(
                            PARAMS_MASKED_EMAIL to maskedEmail,
                            PARAMS_MASKED_PHONE to maskedPhone,
                        )
                    )
                }
                binding.btnLogin.state = PrimaryButton.State.ENABLED
            }
        }

        binding.tvForgetPassword.setOnClickListener {
            clearInput()
            nav().navigate(R.id.action_LoginPasswordFragment_to_ResetPasswordEmailFragment)
        }
    }

    override fun onResume() {
        binding.sifPassword.text = loginPassword
        super.onResume()
        if (CommonUtils.isRememberedMe()) {
            showRememberMeLayout()
        } else {
            showNotRememberMeLayout()
        }
    }

    fun getGreetings(currentHour: Int): String {
        return when (currentHour) {
            in 6..11 -> getString(R.string.morning_greeting)
            in 12..17 -> getString(R.string.afternoon_greeting)
            !in 6..17 -> getString(R.string.evening_greeting)
            else -> {
                getString(R.string.default_greeting)
            }
        }
    }

    private fun loginByPassword(username: String, password: String) {
        loginViewModel.loginwithFirstFactor(username, password).observe(this) {
            parseState(
                it,
                onLoading = {
                    binding.sifPassword.state = StandardInputField.State.INPUT
                    binding.sifPassword.clearInlineMessageView()
                    binding.btnLogin.state = PrimaryButton.State.LOADING
                },
                onSuccess = {
                    isComplete = true
                    maskedEmail = it.data?.maskedEmail.orEmpty()
                    maskedPhone = it.data?.maskedPhoneNumber.orEmpty()
                    binding.btnLogin.state = PrimaryButton.State.SUCCESS
                    appViewModel.secretToken.value = it.data?.token
                },
                onNetworkOrExceptionError = {
                    CommonUtils.showSystemError(binding.content)
                    binding.btnLogin.state = PrimaryButton.State.ENABLED
                },
                onInnerError = {
                    binding.sifPassword.errorMessage = it.errorMessage.orEmpty()
                    binding.sifPassword.state = StandardInputField.State.ERROR
                    binding.btnLogin.state = PrimaryButton.State.ENABLED
                }
            )
        }
    }

    override fun navigateBack(): Boolean {
        navigateBackInLoginPasswordPage()
        return true
    }

    private fun navigateBackInLoginPasswordPage() {
        if (CommonUtils.isRememberedMe()) {
            nav().safeNavigate(R.id.welcomeFragment)
        } else {
            nav().safeNavigate(R.id.loginStartFragment)
        }
    }

    private fun showRememberMeLayout() {
        binding.tvSwitchUser.visible()
        binding.tvAdTitle.visible()
    }

    private fun showNotRememberMeLayout() {
        binding.tvAdTitle.gone()
        binding.tvSwitchUser.gone()
    }


}