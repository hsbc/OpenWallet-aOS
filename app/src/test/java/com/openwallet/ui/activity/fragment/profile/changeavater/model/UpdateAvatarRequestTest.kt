package com.openwallet.ui.activity.fragment.profile.changeavater.model

import com.openwallet.ui.activity.fragment.logout.model.LogoutResponse
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UpdateAvatarRequestTest : TestCase() {
    @Before
    public override fun setUp() {
        super.setUp()
    }
    @Test
    fun test_UpdateAvatarRequest() {
        val expectedData = UpdateAvatarRequest("avatar")
        val updateAvatarRequest = UpdateAvatarRequest("avatar")
        updateAvatarRequest.avatar
        assertEquals(expectedData.avatar,updateAvatarRequest.avatar)
    }
}