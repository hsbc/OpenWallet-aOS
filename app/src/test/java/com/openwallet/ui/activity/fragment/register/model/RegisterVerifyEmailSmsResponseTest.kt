package com.openwallet.ui.activity.fragment.register.model

import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RegisterVerifyEmailSmsResponseTest : TestCase() {
    @Before
    public override fun setUp() {
        super.setUp()
    }
    @Test
    fun test_RegisterVerifyEmailSmsResponse() {
        val expectedData = RegisterVerifyEmailSmsData("token","111111")
        val registerVerifyEmailSmsData = RegisterVerifyEmailSmsData("token","111111")
        registerVerifyEmailSmsData.captcha
        registerVerifyEmailSmsData.token
        assertEquals(expectedData.token,registerVerifyEmailSmsData.token)

        val expectedResponseData = RegisterVerifyEmailSmsResponse(false,"message",registerVerifyEmailSmsData)

        val registerVerifyEmailSmsResponseData = RegisterVerifyEmailSmsResponse(false,"message",registerVerifyEmailSmsData)
        registerVerifyEmailSmsResponseData.message
        registerVerifyEmailSmsResponseData.status
        registerVerifyEmailSmsResponseData.data
        assertEquals(expectedResponseData.data,registerVerifyEmailSmsResponseData.data)
    }
}