package com.openwallet.ui.activity.fragment.resetpassword

import androidx.core.os.bundleOf
import com.openwallet.R
import com.openwallet.constants.PARAMS_ACTION_FROM
import com.openwallet.constants.PARAMS_ACTION_FROM_RESET_PASSWORD_SUCCESS
import com.openwallet.constants.PARAMS_ACTION_FROM_RESET_PASSWORD_SUCCESS_WITH_LOGIN
import com.openwallet.ext.nav
import com.openwallet.ext.parseState
import com.openwallet.ext.safeNavigate
import com.openwallet.ui.activity.fragment.base.BasePasswordFragment
import com.openwallet.ui.activity.fragment.logout.model.LogoutViewModel
import com.openwallet.ui.activity.fragment.resetpassword.model.ResetPasswordViewModel
import com.openwallet.util.CommonUtils

class ResetPasswordConfirmationFragment : BasePasswordFragment() {

    private val resetPasswordViewModel by viewModelFragment<ResetPasswordViewModel>()
    private val logoutViewModel by viewModelFragment<LogoutViewModel>()

    override fun getParameters() {
    }

    override fun initToolBar() {
        binding.toolbar.init(
            title = getTitle(),
            isShowBack = true,
            isShowCancel = true,
            onCancelClickListener = {
                if (CommonUtils.isLogin()) {
                    nav().safeNavigate(R.id.changePasswordEnterPassword)
                    CommonUtils.clearInput()
                } else {
                    nav().safeNavigate(R.id.loginPasswordFragment)
                    CommonUtils.clearInput()
                }
            }
        )
    }

    override fun getTitle(): String = getString(R.string.reset_password_title)

    override fun getStepBar() {
        binding.stepBar.apply {
            stepCompleted = 3
            stepMax = 3
            title = String.format(
                getString(R.string.register_step_title_format), stepCompleted, stepMax,
                getString(R.string.reset_password_step3_password)
            )
        }
    }

    override fun submit() {
        resetPasswordViewModel.resetPassword(binding.sifPasswordConfirm.text).observe(this) {
            parseState(
                it,
                onLoading = {
                    binding.btnContinue.state = PrimaryButton.State.LOADING
                },
                onSuccess = {
                    //need to logout user after reset password
                    if (CommonUtils.isLogin()) {
                        logout()
                    } else {
                        isComplete = true
                        binding.btnContinue.state = PrimaryButton.State.SUCCESS
                        CommonUtils.clearInput()
                    }
                },
                onInnerError = {
                    binding.sifPasswordConfirm.errorMessage = it.errorMessage.orEmpty()
                    binding.sifPasswordConfirm.state = StandardInputField.State.ERROR
                    binding.btnContinue.state = PrimaryButton.State.ENABLED
                },
                onNetworkOrExceptionError = {
                    binding.btnContinue.state = PrimaryButton.State.ENABLED
                    CommonUtils.showSystemError(binding.content)
                }
            )
        }
    }

    override fun navigate() {
        if (CommonUtils.isLogin()) {
            CommonUtils.clearLoginState()
            nav().navigate(
                R.id.action_ResetPasswordComfirmationFragment_to_StatusFragment, bundleOf(
                    PARAMS_ACTION_FROM to PARAMS_ACTION_FROM_RESET_PASSWORD_SUCCESS_WITH_LOGIN
                )
            )
        } else {
            nav().navigate(
                R.id.action_ResetPasswordComfirmationFragment_to_StatusFragment, bundleOf(
                    PARAMS_ACTION_FROM to PARAMS_ACTION_FROM_RESET_PASSWORD_SUCCESS
                )
            )
        }

    }

    override fun getButtonText(): String = getString(R.string.submit)

    private fun logout() {
        logoutViewModel.logout().observe(this) {
            parseState(
                it,
                onLoading = {},
                onSuccess = {
                    isComplete = true
                    binding.btnContinue.state = PrimaryButton.State.SUCCESS
                }, onError = {
                    binding.btnContinue.state = PrimaryButton.State.ENABLED
                    binding.sifPasswordConfirm.errorMessage = it.errorMessage.orEmpty()
                    binding.sifPasswordConfirm.state = StandardInputField.State.ERROR
                })
        }
    }


}