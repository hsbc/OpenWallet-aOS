package com.openwallet.ui.activity.fragment.register

import androidx.core.os.bundleOf
import com.openwallet.R
import com.openwallet.constants.PARAMS_REGISTER_NAME
import com.openwallet.ext.nav
import com.openwallet.ext.parseState
import com.openwallet.ext.safeNavigate
import com.openwallet.ui.activity.fragment.base.BasePasswordFragment
import com.openwallet.ui.activity.fragment.register.model.RegisterViewModel
import com.openwallet.util.CommonUtils

class RegisterPasswordFragment : BasePasswordFragment() {

    private val registerViewModel by viewModelFragment<RegisterViewModel>()
    private lateinit var registerUsername: String

    override fun initToolBar() {
        binding.toolbar.init(
            title = getTitle(),
            isShowBack = true,
            isShowCancel = true,
            onCancelClickListener = {
                nav().safeNavigate(R.id.startRegisterFragment)
                CommonUtils.clearInput()
            }
        )
    }

    override fun getParameters() {
        arguments?.let {
            registerUsername = it.getString(PARAMS_REGISTER_NAME).orEmpty()
        }
    }

    override fun getTitle(): String = getString(R.string.register)

    override fun getStepBar() {
        binding.stepBar.apply {
            stepCompleted = 2
            stepMax = 4
            title = String.format(
                getString(R.string.register_step_title_format), stepCompleted, stepMax,
                getString(R.string.register_step2_password)
            )
        }
    }

    override fun submit() {
        registerViewModel.registerPassword(registerUsername, binding.sifPasswordConfirm.text).observe(this) {
            parseState(
                it,
                onLoading = {
                    binding.sifPassword.state = StandardInputField.State.INPUT
                    binding.sifPasswordConfirm.state = StandardInputField.State.INPUT
                    binding.sifPassword.clearInlineMessageView()
                    binding.sifPasswordConfirm.clearInlineMessageView()
                    binding.btnContinue.state = PrimaryButton.State.LOADING
                },
                onSuccess = {
                    isComplete = true
                    binding.btnContinue.state = PrimaryButton.State.SUCCESS
                },
                onNetworkOrExceptionError = {
                    CommonUtils.showSystemError(binding.content)
                    binding.btnContinue.state = PrimaryButton.State.ENABLED
                },

                onInnerError = {
                    binding.sifPasswordConfirm.errorMessage = it.errorMessage.orEmpty()
                    binding.sifPasswordConfirm.state = StandardInputField.State.ERROR
                    binding.btnContinue.state = PrimaryButton.State.ENABLED
                }
            )
        }
    }

    override fun navigate() {
        nav().navigate(
            R.id.action_RegisterPasswordFragment_to_RegisterEmailFragment, bundleOf(
                PARAMS_REGISTER_NAME to registerUsername
            )
        )
    }

    override fun getButtonText(): String = getString(R.string.next)
}