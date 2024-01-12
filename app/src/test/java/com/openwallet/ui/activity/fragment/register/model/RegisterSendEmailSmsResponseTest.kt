package com.openwallet.ui.activity.fragment.register.model

import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RegisterSendEmailSmsResponseTest : TestCase() {
    @Before
    public override fun setUp() {
        super.setUp()
    }
    @Test
    fun test_RegisterSendEmailSmsResponse() {
        val expectedData = RegisterVerifyEmailSmsData("token","111111")
        val registerVerifyEmailSmsData = RegisterVerifyEmailSmsData("token","111111")
        registerVerifyEmailSmsData.captcha
        registerVerifyEmailSmsData.token
        assertEquals(expectedData.token,registerVerifyEmailSmsData.token)

        val expectedResponse = RegisterSendEmailSmsResponse(false,"message",registerVerifyEmailSmsData)
        val registerSendEmailSmsResponse = RegisterSendEmailSmsResponse(false,"message",registerVerifyEmailSmsData)
        registerSendEmailSmsResponse.data.toString()
        registerSendEmailSmsResponse.message
        registerSendEmailSmsResponse.status
        assertEquals(expectedResponse.data,registerSendEmailSmsResponse.data)

        val expectedDataRegisterSendEmailSmsData = RegisterSendEmailSmsData("token","111111")
        val registerSendEmailSmsData = RegisterSendEmailSmsData("token","111111")
        registerSendEmailSmsData.captcha
        registerSendEmailSmsData.token
        assertEquals(expectedDataRegisterSendEmailSmsData.token,registerSendEmailSmsData.token)
    }
}