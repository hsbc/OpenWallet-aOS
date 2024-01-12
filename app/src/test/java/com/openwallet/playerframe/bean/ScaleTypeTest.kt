package com.openwallet.playerframe.bean

import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ScaleTypeTest : TestCase() {
    @Before
    public override fun setUp() {
        super.setUp()
    }
    @Test
    fun test_ScaleType() {
        ScaleType.FIT_LONG_SIDE
        ScaleType.FIT_SHORT_SIDE
        ScaleType.CENTER
        ScaleType.FULL_SCREEN
    }
}