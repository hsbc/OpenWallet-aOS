package com.openwallet.ui.activity.fragment.register.model

import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RegisterUserPasswordResponseDataTest : TestCase() {
    @Before
    public override fun setUp() {
        super.setUp()
    }
    @Test
    fun testGetToken() {
        val expectedData = RegisterUserPasswordResponseData("token","111111")
        val registerUserPasswordResponseData = RegisterUserPasswordResponseData("token","111111")
        registerUserPasswordResponseData.token
        registerUserPasswordResponseData.captcha
        assertEquals(expectedData.token,registerUserPasswordResponseData.token)
    }
}