package com.openwallet.ui.activity.fragment.resetpassword.model

import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ResetPasswordResponseDataTest : TestCase() {
    @Before
    public override fun setUp() {
        super.setUp()
    }
    @Test
    fun test_ResetPasswordResponseData() {
        val expectedData = ResetPasswordResponseData("data")
        val resetPasswordResponseData = ResetPasswordResponseData("data")
        resetPasswordResponseData.data
        assertEquals(expectedData.data,resetPasswordResponseData.data)
    }
}