package com.openwallet.ui.activity.fragment.profile

import com.openwallet.ui.activity.fragment.profile.notification.model.NotificationResponse
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TermsAndConditionsInfoTest : TestCase() {
    @Before
    public override fun setUp() {
        super.setUp()
    }
    @Test
    fun test_TermsAndConditionsInfo() {
        val expectedData = TermsAndConditionsInfo("content")
        val termsAndConditionsInfo = TermsAndConditionsInfo("content")
        termsAndConditionsInfo.content
        assertEquals(expectedData.content,termsAndConditionsInfo.content)
    }
}