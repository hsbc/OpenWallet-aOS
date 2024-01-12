package com.openwallet.ui.activity.fragment.logout.model

import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LogoutResponseTest : TestCase() {
    @Before
    public override fun setUp() {
        super.setUp()
    }
    @Test
    fun test_LogoutResponse() {
        val expectedData = LogoutResponse(false,"message","data")
        val logoutResponse = LogoutResponse(false,"message","data")
        logoutResponse.message
        logoutResponse.status
        logoutResponse.data
        assertEquals(expectedData.message,logoutResponse.message)
    }
}