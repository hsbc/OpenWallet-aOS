package com.openwallet.ui.activity.fragment.profile.notification.model

import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class NotificationResponseTest : TestCase() {
    @Before
    public override fun setUp() {
        super.setUp()
    }
    @Test
    fun test_NotificationResponse() {
        var list = ArrayList<NotificationResponse.NotificationResponseItem>()
        val expectedData = NotificationResponse.NotificationResponseItem("1",1,"message",System.currentTimeMillis(),"1","title",true)

        val notificationResponseItem = NotificationResponse.NotificationResponseItem("1",1,"message",System.currentTimeMillis(),"1","title",false)
        notificationResponseItem.isExpand = true
        notificationResponseItem.status = "1"
        notificationResponseItem.category
        notificationResponseItem.createTime
        notificationResponseItem.id
        notificationResponseItem.isExpand
        notificationResponseItem.message
        notificationResponseItem.status
        notificationResponseItem.title

        assertEquals(expectedData.isExpand,notificationResponseItem.isExpand)

        list.add(notificationResponseItem)
        val expectedResponse = NotificationResponse("1","message",list)
        val notificationResponse = NotificationResponse("1","message",list)
        notificationResponse.message
        notificationResponse.status
        notificationResponse.data
        notificationResponse.hasUnread()
        assertEquals(expectedResponse.data,notificationResponse.data)
    }
}