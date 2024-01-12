package com.openwallet.base.adapter

import android.content.Context
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.openwallet.R

class CommonViewHolder(
    private val mContext: Context,
    private val mConvertView: View,
    parent: ViewGroup
) : RecyclerView.ViewHolder(mConvertView) {
    private val mViews: SparseArray<View> = SparseArray()

    /**
     * @param viewId
     * @return
     */
    fun <T : View> getView(viewId: Int): T {
        var view: View? = mViews.get(viewId)
        return view?.run {
            view as T
        } ?: view.run {
            view = mConvertView.findViewById(viewId)
            mViews.put(viewId, view)
            view as T
        }
    }

    fun setText(viewId: Int, text: String): CommonViewHolder {
        val tv = getView<TextView>(viewId)
        tv.text = text
        return this
    }

    fun setImageResource(viewId: Int, resId: Int): CommonViewHolder {
        val view = getView<ImageView>(viewId)
        view.setImageResource(resId)
        return this
    }

    fun setImageResourceWithGlide(viewId: Int, url: String): CommonViewHolder {
        val view = getView<ImageView>(viewId)
        Glide.with(mContext)
            .load(url)
            .placeholder(R.drawable.ic_launcher_background)
            .into(view)
        return this
    }

    fun setOnClickListener(
        viewId: Int,
        listener: View.OnClickListener
    ): CommonViewHolder {
        val view = getView<View>(viewId)
        view.setOnClickListener(listener)
        return this
    }

    companion object {
        fun get(context: Context, parent: ViewGroup, layoutId: Int): CommonViewHolder {
            val itemView = LayoutInflater.from(context).inflate(layoutId, parent, false)
            return CommonViewHolder(context, itemView, parent)
        }
    }
}
