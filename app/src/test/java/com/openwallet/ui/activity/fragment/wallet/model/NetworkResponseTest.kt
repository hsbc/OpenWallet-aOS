package com.openwallet.ui.activity.fragment.wallet.model

import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class NetworkResponseTest : TestCase() {
    @Before
    public override fun setUp() {
        super.setUp()
    }
    @Test
    fun test_NetworkResponse() {
        val t = T
        val expectedData = NetworkResponse("message",false,t)
        val networkResponse = NetworkResponse("message",false,t)
        networkResponse.message
        networkResponse.status
        networkResponse.data
        assertEquals(expectedData.message,networkResponse.message)
    }
}