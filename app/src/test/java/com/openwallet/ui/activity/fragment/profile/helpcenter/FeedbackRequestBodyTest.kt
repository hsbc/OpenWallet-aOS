package com.openwallet.ui.activity.fragment.profile.helpcenter

import com.openwallet.ui.activity.fragment.profile.changeavater.model.UpdateAvatarRequest
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FeedbackRequestBodyTest : TestCase() {
    @Before
    public override fun setUp() {
        super.setUp()
    }
    @Test
    fun test_FeedbackRequestBody() {
        val expectedData = FeedbackRequestBody("content")
        val feedbackRequestBody = FeedbackRequestBody("content");
        feedbackRequestBody.content
        assertEquals(expectedData.content,feedbackRequestBody.content)
    }
}