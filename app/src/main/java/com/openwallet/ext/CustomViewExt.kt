package com.openwallet.ext

import android.view.View
import android.view.ViewGroup
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx


fun BottomNavigationViewEx.interceptLongClick(vararg ids: Int) {
    val bottomNavigationMenuView: ViewGroup = (this.getChildAt(0) as ViewGroup)
    for (index in ids.indices) {
        bottomNavigationMenuView.getChildAt(index).findViewById<View>(ids[index])
            .setOnLongClickListener {
                true
            }
    }
}
