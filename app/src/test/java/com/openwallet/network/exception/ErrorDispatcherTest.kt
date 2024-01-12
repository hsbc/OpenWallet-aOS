package com.openwallet.network.exception

import com.openwallet.app.AppViewModel
import com.openwallet.app.OpenWalletApplication
import io.mockk.*
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ErrorDispatcherTest : TestCase() {
    val errorDispatcher = mockk<ErrorDispatcher>()
    @Before
    public override fun setUp() {
        super.setUp()
    }
    @Test
    fun testGetErrorCode() {
        var expectednErrorDispatcher= mockk<ErrorDispatcher>()
        every { expectednErrorDispatcher.getErrorCode("error") } returns "error"
        every { errorDispatcher.getErrorCode("error") } returns "error"
        val expectedResult = expectednErrorDispatcher.getErrorCode("error")
        val actualResult = errorDispatcher.getErrorCode("error")
        assertEquals(expectedResult,actualResult)
    }
    @Test
    fun testHandleErrorByCode() {
        //Stub就是把需要测试的数据塞进对象中，使用基本为：
        //Mockito .when ( ... ) .thenReturn ( ... ) ;
        //使用 Stub 时，我们只关注于方法调用和返回结果
        //`when`(mockedList[0]).thenReturn("first").thenReturn("second").thenThrow(NullPointerException())
        val appViewModel = mockk<AppViewModel>()
        mockkObject(OpenWalletApplication.Companion)
        every { OpenWalletApplication.Companion.appViewModel } answers { appViewModel }
        every { OpenWalletApplication.appViewModel.sessionTimeOut.value} answers { true }

        mockkObject(ErrorDispatcher)

        every { ErrorDispatcher.handleErrorByCode("0x1000000c") } just Runs

        ErrorDispatcher.handleErrorByCode("0x1000000c")

    }
}