package com.openwallet.ui.activity.fragment.profile.changepassword.model

import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ChangePasswordResponseTest : TestCase() {
    @Before
    public override fun setUp() {
        super.setUp()
    }
    @Test
    fun test_ChangePasswordResponse() {
        val expectedData = ChangePasswordResponse("data")
        val changePasswordResponse = ChangePasswordResponse("data")
        changePasswordResponse.data
        assertEquals(expectedData.data,changePasswordResponse.data)
    }
}