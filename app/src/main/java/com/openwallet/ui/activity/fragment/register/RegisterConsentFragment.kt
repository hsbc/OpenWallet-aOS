package com.openwallet.ui.activity.fragment.register

import android.os.Bundle
import android.text.Html
import android.view.View
import androidx.core.os.bundleOf
import com.openwallet.R
import com.openwallet.app.OpenWalletApplication.Companion.appViewModel
import com.openwallet.base.BaseFragment
import com.openwallet.constants.PARAMS_ACTION_FROM
import com.openwallet.constants.PARAMS_ACTION_FROM_REGISTER_FAIL
import com.openwallet.constants.PARAMS_ACTION_FROM_REGISTER_SUCCESS
import com.openwallet.constants.PARAMS_REGISTER_NAME
import com.openwallet.databinding.FragmentTermsAndConditionsBinding
import com.openwallet.ext.*
import com.openwallet.ui.activity.fragment.profile.TermsAndConditionsViewModel
import com.openwallet.ui.activity.fragment.register.model.RegisterViewModel
import com.openwallet.ui.activity.fragment.register.model.TermsAndConditionRequestBody
import com.openwallet.util.CommonUtils

class RegisterConsentFragment : BaseFragment() {

    private val binding by viewBinding<FragmentTermsAndConditionsBinding>()
    private val registerViewModel by viewModelFragment<RegisterViewModel>()
    private val termsAndConditionsViewModel by viewModelFragment<TermsAndConditionsViewModel>()
    private lateinit var registerUsername: String
    private var isComplete = false

    override fun isNeedStatusLayout(): Boolean = true

    override fun requestData() {
        requestConsentData()
    }

    override fun initViewAndData(view: View, savedInstanceStat: Bundle?) {
        arguments?.let {
            registerUsername = it.getString(PARAMS_REGISTER_NAME).orEmpty()
        }

        binding.toolbar.init(
            isShowBack = true,
            isShowCancel = true,
            onCancelClickListener = {
                nav().safeNavigate(R.id.startRegisterFragment)
                CommonUtils.clearInput()
            },
            title = getString(R.string.intro_terms_and_conditions)
        )
        binding.cbTandc.setOnCheckedChangeListener { _, isChecked ->
            binding.btnTandc.isEnabled = isChecked
        }
        binding.btnTandc.apply {
            setOnClickListener {
                registerViewModel.register(registerUsername).observe(this@RegisterConsentFragment) {
                    parseState(
                        it,
                        onLoading = {
                            binding.btnTandc.state = PrimaryButton.State.LOADING
                        },
                        onSuccess = {
                            if (it.status == true) {
                                isComplete = true
                                binding.btnTandc.state = PrimaryButton.State.SUCCESS
                            } else {
                                binding.btnTandc.state = PrimaryButton.State.ENABLED
                                nav().navigate(
                                    R.id.action_RegisterConsentFragment_to_StatusFragment,
                                    bundleOf(PARAMS_ACTION_FROM to PARAMS_ACTION_FROM_REGISTER_FAIL)
                                )
                            }
                            CommonUtils.clearInput()
                        },
                        onError = {
                            nav().navigate(
                                R.id.action_RegisterConsentFragment_to_StatusFragment,
                                bundleOf(PARAMS_ACTION_FROM to PARAMS_ACTION_FROM_REGISTER_FAIL)
                            )
                            binding.btnTandc.state = PrimaryButton.State.ENABLED
                            CommonUtils.clearInput()
                        }
                    )
                }
            }

            setOnCompleteListener {
                if (isComplete) {
                    nav().navigate(
                        R.id.action_RegisterConsentFragment_to_StatusFragment, bundleOf(
                            PARAMS_ACTION_FROM to PARAMS_ACTION_FROM_REGISTER_SUCCESS
                        )
                    )
                }
            }
        }
        requestConsentData()
    }

    private fun requestConsentData() {
        termsAndConditionsViewModel.getTermAndConditions(TermsAndConditionRequestBody(appViewModel.secretToken.value.orEmpty()))
            .observe(this) {
                parseState(
                    it,
                    statusView = stateLayout,
                    onSuccess = { response ->
                        response.data?.run {
                            val htmlString = content?.replace("\n", "<br>").orEmpty()
                            binding.content.text = Html.fromHtml(htmlString, Html.FROM_HTML_MODE_COMPACT)
                        }
                    }
                )
            }
    }
}