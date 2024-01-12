package com.openwallet.ui.activity.fragment.register

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import com.openwallet.R
import com.openwallet.app.OpenWalletApplication.Companion.appViewModel
import com.openwallet.base.BaseFragment
import com.openwallet.constants.PARAMS_REGISTER_NAME
import com.openwallet.databinding.FragmentRegisterNameBinding
import com.openwallet.ext.*
import com.openwallet.ui.activity.fragment.register.model.RegisterViewModel
import com.openwallet.util.CommonUtils

class RegisterNameFragment : BaseFragment() {
    private val binding by viewBinding<FragmentRegisterNameBinding>()
    private lateinit var bottomSheetFragment: MobileDesignBottomSheetDialogFragment
    private var isComplete: Boolean? = false
    private val registerViewModel by viewModelFragment<RegisterViewModel>()


    override fun initViewAndData(view: View, savedInstanceStat: Bundle?) {

        appViewModel.userInput?.run {
            binding.sifUserName.text = name.orEmpty()
            binding.btnContinue.isEnabled = name.orEmpty().isNotEmpty()
        }

        binding.toolbar.init(
            isShowCancel = true,
            isShowBack = true,
            title = getString(R.string.register),
            onBackClickListener = {
                nav().navigateUp()
                CommonUtils.clearInput()
            },
            onCancelClickListener = {
                nav().safeNavigate(R.id.startRegisterFragment)
                CommonUtils.clearInput()
            }
        )

        binding.stepBar.apply {
            stepCompleted = 1
            stepMax = 4
            title = String.format(
                getString(R.string.register_step_title_format), stepCompleted, stepMax,
                getString(R.string.register_step1_username)
            )
        }

        binding.btnContinue.setOnClickListener {
            nav().navigate(R.id.action_RegisterNameFragment_to_RegisterPasswordFragment)
        }
        binding.sifUserName.showLablewithIcon(
            getString(R.string.register_step1_username_enter),
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
                        setTitle(title = getString(R.string.register_name_bottomsheet_hint))
                    }
                })

            }
        }

        binding.sifUserName.apply {
            doAfterTextChanged {
                binding.btnContinue.isEnabled = it.toString().length > 5
                appViewModel.userInput?.name = it.toString()
            }
        }

//        binding.sifUserName.apply {
//            setEditTextFocusChangeListener { _, hasFocus ->
//                if (hasFocus) {
//                    clearInlineMessageView()
//                }
//                else{
//                    if (!CommomUtils.isVaildUserName(binding.sifUserName.text)) {
//                        binding.sifUserName.errorMessage = ERROR_MESSAGE_USER_NAME_FORMAT_INVALID
//                        binding.sifUserName.state = StandardInputField.State.ERROR
//                    }
//                    else{
//                        binding.btnContinue.isEnabled = true
//                    }
//                }
//            }
//        }


        binding.btnContinue.apply {
            setOnClickListener {
                if (binding.sifUserName.text.isEmpty()) return@setOnClickListener
                if (!CommonUtils.isVaildUserName(binding.sifUserName.text)) {
                    binding.sifUserName.warningMessage = getString(R.string.register_username_info)
                    binding.sifUserName.state = StandardInputField.State.WARNING
                    return@setOnClickListener
                }
                activity?.hideKeyboard()
                registerViewModel.registerUserName(binding.sifUserName.text).observe(this@RegisterNameFragment) {
                    parseState(
                        it,
                        onLoading = {
                            binding.sifUserName.state = StandardInputField.State.INPUT
                            binding.sifUserName.clearInlineMessageView()
                            binding.btnContinue.state = PrimaryButton.State.LOADING
                        },
                        onSuccess = {
                            isComplete = true
                            binding.btnContinue.state = PrimaryButton.State.SUCCESS
                            appViewModel.secretToken.value = it.data?.token.orEmpty()

                        },
                        onInnerError = {
                            binding.sifUserName.errorMessage = it.errorMessage.orEmpty()
                            binding.sifUserName.state = StandardInputField.State.ERROR
                            binding.btnContinue.state = PrimaryButton.State.ENABLED
                        },
                        onNetworkOrExceptionError = {
                            CommonUtils.showSystemError(binding.content)
                            binding.btnContinue.state = PrimaryButton.State.ENABLED
                        }
                    )
                }
            }
            setOnCompleteListener {
                if (isComplete == true) {
                    nav().navigate(
                        R.id.action_RegisterNameFragment_to_RegisterPasswordFragment,
                        bundleOf(PARAMS_REGISTER_NAME to binding.sifUserName.text)
                    )
                    binding.btnContinue.state = PrimaryButton.State.ENABLED
                }
            }
        }
    }
}