package com.openwallet.ui.activity.fragment.profile.faq

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.openwallet.R
import com.openwallet.base.BaseFragment
import com.openwallet.base.adapter.CommonAdapter
import com.openwallet.base.adapter.CommonViewHolder
import com.openwallet.databinding.FragmentFaqBinding
import com.openwallet.ext.gone
import com.openwallet.ext.parseState
import com.openwallet.ext.visible
import com.openwallet.ui.activity.fragment.profile.faq.model.FaqData


class FaqFragment : BaseFragment() {

    private val binding by viewBinding<FragmentFaqBinding>()
    private val faqViewModel by viewModelFragment<FaqViewModel>()

    override fun isNeedStatusLayout() = true

    override fun initViewAndData(view: View, savedInstanceStat: Bundle?) {
        binding.toolbar.init(title = getString(R.string.profile_faq), isShowBack = true)
        binding.list.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        requestData()
    }

    private fun initListView(response: MutableList<FaqData>) {
        binding.list.adapter =
            object : CommonAdapter<FaqData>(
                requireContext(),
                R.layout.item_faq,
                response
            ) {
                override fun convert(
                    holder: CommonViewHolder,
                    data: FaqData,
                    position: Int
                ) {
                    holder.getView<ListItemView>(R.id.question).setTitle(data.question.orEmpty())
                    holder.getView<TextView>(R.id.answer).text = data.answer.orEmpty()
                    ContextCompat.getDrawable(
                        requireContext(),
                        if (data.isExpandedQuestion) R.drawable.ic_chevron_down_hase
                        else R.drawable.ic_chevron_right_hase
                    )?.let {
                        holder.getView<ListItemView>(R.id.question).setConfirmationIcon(it)
                    }
                    holder.getView<ListItemView>(R.id.question)
                        .setActionIcon(resources.getDrawable(R.color.white, null))
                    holder.getView<View>(R.id.answer).visibility =
                        if (data.isExpandedQuestion) View.VISIBLE else View.GONE

                    holder.getView<View>(R.id.llQuestion).setOnClickListener {
                        data.isExpandedQuestion = !data.isExpandedQuestion
                        notifyItemChanged(position)
                    }
                }
            }
    }

    override fun requestData() {
        faqViewModel.getFaqList().observe(this) {
            parseState(
                it,
                stateLayout,
                onLoading = {
                    binding.loading.visible()
                },
                onSuccess = { response ->
                    response.data?.run {
                        binding.loading.gone()
                        initListView(this)
                    }

                },
                onError = {
                    binding.loading.gone()
                }
            )
        }
    }
}