package com.openwallet.ui.activity.fragment.profile.notification.model

import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class NotificationUpdateStatusRequestTest : TestCase() {
    @Before
    public override fun setUp() {
        super.setUp()
    }
    @Test
    fun test_NotificationUpdateStatusRequest() {
        val expectedData = NotificationUpdateStatusRequest(1)
        val notificationUpdateStatusRequest = NotificationUpdateStatusRequest(1)
        notificationUpdateStatusRequest.notificationId
        assertEquals(expectedData.notificationId,notificationUpdateStatusRequest.notificationId)
    }
}