package com.openwallet.ui.activity.fragment.profile.deleteprofile

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import com.openwallet.R
import com.openwallet.base.BaseFragment
import com.openwallet.constants.PARAMS_ACTION_FROM
import com.openwallet.constants.PARAMS_ACTION_FROM_DELETE_PROFILE
import com.openwallet.databinding.FragmentDeleteProfileBinding
import com.openwallet.databinding.LayoutCustomDialogBodyBinding
import com.openwallet.ext.nav
import com.openwallet.ext.parseState
import com.openwallet.util.CommonUtils
import kotlinx.coroutines.launch

class DeleteProfileFragment : BaseFragment() {
    private val binding by viewBinding<FragmentDeleteProfileBinding>()
    private val viewModel by viewModelFragment<DeleteProfileViewModel>()

    override fun initViewAndData(view: View, savedInstanceStat: Bundle?) {
        binding.toolbar.init(title = getString(R.string.toolbar_title_delete_profile), isShowMore = true, isUpdateTooBar = true)
        binding.btnDelete.setOnClickListener { showCancelConfirmDialog() }
        binding.btnCancel.setOnClickListener { nav().navigateUp() }

    }

    private fun showCancelConfirmDialog() {
        val dialogView = dialogView(
            dialogTitle = getString(R.string.delete_profile_dialog_title),
            dialogContentBody = generateAlertBody(getString(R.string.delete_profile_dialog_content)),
            positiveButtonText = getString(R.string.no)
        ) {
            negativeButtonText { getString(R.string.yes) }
        }

        val dialog = Dialog.newInstance(requireActivity(), dialogView)
        lifecycleScope.launch {
            val response = dialog.showSuspending(requireActivity(), "tag")
            handleResponse(response)
        }
    }

    private fun handleResponse(event: Dialog.DialogEvent?) {
        when (event) {
            //yes
            is Dialog.DialogEvent.NegativeButtonPressed -> {
                event.dialog.dismiss()
                viewModel.requestDeleteProfile().observe(this@DeleteProfileFragment) {
                    parseState(it,
                        onLoading = {
                            binding.btnDelete.state = PrimaryButton.State.LOADING
                        },
                        onSuccess = {
                            binding.btnDelete.state = PrimaryButton.State.SUCCESS
                            CommonUtils.clearLoginState()
                            nav().navigate(
                                R.id.action_deleteProfileFragment_to_StatusFragment,
                                bundleOf(PARAMS_ACTION_FROM to PARAMS_ACTION_FROM_DELETE_PROFILE)
                            )

                        },
                        onError = {
                            CommonUtils.showSystemError(binding.layoutContent)
                            binding.btnDelete.state = PrimaryButton.State.ENABLED
                        }
                    )
                }

            }
            //no
            is Dialog.DialogEvent.PositiveButtonPressed -> {
                event.dialog.dismiss()

            }

            else -> {}
        }
    }

    private fun generateAlertBody(bodyText: String): View {
        val bodyView = LayoutCustomDialogBodyBinding.inflate(layoutInflater, null, false)
        bodyView.alertDialogBodyContent.text = bodyText
        return bodyView.root
    }
}