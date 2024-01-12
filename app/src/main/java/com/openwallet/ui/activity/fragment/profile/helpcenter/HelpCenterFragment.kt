package com.openwallet.ui.activity.fragment.profile.helpcenter

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import com.openwallet.base.BaseFragment
import com.openwallet.databinding.FragmentHelpCenterBinding
import com.openwallet.ext.nav
import com.openwallet.ext.parseState
import com.openwallet.util.CommonUtils


class HelpCenterFragment : BaseFragment() {

    private val binding by viewBinding<FragmentHelpCenterBinding>()
    private val helpCenterViewModel by viewModelFragment<HelpCenterViewModel>()
    private val maxWordLimit = 2000

    private fun updateWordLimit() {
        if(binding.input.text == null) {
            return
        }
        if (TextUtils.isEmpty(binding.input.text.trim())) {
            binding.submitButton.state = PrimaryButton.State.DISABLED
        }
        else{
            binding.submitButton.state = PrimaryButton.State.ENABLED
            var length = binding.input.text.trim().length
            binding.count.text = "$length/$maxWordLimit"
        }
    }

    override fun initViewAndData(view: View, savedInstanceStat: Bundle?) {
        binding.submitButton.state = PrimaryButton.State.DISABLED
        binding.toolbar.init(title = "Help center", isShowMore = true, isUpdateTooBar = true)

        binding.input.apply {
            addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable) {
                    if (TextUtils.isEmpty(binding.input.text.trim()) || binding.input.text.trim().length > maxWordLimit) {
                        binding.submitButton.state = PrimaryButton.State.DISABLED
                    }
                }

                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    updateWordLimit()
                }

            })
        }
        //init the word limit tip
        updateWordLimit()

        binding.submitButton.setOnClickListener {
            helpCenterViewModel.sendFeedback(FeedbackRequestBody(binding.input.text.toString().trim())).observe(this) {
                parseState(it,
                    onLoading = {
                        binding.submitButton.state = PrimaryButton.State.LOADING
                    },
                    onSuccess = {
                        binding.submitButton.state = PrimaryButton.State.SUCCESS
                        //go back to profile page
                        nav().navigateUp()
                    },
                    onError = {
                        binding.submitButton.state = PrimaryButton.State.ENABLED
                        CommonUtils.showSystemError(binding.layoutContent)
                    })
            }
        }


        binding.cancelButton.setOnClickListener { nav().navigateUp()}
    }

}