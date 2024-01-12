package com.openwallet.ui.activity.fragment.profile.notification.model

import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class NotificationUpdateStatusResponseTest : TestCase() {
    @Before
    public override fun setUp() {
        super.setUp()
    }
    @Test
    fun test_NotificationUpdateStatusResponse() {
        val expectedData = NotificationUpdateStatusResponse("1","data","message")
        val notificationUpdateStatusResponse = NotificationUpdateStatusResponse("1","data","message")
        notificationUpdateStatusResponse.message
        notificationUpdateStatusResponse.status
        notificationUpdateStatusResponse.data
        assertEquals(expectedData.data,notificationUpdateStatusResponse.data)
    }
}