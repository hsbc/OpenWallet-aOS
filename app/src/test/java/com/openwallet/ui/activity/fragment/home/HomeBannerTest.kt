package com.openwallet.ui.activity.fragment.home

import com.openwallet.R
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test

class HomeBannerTest : TestCase() {
    @Before
    public override fun setUp() {
        super.setUp()
    }
    @Test
    fun test_HomeBanner() {
        val homeBannerDefaultConstructorMarker = HomeBanner(0)
        val expectedData = HomeBanner(0,"title","","")
        val homeBanner = HomeBanner(0,"","","")
        homeBanner.imageResId = R.drawable.bg_border
        homeBanner.title = "title"
        homeBanner.subtitle = "subtitle"
        homeBanner.body = "body"
        homeBanner.title
        homeBanner.subtitle
        homeBanner.imageResId
        homeBanner.body
        assertEquals(expectedData.title,homeBanner.title)
    }
}