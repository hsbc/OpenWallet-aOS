package com.openwallet.playerframe.utils

import android.util.Log
import com.openwallet.BuildConfig

object LogUtil {

    fun isCanShowLog(): Boolean {
        return BuildConfig.DEBUG
    }

    fun i(tag: String? = null, msg: String) {
        if (!isCanShowLog()) {
            return
        }
        Log.i(getTag(tag), msg)
    }

    fun e(tag: String? = null, msg: String) {
        if (!isCanShowLog()) {
            return
        }
        Log.e(getTag(tag), msg)
    }

    fun d(tag: String? = null, msg: String) {
        if (!isCanShowLog()) {
            return
        }
        Log.d(getTag(tag), msg)
    }

    fun w(tag: String? = null, msg: String) {
        if (!isCanShowLog()) {
            return
        }
        Log.w(getTag(tag), msg)
    }

    private fun getTag(tag: String?): String = tag ?: "LogUtil"

}