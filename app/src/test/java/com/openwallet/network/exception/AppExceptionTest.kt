package com.openwallet.network.exception

import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AppExceptionTest : TestCase() {
    @Before
    public override fun setUp() {
        super.setUp()
    }
    @Test
    fun testGetErrorMessage() {
        val expectedException = AppException()
        expectedException.errorMessage = "error"

        val appException = mockk<AppException>()
        every { appException.errorMessage } returns "error"
        appException.errorMessage

        assertEquals(expectedException.errorMessage,appException.errorMessage)
    }
}