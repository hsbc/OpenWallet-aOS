package com.openwallet.util

import android.os.SystemClock


object FastClickCheckUtil {

    private var lastTimeClicked: Long = 0
    private const val interval600Ms = 600L

    fun isCanClickIn600MS(): Boolean {
        if (SystemClock.elapsedRealtime() - lastTimeClicked < interval600Ms) {
            return false
        }
        lastTimeClicked = SystemClock.elapsedRealtime()
        return true
    }

}