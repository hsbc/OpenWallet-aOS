package com.openwallet.util

import com.google.gson.Gson
import com.google.gson.JsonParser

object HttpLogFormatUtil {
    private val jsonParser = JsonParser()
    fun format(gson: Gson, msg: String): String {
        if (msg.isEmpty()) {
            return ""
        }
        val formatMsg = try {
            gson.toJson(jsonParser.parse(msg))
        } catch (e: Exception) {
            return msg
        }
        return "\n$formatMsg\n"
    }
}