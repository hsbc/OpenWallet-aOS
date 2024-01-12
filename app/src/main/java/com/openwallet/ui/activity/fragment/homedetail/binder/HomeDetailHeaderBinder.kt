package com.openwallet.ui.activity.fragment.homedetail.binder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.drakeet.multitype.ItemViewBinder
import com.openwallet.R
import com.openwallet.ui.activity.fragment.homedetail.HomeDetailHeaderBean

class HomeDetailHeaderBinder : ItemViewBinder<HomeDetailHeaderBean, HomeDetailHeaderBinder.ViewHolder>() {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        return ViewHolder(inflater.inflate(R.layout.item_home_detail_header, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, item: HomeDetailHeaderBean) {
        holder.ivBanner.setImageResource(item.imgRes)
        holder.tvSubTitle.text = item.subTitle
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivBanner: ImageView = itemView.findViewById(R.id.ivBanner)
        val tvSubTitle: TextView = itemView.findViewById(R.id.tvSutTitle)
    }


}
