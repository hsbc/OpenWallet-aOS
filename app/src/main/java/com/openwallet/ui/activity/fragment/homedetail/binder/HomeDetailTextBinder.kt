package com.openwallet.ui.activity.fragment.homedetail.binder

import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.drakeet.multitype.ItemViewBinder
import com.openwallet.R
import com.openwallet.ui.activity.fragment.homedetail.TextBean

class HomeDetailTextBinder : ItemViewBinder<TextBean, HomeDetailTextBinder.ViewHolder>() {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        return ViewHolder(inflater.inflate(R.layout.item_home_detail_text_body, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, item: TextBean) {
        val message = item.body.replace("\n", "<br>")
        holder.tvBody.text = Html.fromHtml(message, HtmlCompat.FROM_HTML_MODE_LEGACY)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvBody: TextView = itemView.findViewById(R.id.tvBody)
    }


}
