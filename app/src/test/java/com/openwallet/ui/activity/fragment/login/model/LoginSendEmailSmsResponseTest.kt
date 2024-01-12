package com.openwallet.ui.activity.fragment.login.model

import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LoginSendEmailSmsResponseTest : TestCase() {
    @Before
    public override fun setUp() {
        super.setUp()
    }
    @Test
    fun test_LoginSendEmailSnsResponse() {
        val expectedData = LoginSendEmailSmsData("token","111111")
        val loginSendEmailSmsData = LoginSendEmailSmsData("token","111111")
        loginSendEmailSmsData.token
        loginSendEmailSmsData.captcha
        assertEquals(expectedData.token,loginSendEmailSmsData.token)

        val expectedResponse = LoginSendEmailSmsResponse(false,"message",loginSendEmailSmsData)
        val loginSendEmailSmsResponse = LoginSendEmailSmsResponse(false,"message",loginSendEmailSmsData)
        loginSendEmailSmsResponse.status
        loginSendEmailSmsResponse.message
        loginSendEmailSmsResponse.data
        assertEquals(expectedResponse.data,loginSendEmailSmsResponse.data)
    }
}