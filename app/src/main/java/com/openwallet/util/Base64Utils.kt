package com.openwallet.util

import android.util.Base64


object Base64Utils {

    fun decodeStr(str: String): String {
        var res = ""
        try {
            val bytes = Base64.decode(str.toByteArray(), Base64.DEFAULT)
            res = String(bytes)
        } catch (_: Exception) {

        }
        return res
    }

    fun encodeStr(str: String): String {
        var res = ""
        try {
            val bytes = Base64.encode(str.toByteArray(), Base64.DEFAULT)
            res = String(bytes)
        } catch (_: Exception) {
        }
        return res
    }

}