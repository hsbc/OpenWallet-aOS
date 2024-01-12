package com.openwallet.ui.activity.fragment.profile.changeavater.model

import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UpdateAvatarResponseTest : TestCase() {
    @Before
    public override fun setUp() {
        super.setUp()
    }
    @Test
    fun test_UpdateAvatarResponse() {
        val expectedData = UpdateAvatarResponse("data","message")
        val updataAvatarResponse = UpdateAvatarResponse("data","message")
        updataAvatarResponse.message
        updataAvatarResponse.data
        assertEquals(expectedData.data,updataAvatarResponse.data)
    }
}