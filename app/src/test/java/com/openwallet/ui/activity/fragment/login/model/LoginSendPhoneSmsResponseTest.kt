package com.openwallet.ui.activity.fragment.login.model

import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LoginSendPhoneSmsResponseTest : TestCase() {
    @Before
    public override fun setUp() {
        super.setUp()
    }
    @Test
    fun test_LoginSendPhoneSmsResponse() {
        val expectedData = LoginSendPhoneSmsData("token","111111")
        val loginSendPhoneSmsData = LoginSendPhoneSmsData("token","111111")
        loginSendPhoneSmsData.token
        loginSendPhoneSmsData.captcha
        assertEquals(expectedData.token,loginSendPhoneSmsData.token)

        val expectedResponse = LoginSendPhoneSmsResponse(false,"message",loginSendPhoneSmsData)
        val loginsendPhoneSmsResponse = LoginSendPhoneSmsResponse(false,"message",loginSendPhoneSmsData)
        loginsendPhoneSmsResponse.message
        loginsendPhoneSmsResponse.status
        loginsendPhoneSmsResponse.data
        assertEquals(expectedResponse.data,loginsendPhoneSmsResponse.data)
    }
}