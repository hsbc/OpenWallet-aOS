package com.openwallet.ui.activity.fragment.wallet

import android.os.Bundle
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.integration.webp.decoder.WebpDrawable
import com.bumptech.glide.integration.webp.decoder.WebpDrawableTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.openwallet.R
import com.openwallet.app.OpenWalletApplication.Companion.appViewModel
import com.openwallet.base.BaseFragment
import com.openwallet.constants.RETROFIT_HOST
import com.openwallet.databinding.FragmentWalletBinding
import com.openwallet.ext.nav
import com.openwallet.ext.navigateAction
import com.openwallet.ext.parseState
import com.openwallet.glide.commonOptions
import com.openwallet.playerframe.PlayerController
import com.openwallet.ui.activity.fragment.profile.notification.NotificationViewModel
import com.openwallet.ui.activity.fragment.profile.notification.model.hasUnread
import com.openwallet.ui.activity.fragment.wallet.detail.views.WalletNftVideoView
import com.openwallet.ui.activity.fragment.wallet.model.NetworkResponse
import com.openwallet.ui.activity.fragment.wallet.model.RedeemStatus
import com.openwallet.ui.activity.fragment.wallet.model.WalletSummary
import com.openwallet.ui.activity.fragment.wallet.model.WalletViewModel
import com.openwallet.util.DateUtils
import com.openwallet.util.DateUtils.FORMAT_YEAR_MONTH_DAY

class WalletFragment : BaseFragment() {

    private val binding by viewBinding<FragmentWalletBinding>()
    private val viewModel by viewModelActivity<WalletViewModel>()
    private val notificationViewModel by viewModelActivity<NotificationViewModel>()

    companion object{
        const val TAG = "WalletFragment"
    }

    override fun initViewAndData(view: View, savedInstanceStat: Bundle?) {
        requestData()

        binding.layoutRefresh.setOnRefreshListener {
            requestData()
        }
        binding.layoutCard.setOnClickListener {
            nav().navigateAction(R.id.action_MainFragment_to_WallDetailFragment)
        }
        binding.ivNotificationBg.setOnClickListener{
            nav().navigateAction(R.id.action_mainFragment_to_notificationFragment)
        }

        //when redeem status changed, force refresh
        appViewModel.isRedeemStatusChanged.observeInFragment(this) {
            binding.layoutRefresh.isRefreshing = true
            Log.e("liuz", "isRedeemStatusChanged ")
            requestData()
        }

        appViewModel.notificationList.observeInFragment(this) { updateNotificationStatus() }
        appViewModel.run {
            profileInfo.observeInFragment(this@WalletFragment) {
                updateNotificationStatus()
            }
        }
    }

    override fun isNeedStatusLayout() = true

    override fun requestData() {
        notificationViewModel.getNotification()
        viewModel.getList().observe(this) {
            parseState(it, stateLayout, false,
                onSuccess = { walletResponse ->
                    binding.layoutRefresh.isRefreshing = false
                    if (walletResponse.data.isNullOrEmpty()) {
                        stateLayout.showEmpty()
                    } else {
                        handleSuccess(walletResponse)
                    }
                },
                onError = {
                    binding.layoutRefresh.isRefreshing = false
                },
                onLoading = {
                    if (!binding.layoutRefresh.isRefreshing) {
                        stateLayout.showLoading()
                    }
                },
                onHideLoading = {
                    binding.layoutRefresh.isRefreshing = false
                }
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        PlayerController.getInstance().destroyPlayer(TAG)
    }

    private fun loadNftImage(imgUrl: List<String>){
        // 这里为了兼容新旧账号，旧帐号没有包含webp文件，新的账号包含了webp文件
        val imageUrl = imgUrl.firstOrNull { it.endsWith(".webp",true) } ?: imgUrl[0]
        Glide.with(this@WalletFragment)
            .load("$RETROFIT_HOST/$imageUrl").apply{
                placeholder(R.drawable.icon_wallet_nft_front)
                if(imageUrl.endsWith(".webp",true)){
                    optionalTransform(WebpDrawable::class.java , WebpDrawableTransformation(CenterCrop()))
                }
                apply(commonOptions()).into(binding.ivNft)
            }
    }

    private fun loadNftMp4(url : String,videoView : WalletNftVideoView) {
        //videoView.setVideoInfo(TAG,"http://vfx.mtime.cn/Video/2019/03/12/mp4/190312083533415853.mp4")
        videoView.setVideoInfo(TAG,url)
    }

    private fun handleSuccess(walletResponse: NetworkResponse<List<WalletSummary>>) {
        val walletItem = walletResponse.data?.first()
        walletItem?.run {
            viewModel.walletSummary = walletItem
            var url = imgUrl.firstOrNull{ it.endsWith(".mp4",true) }
            if(url != null) {
                binding.flNft.visibility = View.VISIBLE
                binding.ivNft.visibility = View.INVISIBLE
                loadNftMp4("$RETROFIT_HOST/$url",binding.flNft)
            } else {
                binding.flNft.visibility = View.INVISIBLE
                binding.ivNft.visibility = View.VISIBLE
                loadNftImage(imgUrl)
            }

            binding.tvNftTitle.text = name
            binding.tvNftSubTitle.text = edition
            binding.tvOwned.text = String.format(getString(R.string.wallet_own_by), ownedBy)

            binding.tvRedeem.text = status.displayName
            binding.tvRedeemedDate.visibility = View.GONE
            binding.indicatorRedeem.status = when (status) {
                RedeemStatus.REDEEMED -> {
                    binding.tvRedeemedDate.visibility = View.VISIBLE
                    val formatTimeString = DateUtils.convertTimeToString(datetime.toLong(), FORMAT_YEAR_MONTH_DAY)
                    binding.tvRedeemedDate.text = String.format(getString(R.string.walled_detail_redeemed_on), formatTimeString)
                    StatusIndicator.Status.CANCELLED
                }

                RedeemStatus.PROCESSING -> StatusIndicator.Status.PENDING
                RedeemStatus.EXPIRED -> StatusIndicator.Status.DECLINED
                RedeemStatus.UNREDEEMED -> StatusIndicator.Status.APPROVED
            }
            binding.indicatorRedeem.invalidate()  //todo 这里应该控件内部实现
        }
    }

    private fun updateNotificationStatus() {
        binding.ivNotificationBg.setImageResource(
            if (appViewModel.notificationList.value.hasUnread()) R.drawable.icon_notification_unread else R.drawable.icon_notification
        )
    }
}