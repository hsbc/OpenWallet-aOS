package com.openwallet.network.exception

import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.ConnectException

@RunWith(MockitoJUnitRunner::class)
class ExceptionEngineImplTest : TestCase() {
    @Before
    public override fun setUp() {
        super.setUp()
    }
    @Test
    fun test_HandleException_HttpException() {
        val responseData = mockk<Response<Any>>()
        every { responseData.code() } answers { 0 }
        every { responseData.message() } answers { "error" }
        val httpException = HttpException(responseData)
        val appException = AppException()
        appException.errorMessage = httpException.message()
        val exceptionEngine = mockk<ExceptionEngineImpl>()
        every { exceptionEngine.handleException(httpException) } returns appException
        val actualException = exceptionEngine.handleException(httpException)
        assertEquals(appException.errorMessage,actualException.errorMessage)
    }
    @Test
    fun test_HandleException_otherException() {
        val responseData = mockk<Response<Any>>()
        every { responseData.code() } answers { 0 }
        every { responseData.message() } answers { "Network Exception!" }
        val connectException = ConnectException(responseData.message())
        val appException = AppException()
        appException.errorMessage = connectException.message
        val exceptionEngine = ExceptionEngineImpl()
        val actualException = exceptionEngine.handleException(connectException)

        assertEquals(appException.errorMessage,actualException.errorMessage)
    }

    @Test
    fun testHandleErrorMessage() {
        val exceptionEngine = ExceptionEngineImpl()
        exceptionEngine.handleErrorMessage("400")

        //every { exceptionEngine.handleErrorMessage("0x1000000c") } answers { AppException() }
        //exceptionEngine.handleErrorMessage("0x1000000c")
    }
}