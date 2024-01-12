package com.openwallet.ui.activity.fragment.sms.model

import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SmsVerificationResponseDataTest : TestCase() {
    @Before
    public override fun setUp() {
        super.setUp()
    }
    @Test
    fun test_SmsVerificationResponseData() {
        val roles = listOf<String>()
        val expectedData = SmsVerificationResponseData("111111","token","refreshToken","type","1","username","email",roles)
        val smsVerificationResponseData = SmsVerificationResponseData("111111","token","refreshToken","type","1","username","email",roles)
        smsVerificationResponseData.accountId
        smsVerificationResponseData.captcha
        smsVerificationResponseData.token
        smsVerificationResponseData.username
        smsVerificationResponseData.refreshToken
        smsVerificationResponseData.email
        smsVerificationResponseData.type
        smsVerificationResponseData.roles
        assertEquals(expectedData.token,smsVerificationResponseData.token)
    }
}