package com.openwallet.ui.activity.fragment.product

import android.os.Bundle
import android.view.View
import com.openwallet.R
import com.openwallet.base.BaseFragment
import com.openwallet.databinding.FragmentProductDetailsBinding
import com.openwallet.ui.activity.fragment.wallet.model.WalletViewModel

class ProductDetailFragment : BaseFragment() {

    private val binding by viewBinding<FragmentProductDetailsBinding>()
    private val viewModel by viewModelActivity<WalletViewModel>()

    override fun initViewAndData(view: View, savedInstanceStat: Bundle?) {
        binding.toolbar.init(title = getString(R.string.wallet_detail_product_details))

        viewModel.walletDetail?.run {
            binding.tvDetailTitle.text = name
            binding.tvMaterial.valueText = material
            binding.tvFineness.valueText = fineness
            binding.tvWeight.valueText = weight
            binding.tvExtraInfo.text = goldItemInformation
        }
    }
}