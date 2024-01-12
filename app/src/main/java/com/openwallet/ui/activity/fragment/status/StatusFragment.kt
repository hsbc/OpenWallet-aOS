package com.openwallet.ui.activity.fragment.status

import android.os.Bundle
import android.view.View
import androidx.navigation.NavOptions
import com.openwallet.R
import com.openwallet.app.OpenWalletApplication
import com.openwallet.base.BaseFragment
import com.openwallet.constants.*
import com.openwallet.databinding.FragmentStatusBinding
import com.openwallet.ext.gone
import com.openwallet.ext.nav
import com.openwallet.ext.safeNavigate
import com.openwallet.ext.visible
import com.openwallet.util.CommonUtils
import com.openwallet.util.CommonUtils.clearRememberMeState

class StatusFragment : BaseFragment() {

    private val binding by viewBinding<FragmentStatusBinding>()

    override fun initViewAndData(view: View, savedInstanceStat: Bundle?) {
        arguments?.getString(PARAMS_ACTION_FROM)?.let {
            when (it) {
                PARAMS_ACTION_FROM_REGISTER_SUCCESS -> {
                    binding.tvAdTitle.text = getString(R.string.register_success)
                    binding.btnAction.text = getString(R.string.logon)
                    binding.tvAdContent.text = getString(R.string.register_success_detail)
                    binding.ivHomeBanner.setImageResource(R.drawable.congratulations)
                    hideFindUserNameView()
                }

                PARAMS_ACTION_FROM_REGISTER_FAIL -> {
                    binding.tvAdTitle.text = getString(R.string.error)
                    binding.btnAction.text = getString(R.string.goBack)
                    binding.tvAdContent.text = getString(R.string.register_fail)
                    binding.ivHomeBanner.setImageResource(R.drawable.error)
                    hideFindUserNameView()
                }

                PARAMS_ACTION_FROM_LOGIN_FAIL -> {
                    binding.tvAdTitle.text = getString(R.string.error)
                    binding.btnAction.text = getString(R.string.goBack)
                    binding.tvAdContent.text = getString(R.string.register_fail)
                    binding.ivHomeBanner.setImageResource(R.drawable.error)
                    hideFindUserNameView()
                }
                PARAMS_ACTION_FROM_RESET_PASSWORD_SUCCESS -> {
                    binding.tvAdTitle.text = getString(R.string.register_success)
                    binding.btnAction.text = getString(R.string.logon)
                    binding.tvAdContent.text = getString(R.string.reset_password_complete_message)
                    binding.ivHomeBanner.setImageResource(R.drawable.congratulations_password)
                    hideFindUserNameView()
                }
                PARAMS_ACTION_FROM_RESET_PASSWORD_SUCCESS_WITH_LOGIN -> {
                    binding.tvAdTitle.text = getString(R.string.register_success)
                    binding.btnAction.text = getString(R.string.logon)
                    binding.tvAdContent.text = getString(R.string.reset_password_complete_message)
                    binding.ivHomeBanner.setImageResource(R.drawable.congratulations_password)
                    hideFindUserNameView()
                }
                PARAMS_ACTION_FROM_FIND_NAME_SUCCESS -> {
                    binding.tvAdTitle.text = getString(R.string.register_success)
                    binding.btnAction.text = getString(R.string.logon)
                    binding.tvAdContent.text = getString(R.string.find_username_success)
                    binding.ivHomeBanner.setImageResource(R.drawable.congratulations)
                    showFindUserNameView()
                    with(arguments?.getString(PARMAS_FINDED_NAME).orEmpty()) {
                        binding.tvUsername.text = this
                        OpenWalletApplication.appViewModel.userInput?.loginName = this
                    }
                }
                PARAMS_ACTION_FROM_CHANGE_PASSWORD_SUCCESS -> {
                    binding.tvAdTitle.text = getString(R.string.register_success)
                    binding.btnAction.text = getString(R.string.logon)
                    binding.tvAdContent.text = getString(R.string.change_password_complete_message)
                    binding.ivHomeBanner.setImageResource(R.drawable.congratulations_password)
                    hideFindUserNameView()
                }

                PARAMS_ACTION_FROM_DELETE_PROFILE -> {
                    binding.tvAdTitle.text = getString(R.string.delete_profile_success_page_title)
                    binding.tvAdContent.text = getString(R.string.delete_profile_success_page_body)
                    binding.btnAction.text = getString(R.string.got_it)
                    binding.ivHomeBanner.setImageResource(R.drawable.congratulations)
                    hideFindUserNameView()
                }
            }
        }

        binding.btnAction.setOnClickListener {
            arguments?.getString(PARAMS_ACTION_FROM)?.let {
                when (it) {
                    PARAMS_ACTION_FROM_REGISTER_SUCCESS -> {
                        clearRememberMeState()
                        nav().navigate(
                            R.id.action_StatusFragment_to_LoginStartFragment, null,
                            NavOptions.Builder().setPopUpTo(R.id.welcomeFragment, false).build()
                        )
                    }
                    PARAMS_ACTION_FROM_REGISTER_FAIL -> nav().safeNavigate(R.id.welcomeFragment)
                    PARAMS_ACTION_FROM_LOGIN_FAIL ->  nav().safeNavigate(R.id.welcomeFragment)
                    PARAMS_ACTION_FROM_RESET_PASSWORD_SUCCESS -> if (CommonUtils.isRememberedMe()) {
                        nav().safeNavigate(R.id.loginPasswordFragment, false)
                    } else {
                        nav().safeNavigate(R.id.loginStartFragment, false)
                    }
                    PARAMS_ACTION_FROM_RESET_PASSWORD_SUCCESS_WITH_LOGIN ->
                        if (CommonUtils.isRememberedMe()) {
                            nav().safeNavigate(R.id.loginPasswordFragment)
                        } else {
                            nav().safeNavigate(R.id.loginStartFragment)
                        }

                    PARAMS_ACTION_FROM_FIND_NAME_SUCCESS -> nav().safeNavigate(
                        R.id.loginStartFragment, false
                    )
                    PARAMS_ACTION_FROM_DELETE_PROFILE -> nav().safeNavigate(R.id.welcomeFragment)
                    PARAMS_ACTION_FROM_CHANGE_PASSWORD_SUCCESS ->
                        if (CommonUtils.isRememberedMe()) {
                            nav().safeNavigate(R.id.loginPasswordFragment)
                        } else {
                            nav().safeNavigate(R.id.loginStartFragment)
                        }
                }
            }
        }

    }

    private fun showFindUserNameView() {
        binding.tvFindUsernameTtitle.visible()
        binding.tvUsername.visible()
    }

    private fun hideFindUserNameView() {
        binding.tvFindUsernameTtitle.gone()
        binding.tvUsername.gone()
    }

    override fun navigateBack(): Boolean {
        return true
    }
}