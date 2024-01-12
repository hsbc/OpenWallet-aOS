package com.openwallet.ui.activity.fragment.profile.changepassword

import androidx.core.os.bundleOf
import com.openwallet.R
import com.openwallet.constants.PARAMS_ACTION_FROM
import com.openwallet.constants.PARAMS_ACTION_FROM_CHANGE_PASSWORD_SUCCESS
import com.openwallet.ext.nav
import com.openwallet.ext.parseState
import com.openwallet.ext.safeNavigate
import com.openwallet.ui.activity.fragment.base.BasePasswordFragment
import com.openwallet.ui.activity.fragment.logout.model.LogoutViewModel
import com.openwallet.ui.activity.fragment.profile.changepassword.model.ChangePasswordViewModel
import com.openwallet.util.CommonUtils

class ChangePasswordConfirmationFragment : BasePasswordFragment() {

    private val changePasswordViewModel by viewModelFragment<ChangePasswordViewModel>()
    private val logoutViewModel by viewModelFragment<LogoutViewModel>()

    override fun getParameters() {
    }

    override fun getTitle(): String = getString(R.string.change_password_title)

    override fun getStepBar() {
        binding.stepBar.apply {
            stepMax = 3
            stepCompleted = 3
            title = String.format(
                getString(R.string.register_step_title_format), stepCompleted, stepMax,
                getString(R.string.change_password_step_new_password)
            )
        }
    }

    override fun submit() {
        changePasswordViewModel.changePassword(binding.sifPasswordConfirm.text).observe(this) {
            parseState(
                it,
                onLoading = {
                    binding.btnContinue.state = PrimaryButton.State.LOADING
                },
                onSuccess = {
                    logout()
                },
                onNetworkOrExceptionError = {
                    binding.btnContinue.state = PrimaryButton.State.ENABLED
                    CommonUtils.showSystemError(binding.content)
                },
                onInnerError = {
                    binding.btnContinue.state = PrimaryButton.State.ENABLED
                    binding.sifPasswordConfirm.errorMessage = it.errorMessage.orEmpty()
                    binding.sifPasswordConfirm.state = StandardInputField.State.ERROR
                })
        }
    }

    override fun navigate() {
        nav().navigate(
            R.id.action_ChangePasswordConfirmationFragment_to_StatusFragment,
            bundleOf(PARAMS_ACTION_FROM to PARAMS_ACTION_FROM_CHANGE_PASSWORD_SUCCESS)
        )
    }

    override fun getButtonText(): String = getString(R.string.submit)

    override fun initToolBar() {
        binding.toolbar.init(
            isShowBack = true,
            isShowCancel = true,
            onCancelClickListener = {
                nav().safeNavigate(R.id.mainFragment)
                CommonUtils.clearInput()
            }
        )
    }

    private fun logout() {
        logoutViewModel.logout().observe(this) {
            parseState(it,
                onLoading = {

                }, onSuccess = {
                    isComplete = true
                    binding.btnContinue.state = PrimaryButton.State.SUCCESS
                    CommonUtils.clearLoginState()
                }, onError = {
                    binding.btnContinue.state = PrimaryButton.State.ENABLED
                    binding.sifPasswordConfirm.errorMessage = it.errorMessage.orEmpty()
                    binding.sifPasswordConfirm.state = StandardInputField.State.ERROR
                })
        }
    }
}