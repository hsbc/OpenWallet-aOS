package com.openwallet.ui.activity.fragment.login.model

import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LoginVerifyEmailSmsResponseTest : TestCase() {
    @Before
    public override fun setUp() {
        super.setUp()
    }
    @Test
    fun test_LoginVerifyEmailSmsResponse() {
        val roles = listOf<String>()
        val expectedData = LoginVerifyData("token","type","1","username","email",roles)
        val loginVerifyData = LoginVerifyData("token","type","1","username","email",roles)
        loginVerifyData.email
        loginVerifyData.token
        loginVerifyData.type
        loginVerifyData.accountId
        loginVerifyData.username
        loginVerifyData.roles
        assertEquals(expectedData.token,loginVerifyData.token)

        val expectedResponse = LoginVerifyEmailSmsResponse(false,"message",loginVerifyData)
        val loginVerifyEmailSmsResponse = LoginVerifyEmailSmsResponse(false,"message",loginVerifyData)
        loginVerifyEmailSmsResponse.message
        loginVerifyEmailSmsResponse.status
        loginVerifyEmailSmsResponse.data
        assertEquals(expectedResponse.data,loginVerifyEmailSmsResponse.data)
    }
}