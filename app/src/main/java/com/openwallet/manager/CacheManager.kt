package com.openwallet.manager

import android.text.TextUtils
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.openwallet.app.OpenWalletApplication.Companion.APP_ID
import com.openwallet.ui.activity.fragment.sms.model.SmsVerificationResponseData
import com.tencent.mmkv.MMKV
import java.util.*

object CacheManager {

    private fun setUUID(): String {
        val kv = MMKV.mmkvWithID(APP_ID)
        val uuid = UUID.randomUUID().toString()
        kv.encode("UUID", uuid)
        return uuid
    }

    fun getUUID(): String {
        val kv = MMKV.mmkvWithID(APP_ID)
        val uuid = kv.decodeString("UUID")
        return if (TextUtils.isEmpty(uuid)) {
            setUUID()
        } else {
            uuid!!
        }
    }

    fun getUser(): SmsVerificationResponseData? {
        val kv = MMKV.mmkvWithID(APP_ID)
        val userStr = kv.decodeString("user")
        return if (TextUtils.isEmpty(userStr)) {
            null
        } else {
            Gson().fromJson(userStr, SmsVerificationResponseData::class.java)
        }
    }

    fun setUser(loginResponse: SmsVerificationResponseData?) {
        val kv = MMKV.mmkvWithID(APP_ID)
        if (loginResponse == null) {
            kv.encode("user", "")
        } else {
            kv.encode("user", GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().toJson(loginResponse))
        }
    }

    fun isRememberMe(): Boolean {
        val kv = MMKV.mmkvWithID(APP_ID)
        return kv.decodeBool("rememberMe", false)
    }

    fun setIsRememberMe(isRememberMe: Boolean) {
        val kv = MMKV.mmkvWithID(APP_ID)
        kv.encode("rememberMe", isRememberMe)
    }

    fun clearRememberMe() {
        val kv = MMKV.mmkvWithID(APP_ID)
        kv.removeValueForKey("rememberMe")
        kv.removeValueForKey("user")
    }
}