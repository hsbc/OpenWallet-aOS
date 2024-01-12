package com.openwallet.network.interceptor

import com.openwallet.app.AppViewModel
import com.openwallet.app.OpenWalletApplication
import com.openwallet.manager.CacheManager
import com.openwallet.network.ApiRepository
import com.kunminx.architecture.ui.callback.UnPeekLiveData
import com.tencent.mmkv.MMKV
import io.mockk.*
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase
import okhttp3.Interceptor
import okhttp3.Response
import org.junit.Before
import org.junit.Test
import org.junit.runner.Request
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ResponseInterceptorTest : TestCase() {
    @Before
    public override fun setUp() {
        super.setUp()
        MockKAnnotations.init(this)

    }
    @Test
    fun test_ResponseInterceptor() {
        val appViewModel = mockk<AppViewModel>()
        mockkObject(OpenWalletApplication)
        mockkObject(CacheManager)
        every { OpenWalletApplication.Companion.appViewModel } answers { appViewModel }
        every { OpenWalletApplication.appViewModel.loginToken} answers { "token" }

        val mockSessionTimeOut = mockk<UnPeekLiveData<Boolean>>()
        every { mockSessionTimeOut.value } returns true

        every { OpenWalletApplication.appViewModel.sessionTimeOut} returns mockSessionTimeOut


        every { CacheManager.getUUID()} answers { "test" }

        val chain = mockk<Interceptor.Chain>()
        every { chain.request() } answers { mockk<okhttp3.Request>() }
        every { chain.request().newBuilder() } answers  { mockk<okhttp3.Request.Builder>()}
        every { chain.request().newBuilder().addHeader("Authorization","test") } answers  { chain.request().newBuilder().addHeader("Authorization","test")  }
        every { chain.request().newBuilder().addHeader("Source", CacheManager.getUUID()) } answers  { chain.request().newBuilder().addHeader("Source","test")  }

        var response = mockk<Response>()
        every { response.code } returns 401
        val responseInterceptor = ResponseInterceptor()
        val mockResponseInterceptor = mockk<ResponseInterceptor>()
        every { responseInterceptor.intercept(chain) } answers { response }
        every { mockResponseInterceptor.intercept(chain) } answers { response }
        val responseInterceptors = mockResponseInterceptor.intercept(chain)
        assertEquals(response.code,responseInterceptors.code)
    }
}