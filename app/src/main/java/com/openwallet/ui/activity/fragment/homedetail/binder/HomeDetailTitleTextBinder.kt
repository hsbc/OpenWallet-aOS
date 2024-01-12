package com.openwallet.ui.activity.fragment.homedetail.binder

import android.text.Html
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.RecyclerView
import com.drakeet.multitype.ItemViewBinder
import com.openwallet.R
import com.openwallet.app.OpenWalletApplication
import com.openwallet.ui.activity.fragment.homedetail.TitleTextBean
import com.openwallet.util.DisplayUtil

class HomeDetailTitleTextBinder : ItemViewBinder<TitleTextBean, HomeDetailTitleTextBinder.ViewHolder>() {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        return ViewHolder(inflater.inflate(R.layout.item_home_detail_text_title, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, item: TitleTextBean) {
        holder.tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, item.textSize)
        item.apply {
            marginTop?.let {
                holder.tvTitle.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                    topMargin = DisplayUtil.dip2px(OpenWalletApplication.instance.applicationContext, it)
                }
            }
            marginBottom?.let {
                holder.tvTitle.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                    bottomMargin = DisplayUtil.dip2px(OpenWalletApplication.instance.applicationContext, it)
                }
            }
        }
        holder.tvTitle.text = Html.fromHtml(item.title, HtmlCompat.FROM_HTML_MODE_LEGACY)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
    }


}
