package com.openwallet.util

import android.util.Log
import com.openwallet.BuildConfig

object LogUtils {

    private const val defaultTag ="OpenWallet"

    fun isCanShowLog(): Boolean {
        return BuildConfig.DEBUG
    }

    fun logD(msg: String, tag:String = defaultTag, ) {
        if(!isCanShowLog()) {
            return
        }
        Log.d(tag, msg)
    }

    fun logV(msg: String, tag:String = defaultTag) {
        if(!isCanShowLog()) {
            return
        }
        Log.v(tag, msg)
    }

    fun logI(msg: String, tag:String = defaultTag) {
        if(!isCanShowLog()) {
            return
        }
        Log.i(tag, msg)
    }

    fun logW(msg: String, tag:String = defaultTag) {
        if(!isCanShowLog()) {
            return
        }
        Log.w(tag, msg)
    }

    fun logE(msg: String, tag:String = defaultTag) {
        if(!isCanShowLog()) {
            return
        }
        Log.e(tag, msg)
    }

}