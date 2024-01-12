package com.openwallet.ui.activity.fragment.profile.changeavater.model

import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ProfileInfoTest : TestCase() {
    @Before
    public override fun setUp() {
        super.setUp()
    }
    @Test
    fun test_ProfileInfo() {
        val expectedData = ProfileInfo("username","email","1","15911111111","avatar",false)
        val profileInfo = ProfileInfo("username","email","1","15911111111","avatar",false)
        profileInfo.avatar
        profileInfo.emailAddress
        profileInfo.marketingEnabled
        profileInfo.phoneCountryCode
        profileInfo.phoneNumber
        profileInfo.userName
        assertEquals(expectedData.userName,profileInfo.userName)
    }
}