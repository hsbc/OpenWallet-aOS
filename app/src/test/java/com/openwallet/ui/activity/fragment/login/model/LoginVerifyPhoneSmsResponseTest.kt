package com.openwallet.ui.activity.fragment.login.model

import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LoginVerifyPhoneSmsResponseTest : TestCase() {
    @Before
    public override fun setUp() {
        super.setUp()
    }
    @Test
    fun test_LoginVerifyPhoneSmsResponse() {

        val roles = listOf<String>()
        val expectedData = LoginVerifyData("token","type","1","username","email",roles)
        val loginVerifyData = LoginVerifyData("token","type","1","username","email",roles)
        loginVerifyData.email
        loginVerifyData.token
        loginVerifyData.type
        loginVerifyData.accountId
        loginVerifyData.username
        loginVerifyData.roles.toString()
        assertEquals(expectedData.token,loginVerifyData.token)

        val expectedResponse = LoginVerifyPhoneSmsResponse(false,"message",loginVerifyData)
        val loginVerifyPhoneSmsResponse = LoginVerifyPhoneSmsResponse(false,"message",loginVerifyData)
        loginVerifyPhoneSmsResponse.message
        loginVerifyPhoneSmsResponse.status
        loginVerifyPhoneSmsResponse.data.toString()
        assertEquals(expectedResponse.data,loginVerifyPhoneSmsResponse.data)
    }
}