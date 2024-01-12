package com.openwallet.ui.activity.fragment.register.model

import com.google.android.exoplayer2.text.Cue
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RegisterResponseDataTest : TestCase() {
    @Before
    public override fun setUp() {
        super.setUp()
    }
    @Test
    fun test_RegisterResponseData() {
        val expectedData = RegisterResponseData("data")
        val registerResponseData = RegisterResponseData("data")
        registerResponseData.data
        assertEquals(expectedData.data,registerResponseData.data)
    }
}