package com.openwallet.playerframe.bean

import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ScaleInfoTest : TestCase() {
    @Before
    public override fun setUp() {
        super.setUp()
    }
    @Test
    fun test_ScaleInfo() {
        val scaleInfo = ScaleInfo(0,0,0,0)
        scaleInfo.width
        scaleInfo.height
        scaleInfo.leftMargin
        scaleInfo.topMargin
    }
}