package com.openwallet.ui.activity.fragment.register.model

import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RegisterVerifyPhoneSmsResponseTest : TestCase() {
    @Before
    public override fun setUp() {
        super.setUp()
    }
    @Test
    fun test_RegisterVerifyPhoneSmsResponse() {
        val expectedData = RegisterVerifyPhoneSmsData("token","111111")
        val registerVerifyPhoneSmsData = RegisterVerifyPhoneSmsData("token","111111")
        registerVerifyPhoneSmsData.captcha
        registerVerifyPhoneSmsData.token
        assertEquals(expectedData.token,registerVerifyPhoneSmsData.token)

        val expectedResponse = RegisterVerifyPhoneSmsResponse(false,"message",registerVerifyPhoneSmsData)
        val registerVerifyPhoneSmsResponse = RegisterVerifyPhoneSmsResponse(false,"message",registerVerifyPhoneSmsData)
        registerVerifyPhoneSmsResponse.message
        registerVerifyPhoneSmsResponse.status
        registerVerifyPhoneSmsResponse.data
        assertEquals(expectedResponse.data,registerVerifyPhoneSmsResponse.data)
    }
}