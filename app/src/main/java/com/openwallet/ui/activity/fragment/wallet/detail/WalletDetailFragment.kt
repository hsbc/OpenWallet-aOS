package com.openwallet.ui.activity.fragment.wallet.detail

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.drakeet.multitype.MultiTypeAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.openwallet.R
import com.openwallet.base.BaseFragment
import com.openwallet.constants.RETROFIT_HOST
import com.openwallet.databinding.FragmentWalletDetailBinding
import com.openwallet.ext.nav
import com.openwallet.ext.navigateAction
import com.openwallet.ext.parseState
import com.openwallet.ext.visible
import com.openwallet.ui.activity.fragment.homedetail.ImageUriBean
import com.openwallet.ui.activity.fragment.wallet.model.RedeemStatus
import com.openwallet.ui.activity.fragment.wallet.model.WalletDetail
import com.openwallet.ui.activity.fragment.wallet.model.WalletDetailResponse
import com.openwallet.ui.activity.fragment.wallet.model.WalletViewModel
import com.openwallet.util.CommonUtils
import com.openwallet.util.DateUtils
import com.openwallet.util.DateUtils.FORMAT_YEAR_MONTH_DAY
import com.openwallet.util.DisplayUtil

class WalletDetailFragment : BaseFragment() {

    private val binding by viewBinding<FragmentWalletDetailBinding>()
    private val viewModel by viewModelActivity<WalletViewModel>()

    private var mAdapter = MultiTypeAdapter()
    private lateinit var mWallDetail: WalletDetail

    override fun isNeedStatusLayout() = true

    companion object{
        const val TAG = "WalletDetailFragment"
    }

    override fun initViewAndData(view: View, savedInstanceStat: Bundle?) {
        binding.toolbar.init(title = getString(R.string.wallet_detail_title))

        requestData()
        initViewPager()
        initClickListener()
    }

    private fun initViewPager() {
        mAdapter.register(WalletDetailGoldItemBinder())
        binding.goldViewPager.apply {
            setPageTransformer(MarginPageTransformer(DisplayUtil.dip2px(context, 5f)))
            offscreenPageLimit = 1

            val recyclerView = getChildAt(0)
            if (recyclerView != null && recyclerView is RecyclerView) {
                recyclerView.setPadding(DisplayUtil.dip2px(context, 14f), 0, DisplayUtil.dip2px(context, 14f), 0);
                recyclerView.clipToPadding = false

            }
            adapter = mAdapter

            TabLayoutMediator(binding.indicator, this) { _, _ -> }.attach()
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    if (position == mAdapter.itemCount - 1) {
                        binding.ivNext.alpha = 0.3f
                        binding.ivPrev.alpha = 1f
                        binding.ivNext.isClickable = false
                        binding.ivPrev.isClickable = true
                    } else if (position == 0) {
                        binding.ivPrev.alpha = 0.3f
                        binding.ivNext.alpha = 1f
                        binding.ivNext.isClickable = true
                        binding.ivPrev.isClickable = false
                    } else {
                        binding.ivPrev.alpha = 1f
                        binding.ivNext.alpha = 1f
                        binding.ivNext.isClickable = true
                        binding.ivPrev.isClickable = true
                    }
                }
            })
            binding.ivPrev.setOnClickListener { currentItem -= 1 }
            binding.ivNext.setOnClickListener { currentItem += 1 }
        }

    }

    private fun initClickListener() {
        binding.tvDetailMore.setOnClickListener {
            nav().navigateAction(R.id.action_WallDetailFragment_to_ProductDetailFragment)
        }
        binding.btnRedeem.setOnClickListener {
            nav().navigateAction(R.id.action_wallDetailFragment_to_redeemFragment)
        }
        binding.tvBackgroundMore.setOnClickListener {
            nav().navigateAction(R.id.action_wallDetailFragment_to_detailBackgroundFragment)
        }
        binding.tvGiftingRecordMore.setOnClickListener {
            nav().navigateAction(R.id.action_wallDetailFragment_to_detailDeliveryFragment)
        }
    }

    override fun requestData() {
        viewModel.getWalletDetail(viewModel.walletSummary.detailLinkUrl).observe(this) {
            parseState(it, stateLayout,
                onSuccess = { walletResponse ->
//                    binding.loading.gone()
                    binding.scrollView.visible()
                    binding.btnRedeem.visible()
                    handleSuccess(walletResponse)
                },
                onError = {
//                    binding.loading.gone()

                }, onLoading = {
//                    binding.loading.visible()
                }
            )
        }
    }

    private fun handleSuccess(walletDetailResponse: WalletDetailResponse) {
        mWallDetail = walletDetailResponse.data
        viewModel.walletDetail = mWallDetail
        with(mWallDetail) {
            binding.tvName.text = name
            binding.tvDetailTitle.text = name

            //Gold Gift Details
            binding.tvMaterial.valueText = material
            binding.tvFineness.valueText = fineness
            binding.tvWeight.valueText = weight
            binding.tvGoldGiftInfoBody.text = goldItemInformation

            //NFT background
            binding.tvNftId.valueText = CommonUtils.getNftIdString(serialNumber)
            binding.tvStatus.valueText = status.displayName

            //Gift record
            binding.tvOwned.valueText = ownedBy
            when (status) {
                RedeemStatus.REDEEMED -> {
                    binding.tvDate.keyText = "Redemption date"
                }

                else -> {
                    binding.tvDate.keyText = "Expiration date"
                }
            }

            binding.tvRecordInfo.text = when (status) {
                RedeemStatus.UNREDEEMED, RedeemStatus.EXPIRED -> getString(R.string.wallet_nft_record_desc_expired)
                RedeemStatus.REDEEMED, RedeemStatus.PROCESSING -> getString(R.string.wallet_nft_record_desc_redeemed)
            }

            binding.tvDate.valueText = DateUtils.convertTimeToString(dateTime.toLong(), FORMAT_YEAR_MONTH_DAY)
            binding.btnRedeem.state = if (status == RedeemStatus.UNREDEEMED) PrimaryButton.State.ENABLED else PrimaryButton.State.DISABLED

            val items = imageList
                .filter { !it.endsWith(".webp") && !it.endsWith("placeholder.png") }
                .map { ImageUriBean("$RETROFIT_HOST/$it") }
            mAdapter.items = items
            mAdapter.notifyDataSetChanged()
        }
    }
}