package com.openwallet.ui.activity.fragment.register.model

import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RegisterSendPhoneSmsResponseTest : TestCase() {
    @Before
    public override fun setUp() {
        super.setUp()
    }
    @Test
    fun test_RegisterSendPhoneSmsResponse() {
        val expectedData = RegisterVerifyEmailSmsData("token","111111")
        val registerVerifyEmailSmsData = RegisterVerifyEmailSmsData("token","111111")
        registerVerifyEmailSmsData.captcha
        registerVerifyEmailSmsData.token
        assertEquals(expectedData.token,registerVerifyEmailSmsData.token)

        val expectedResponse = RegisterSendPhoneSmsResponse(false,"message",registerVerifyEmailSmsData)
        val registerSendPhoneSmsResponse = RegisterSendPhoneSmsResponse(false,"message",registerVerifyEmailSmsData)
        registerSendPhoneSmsResponse.message
        registerSendPhoneSmsResponse.status
        registerSendPhoneSmsResponse.data
        assertEquals(expectedResponse.data,registerSendPhoneSmsResponse.data)

        val expectedDataRegisterSendPhoneSmsData = RegisterSendPhoneSmsData("token","111111")
        val registerSendPhoneSmsData = RegisterSendPhoneSmsData("token","111111")
        registerSendPhoneSmsData.token
        registerSendPhoneSmsData.captcha
        assertEquals(expectedDataRegisterSendPhoneSmsData.token,registerSendPhoneSmsData.token)

    }
}