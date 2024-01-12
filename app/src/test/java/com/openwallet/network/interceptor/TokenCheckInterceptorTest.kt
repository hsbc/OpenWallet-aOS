package com.openwallet.network.interceptor

import com.openwallet.app.AppViewModel
import com.openwallet.app.OpenWalletApplication
import com.openwallet.manager.CacheManager
import com.openwallet.util.CommonUtils
import com.openwallet.util.LogUtils
import io.mockk.*
import junit.framework.TestCase
import okhttp3.Interceptor
import okhttp3.Response
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TokenCheckInterceptorTest : TestCase() {
    @Before
    public override fun setUp() {
        super.setUp()
    }
    @Test
    fun test_Intercept_RefreshToken_true() {
        val appViewModel = mockk<AppViewModel>()
        mockkObject(OpenWalletApplication)
        mockkObject(CacheManager)
        every { OpenWalletApplication.Companion.appViewModel } answers { appViewModel }
        every { OpenWalletApplication.appViewModel.loginToken} answers { "token" }

        every { CacheManager.getUUID()} answers { "test" }

        val chain = mockk<Interceptor.Chain>()
        every { chain.request() } answers { mockk<okhttp3.Request>() }
        val request = chain.request()

        val tokenCheckInterceptor = TokenCheckInterceptor()
        every { chain.request().header("RefreshToken") } answers { "true" }
        every { chain.request().newBuilder().removeHeader("RefreshToken").build() } answers { request }
        every { chain.proceed(request) } answers { mockk<Response>()}

        val mockTokenCheckInterceptor = mockk<TokenCheckInterceptor>()

        val response = mockk<Response>()
        every { response.code } returns 200
        every { tokenCheckInterceptor.intercept(chain) } answers { response }
        every { mockTokenCheckInterceptor.intercept(chain) } answers { response }

        //tokenCheckInterceptor.intercept(chain)
        val responseInterceptors = mockTokenCheckInterceptor.intercept(chain)
        assertEquals(response.code,responseInterceptors.code)
    }
    @Test
    fun test_Intercept_RefreshToken_false() {
        val appViewModel = mockk<AppViewModel>()
        mockkObject(OpenWalletApplication)
        mockkObject(CacheManager)
        mockkObject(CommonUtils, recordPrivateCalls = true)

        every { OpenWalletApplication.Companion.appViewModel } answers { appViewModel }
        //every { OpenWalletApplication.appViewModel.loginToken} answers { "token" }

        //every { appViewModel.loginToken } answers { "test" }
        //every { appViewModel.refreshToken } answers { "test" }

        every { CacheManager.getUUID()} answers { "test" }

        //every { CommonUtils.isLogin() } answers { true }

        //every { CommonUtils.isRefreshTokenExist()} answers { true }

        //isCanShowLog 设置为false
        every { CommonUtils["isLogin"]() } returns false

        TokenCheckInterceptor.Companion.isRefreshToken
        TokenCheckInterceptor.Companion.waitRefreshToken = true

        val chain = mockk<Interceptor.Chain>()
        every { chain.request() } answers { mockk<okhttp3.Request>() }
        val request = chain.request()

        val tokenCheckInterceptor = TokenCheckInterceptor()
        every { chain.request().header("RefreshToken") } answers { "false" }
        every { chain.request().newBuilder().removeHeader("RefreshToken").build() } answers { request }
        every { chain.proceed(request) } answers { mockk<Response>()}

        // 调用私有函数 calSpeed()
        every { tokenCheckInterceptor["isNeedRefreshToken"]() } returns true

        //tokenCheckInterceptor.intercept(chain)

        val response = mockk<Response>()
        every { response.code } returns 200

        val mockTokenCheckInterceptor = mockk<TokenCheckInterceptor>()

        //every { tokenCheckInterceptor["isNeedRefreshToken"]() } answers { true }

        every { tokenCheckInterceptor.intercept(chain) } answers { response }
        every { mockTokenCheckInterceptor.intercept(chain) } answers { response }


        val responseInterceptors = mockTokenCheckInterceptor.intercept(chain)
        assertEquals(response.code,responseInterceptors.code)

    }
}