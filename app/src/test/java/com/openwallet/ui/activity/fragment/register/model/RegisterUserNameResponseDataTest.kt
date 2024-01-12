package com.openwallet.ui.activity.fragment.register.model

import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RegisterUserNameResponseDataTest : TestCase() {
    @Before
    public override fun setUp() {
        super.setUp()
    }
    @Test
    fun test_RegisterUserNameResponseData() {
        val expectedData = RegisterUserNameResponseData("token","111111")

        val registerUserNameResponseData = RegisterUserNameResponseData("token","111111")
        registerUserNameResponseData.token
        registerUserNameResponseData.captcha
        assertEquals(expectedData.token,registerUserNameResponseData.token)
    }
}