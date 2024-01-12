package com.openwallet.ui.activity.fragment.login

import android.app.Dialog
import android.os.Bundle
import android.view.View
import com.openwallet.R
import com.openwallet.app.OpenWalletApplication.Companion.appViewModel
import com.openwallet.base.BaseFragment
import com.openwallet.databinding.FragmentLoginHomeBinding
import com.openwallet.ext.nav
import com.openwallet.ext.safeNavigate
import com.openwallet.ext.showLablewithIcon
import com.openwallet.util.CommonUtils
import com.openwallet.util.CommonUtils.clearInput

class LoginStartFragment : BaseFragment() {

    private val binding by viewBinding<FragmentLoginHomeBinding>()
    private lateinit var bottomSheetFragment: MobileDesignBottomSheetDialogFragment
    private var loginUsername = ""
        get() = appViewModel.userInput?.loginName.orEmpty()
    private var isRememberMe = false
        get() = appViewModel.userInput?.isRememberMeChecked == true

    override fun initViewAndData(view: View, savedInstanceStat: Bundle?) {

        binding.tvAdTitle.text = getGreetings(CommonUtils.getLocalHour())

        binding.toolbar.init(
            isShowBack = true,
            onBackClickListener = {
                navigateBackInLoginStartPage()
                CommonUtils.clearInput()
            },
            isShowCenterIcon = true,
        )

        binding.cbRemember.apply {
            isChecked = isRememberMe
            this.setOnCheckedChangeListener { _, isChecked ->
                appViewModel.userInput?.isRememberMeChecked = isChecked
            }
        }

//        binding.cbRemember.setOnCheckedChangeListener { _, isChecked ->
//            appViewModel.isRememberMe.value = isChecked
//        }

        binding.sifUserName.apply {
            text = loginUsername
            doAfterTextChanged { text ->
                binding.btnLoginNext.isEnabled = text.toString().length > 5
                appViewModel.userInput?.loginName = text.toString()
            }
        }

        binding.sifUserName.showLablewithIcon(
            getString(R.string.register_step1_username_enter), R.drawable.ic_help_circle
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
                        setTitle(title = getString(R.string.profile_edit_profile_labelText))
                    }
                })

            }
        }

        binding.btnLoginNext.setOnClickListener {
            if (binding.sifUserName.text.isEmpty()) return@setOnClickListener
            if (!CommonUtils.isVaildUserName(binding.sifUserName.text)) {
                binding.sifUserName.warningMessage = getString(R.string.enter_valid_username_hint)
                binding.sifUserName.state = StandardInputField.State.WARNING
                return@setOnClickListener
            }
            binding.sifUserName.clearInlineMessageView()
            binding.sifUserName.state = StandardInputField.State.INPUT
            nav().navigate(
                R.id.action_LoginStartFragment_to_LoginPasswordFragment,
            )
        }

        binding.tvForgetUsername.setOnClickListener {
            clearInput()
            nav().navigate(R.id.action_LoginStartFragment_to_FindUsernameEmailFragment)
        }

    }

    override fun onResume() {
        super.onResume()
        binding.sifUserName.text = loginUsername
        binding.cbRemember.isChecked = isRememberMe
    }

    override fun navigateBack(): Boolean {
        navigateBackInLoginStartPage()
        return true
    }

    private fun navigateBackInLoginStartPage() {
        clearInput()
        nav().safeNavigate(R.id.welcomeFragment)
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
}