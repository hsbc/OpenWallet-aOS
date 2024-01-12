package com.openwallet.network.interceptor

import com.openwallet.app.OpenWalletApplication
import com.openwallet.app.OpenWalletApplication.Companion.appViewModel
import com.openwallet.constants.HEADER_TOKEN_START
import com.openwallet.manager.CacheManager
import okhttp3.Interceptor
import okhttp3.Response

class ResponseInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request().newBuilder()
            .addHeader("Authorization", HEADER_TOKEN_START + OpenWalletApplication.appViewModel.loginToken)
            .addHeader("Source", CacheManager.getUUID())
            .addHeader("Cookie", "1865OH=True")
            .build()

        val response: Response = chain.proceed(request)
        when (response.code) {
            401 -> {
                appViewModel.sessionTimeOut.postValue(true)
            }
        }

        return response
    }

}