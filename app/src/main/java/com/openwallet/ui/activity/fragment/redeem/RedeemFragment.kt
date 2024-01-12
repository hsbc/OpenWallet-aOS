package com.openwallet.ui.activity.fragment.redeem

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.openwallet.R
import com.openwallet.app.OpenWalletApplication.Companion.appViewModel
import com.openwallet.base.BaseFragment
import com.openwallet.base.OnBackPressedListener
import com.openwallet.databinding.FragmentRedeemBinding
import com.openwallet.databinding.LayoutCustomDialogBodyBinding
import com.openwallet.ext.nav
import com.openwallet.ext.navigateAction
import com.openwallet.ext.parseState
import com.openwallet.ui.activity.fragment.redeem.model.BankInfoResponse
import com.openwallet.ui.activity.fragment.redeem.model.RedeemResponse
import com.openwallet.ui.activity.fragment.wallet.model.WalletViewModel
import kotlinx.coroutines.launch


class RedeemFragment : BaseFragment(), OnBackPressedListener {

    private val binding by viewBinding<FragmentRedeemBinding>()
    private val viewModel by viewModelActivity<WalletViewModel>()

    override fun initViewAndData(view: View, savedInstanceStat: Bundle?) {
        binding.toolbar.init(
            isShowBack = false,
            title = getString(R.string.redeem_fragment_title),
            isShowCancel = true,
            onCancelClickListener = {
                showCancelConfirmDialog()
            }
        )

//        binding.radioButton.setOnCheckedChangeListener { buttonView, isChecked ->
//            binding.btnSubmit.state = if (isChecked) PrimaryButton.State.ENABLED else PrimaryButton.State.DISABLED
//        }
        binding.btnSubmit.setOnClickListener {
            binding.btnSubmit.state = PrimaryButton.State.LOADING
            viewModel.requestRedeem(viewModel.walletSummary.nftId).observe(this) {
                parseState(it,
                    onSuccess = { response -> handleResponseSuccess(response) },
                    onError = { appException ->
                        TopSnackbar.make(
                            binding.content, appException.errorMessage.toString(),
                            TopSnackbar.Style.WARNING
                        ).setTopMargin(
                            view.resources.getDimension(R.dimen.dimen_52dp).toInt()
                        ).show()
                        binding.btnSubmit.state = PrimaryButton.State.ENABLED
                    }
                )
            }

        }

        requestData()
//        initTermsText()

    }

    override fun isNeedStatusLayout() = true


    override fun requestData() {
        viewModel.getBankInfo().observe(this) {
            parseState(it, stateLayout,
                onSuccess = { bankInfoResponse ->
//                    binding.layoutContent.visible()
//                    binding.loading.gone()
                    handleSuccess(bankInfoResponse)
                }

            )
        }
    }

    private fun handleSuccess(bankInfoResponse: BankInfoResponse) {
        bankInfoResponse.data?.run {
            binding.tvOwned.text = legalName.orEmpty()
            binding.tvPhoneNumber.text = "+$phoneCountryCode $phoneNumber"
            viewModel.bankInfo = this
        }
    }


    private fun handleResponseSuccess(redeemResponse: RedeemResponse) {
        binding.btnSubmit.state = PrimaryButton.State.SUCCESS
        nav().navigateAction(R.id.action_redeemFragment_to_successRedeemFragment)
        appViewModel.isRedeemStatusChanged.value = true
    }

    private fun initTermsText() {
        val redeemTermsText = getString(R.string.redeem_terms)
        val index = redeemTermsText.indexOf("terms and conditions")
        val spStr = SpannableString(redeemTermsText)
        spStr.setSpan(object : ClickableSpan() {
            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.color = Color.RED //设置文件颜色
                ds.isUnderlineText = true //设置下划线
            }

            override fun onClick(widget: View) {
                nav().navigateAction(R.id.action_redeemFragment_to_TermsAndConditionsProfileFragment)
            }

        }, index, spStr.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

//        binding.tvTerms.text = spStr
//        binding.tvTerms.highlightColor = Color.TRANSPARENT //设置点击后的颜色为透明，否则会一直出现高亮
//        binding.tvTerms.movementMethod = LinkMovementMethod.getInstance() //开始响应点击事件
    }

    private fun showCancelConfirmDialog() {
        val dialogView = dialogView(
            dialogTitle = getString(com.openwallet.R.string.redeem_dialog_title),
            dialogContentBody = generateAlertBody(getString(com.openwallet.R.string.redeem_dialog_content)),
            positiveButtonText = getString(com.openwallet.R.string.no)
        ) {
            negativeButtonText { getString(com.openwallet.R.string.yes) }
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
                nav().navigateUp()
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


    override fun navigateBack(): Boolean {
        return true

//        binding.btnSubmit.setOnClickListener{
//            nav().navigateAction(R.id.action_redeemFragment_to_successRedeemFragment)
//        }

    }
}