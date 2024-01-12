package com.openwallet.network.interceptor

import com.openwallet.app.OpenWalletApplication.Companion.appViewModel
import com.openwallet.model.token.RefreshTokenRequestBody
import com.openwallet.util.CommonUtils
import okhttp3.Interceptor
import okhttp3.Response

class TokenCheckInterceptor : Interceptor {

    companion object {
        const val isRefreshToken = "RefreshToken:true"
        @Volatile var waitRefreshToken = false

        @Synchronized
        private fun refreshToken() {
            //avoid multi thread here need recheck token exp time
            if(!waitRefreshToken) {
                //just like the double check
                return
            }
            val result = appViewModel.getApiRepository().refreshToken(RefreshTokenRequestBody(appViewModel.refreshToken)).execute()
            val response = result.body()
            val tokenNew = response?.data?.token
            if(!tokenNew.isNullOrBlank()) {
                appViewModel.loginToken = tokenNew
            }
            waitRefreshToken = false
        }

    }

    private val headKey = "RefreshToken"
    private val timeWillExpInterval = 5 * 60 //second


    private fun isNeedRefreshToken(): Boolean {
        if(CommonUtils.isLogin() && CommonUtils.isRefreshTokenExist()) {
            //login check token exp time
            val nowTime = System.currentTimeMillis()/1000
            val expTime = CommonUtils.getTokenExpTime(appViewModel.loginToken)

            if(nowTime >= expTime) {
                //already exp, do not do renew operation.
                return false
            }
            if(nowTime + timeWillExpInterval >= expTime) {
                //need refresh token first.
                waitRefreshToken = true
                return true
            }
        }
        return false
    }

    override fun intercept(chain: Interceptor.Chain): Response {

        val originRequest = chain.request()
        val headVar = originRequest.header(headKey)
        val isRefreshTokenReq = "true" contentEquals  headVar

        if(!isRefreshTokenReq) {
            //skip refresh token request
            if(isNeedRefreshToken()) {
                refreshToken()
            }
        }
        //update new token for the origin request.
        val request = originRequest.newBuilder().removeHeader(headKey).build()
        return chain.proceed(request)
    }

}