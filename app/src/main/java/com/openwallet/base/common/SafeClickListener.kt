package com.openwallet.base.common

import android.os.SystemClock
import android.view.View

/**
 * prevent fast single click at view.
 */
class SafeClickListener (
    private var defaultInterval: Int = 650,
    private val onSafeCLick: (View?) -> Unit
) : View.OnClickListener {

    private var lastTimeClicked: Long = 0

    override fun onClick(v: View?) {
        if (SystemClock.elapsedRealtime() - lastTimeClicked < defaultInterval) {
            return
        }
        lastTimeClicked = SystemClock.elapsedRealtime()
        onSafeCLick(v)
    }
}