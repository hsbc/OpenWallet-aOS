package com.openwallet.ui.activity.fragment.sms.model

import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SmsResponseDataTest : TestCase() {
    @Before
    public override fun setUp() {
        super.setUp()
    }
    @Test
    fun test_SmsResponseData() {
        val expectedData = SmsResponseData("token","111111")
        val smsResponseData = SmsResponseData("token","111111")
        smsResponseData.captcha
        smsResponseData.token
        assertEquals(expectedData.token,smsResponseData.token)
    }
}