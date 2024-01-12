package com.openwallet.ui.activity.fragment.profile.changepassword

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.openwallet.R
import com.openwallet.app.OpenWalletApplication
import com.openwallet.base.BaseFragment
import com.openwallet.databinding.FragmentChangepasswordEnterPasswordBinding
import com.openwallet.ext.nav
import com.openwallet.ext.parseState
import com.openwallet.ext.showLablewithIcon
import com.openwallet.ext.showPassword
import com.openwallet.ui.activity.fragment.profile.changepassword.model.ChangePasswordViewModel
import com.openwallet.util.CommonUtils

class EnterCurrentPasswordFragment : BaseFragment() {

    private val binding by viewBinding<FragmentChangepasswordEnterPasswordBinding>()
    private val viewModel by viewModelFragment<ChangePasswordViewModel>()
    private var isComplete = false
    private var isPasswordVisible = false

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

        binding.toolbar.init(
            title = getString(R.string.change_password_title),
            isShowBack = true,
            isShowCancel = true,
            onBackClickListener = {
                nav().navigateUp()
                CommonUtils.clearInput()
            },
            onCancelClickListener = {
                nav().navigateUp()
                CommonUtils.clearInput()
            }
        )

        binding.stepBar.apply {
            stepCompleted = 1
            stepMax = 3
            title = String.format(
                getString(R.string.register_step_title_format), stepCompleted, stepMax,
                getString(R.string.change_password_step_old_password)
            )
        }

        binding.sifPassword.showLablewithIcon(
            getString(R.string.change_password_step1),
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
            setIconOnClickListener {
                isPasswordVisible = !isPasswordVisible
                showPassword(isPasswordVisible)
            }
            doAfterTextChanged {
                binding.btnNext.isEnabled = it.toString().length > 7
            }
        }

        binding.tvForgetPassword.setOnClickListener {
            nav().navigate(R.id.action_EnterCurrentPasswordFragment_to_ResetPasswordEmailFragment)
        }


        binding.btnNext.apply {
            setOnClickListener {
                viewModel.changePasswordWithFirstFactor(binding.sifPassword.text)
                    .observe(this@EnterCurrentPasswordFragment) {
                        parseState(
                            it,
                            onLoading = {
                                binding.btnNext.state = PrimaryButton.State.LOADING
                            },
                            onSuccess = {
                                isComplete = true
                                binding.btnNext.state = PrimaryButton.State.SUCCESS
                                OpenWalletApplication.appViewModel.secretToken.value = it.data?.token.orEmpty()
                            },
                            onNetworkOrExceptionError = {
                                CommonUtils.showSystemError(binding.content)
                                binding.btnNext.state = PrimaryButton.State.ENABLED
                            },
                            onError = {
                                binding.btnNext.state = PrimaryButton.State.ENABLED
                                binding.sifPassword.errorMessage = it.errorMessage.orEmpty()
                                binding.sifPassword.state = StandardInputField.State.ERROR
                            })
                    }
            }

            setOnCompleteListener {
                if (isComplete) {
                    binding.btnNext.state = PrimaryButton.State.ENABLED
                    nav().navigate(R.id.action_EnterCurrentPasswordFragment_to_ChangePasswordMethodFragment)
                }
            }
        }

    }
}