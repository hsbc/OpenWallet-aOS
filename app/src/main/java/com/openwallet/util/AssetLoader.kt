package com.openwallet.util

import android.content.Context
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AssetLoader @Inject constructor(
    val context: Context,
    val gson: Gson
) {

    inline fun <reified T> loadConfig(fileName: String): T {
        return context.assets.open(fileName).use {
            gson.fromJson(BufferedReader(InputStreamReader(it)), T::class.java)
        }
    }

    fun loadConfigContent(fileName: String): String {
        return context.assets.open(fileName).use {
            BufferedReader(InputStreamReader(it)).readText()
        }
    }
}
