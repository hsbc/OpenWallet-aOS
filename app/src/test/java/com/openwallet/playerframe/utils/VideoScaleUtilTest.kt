package com.openwallet.playerframe.utils

import com.openwallet.playerframe.bean.ScaleType
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class VideoScaleUtilTest : TestCase() {
    @Before
    public override fun setUp() {
        super.setUp()
    }
    @Test
    fun testCalculateScaleInfo() {
        VideoScaleUtil.calculateScaleInfo(100,200,100,200,ScaleType.CENTER)
        VideoScaleUtil.calculateScaleInfo(100,200,100,200,ScaleType.FULL_SCREEN)
        VideoScaleUtil.calculateScaleInfo(100,200,100,200,ScaleType.FIT_SHORT_SIDE)
        VideoScaleUtil.calculateScaleInfo(100,200,100,200,ScaleType.FIT_LONG_SIDE)

        VideoScaleUtil.calculateScaleInfo(100,200,300,200,ScaleType.CENTER)
        VideoScaleUtil.calculateScaleInfo(100,200,300,200,ScaleType.FULL_SCREEN)
        VideoScaleUtil.calculateScaleInfo(100,200,300,200,ScaleType.FIT_SHORT_SIDE)
        VideoScaleUtil.calculateScaleInfo(100,200,300,200,ScaleType.FIT_LONG_SIDE)

    }
}