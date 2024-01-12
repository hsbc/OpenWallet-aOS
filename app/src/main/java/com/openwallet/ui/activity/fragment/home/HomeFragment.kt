package com.openwallet.ui.activity.fragment.home

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.MarginPageTransformer
import com.google.android.material.tabs.TabLayoutMediator
import com.openwallet.R
import com.openwallet.app.OpenWalletApplication
import com.openwallet.app.OpenWalletApplication.Companion.appViewModel
import com.openwallet.base.BaseFragment
import com.openwallet.constants.TAB_INDEX_WALLET
import com.openwallet.databinding.FragmentHomeBinding
import com.openwallet.ext.nav
import com.openwallet.ext.navigateAction
import com.openwallet.ext.parseState
import com.openwallet.ui.activity.fragment.ImageAdapter
import com.openwallet.ui.activity.fragment.homedetail.data.HomeFakeData
import com.openwallet.ui.activity.fragment.profile.notification.NotificationViewModel
import com.openwallet.ui.activity.fragment.profile.notification.model.hasUnread
import com.openwallet.util.CommonUtils
import com.openwallet.util.DisplayUtil

class HomeFragment : BaseFragment() {

    private val binding by viewBinding<FragmentHomeBinding>()

    private val notificationViewModel by viewModelActivity<NotificationViewModel>()

    private val viewModel by viewModelActivity<HomeViewModel>()


    override fun initViewAndData(view: View, savedInstanceStat: Bundle?) {
        requestData()

        val bannerAdapter = ImageAdapter()
        bannerAdapter.setOnItemClickListener { view, position ->
            val bundle = bundleOf("index" to position)
            nav().navigateAction(R.id.action_MainFragment_to_HomeDetailFragment, bundle)
        }

        notificationViewModel.getNotification()
        binding.ivNotificationBg.setOnClickListener {
            nav().navigate(R.id.action_mainFragment_to_notificationFragment)
        }

        appViewModel.notificationList.observeInFragment(this) { updateNotificationStatus() }
        appViewModel.run {
           profileInfo.observeInFragment(this@HomeFragment) {
               updateNotificationStatus()
            }
        }

        binding.imgViewPager.apply {
            adapter = bannerAdapter
            setPageTransformer(MarginPageTransformer(DisplayUtil.dip2px(context, 5f)))

            offscreenPageLimit = 1
            val recyclerView = getChildAt(0)

            if (recyclerView != null && recyclerView is RecyclerView) {
                recyclerView.setPadding(DisplayUtil.dip2px(context, 14f), 0, DisplayUtil.dip2px(context, 14f), 0);
                recyclerView.clipToPadding = false;

            }
        }

        bannerAdapter.setItem(HomeFakeData.getHomeBannerList())
        TabLayoutMediator(binding.indicator, binding.imgViewPager) { _, _ -> }.attach()

        binding.icClose.setOnClickListener { binding.layoutNotify.visibility = View.GONE }
        //nav not work, so use livedata instead
        binding.ivNFT.setOnClickListener {
            OpenWalletApplication.appViewModel.tabIndex.value = TAB_INDEX_WALLET
        }
    }

    override fun requestData() {
        requestHasExpiredNFT()
        binding.tvTitle.text = getGreetings(CommonUtils.getLocalHour()) //模拟网络请求，设置
    }

    private fun requestHasExpiredNFT() {
        viewModel.hasExpiredNFT().observe(this) {
            parseState(it, onSuccess = { it1 ->
                binding.layoutNotify.visibility = if (it1.data == true) View.VISIBLE else View.GONE
            })
        }
    }

    private fun updateNotificationStatus() {
        binding.ivNotificationBg.setImageResource(
            if (appViewModel.notificationList.value.hasUnread()) R.drawable.icon_notification_unread else R.drawable.icon_notification
        )
    }

    fun getGreetings(currentHour: Int): String {
        return when (currentHour) {
            in 6..11 -> getString(R.string.morning_greeting)
            in 12..17 -> getString(R.string.afternoon_greeting)
            !in 6..17-> getString(R.string.evening_greeting)
            else -> {
                getString(R.string.default_greeting)
            }
        }
    }
}