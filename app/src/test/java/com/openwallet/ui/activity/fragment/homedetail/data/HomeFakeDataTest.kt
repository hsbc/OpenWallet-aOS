package com.openwallet.ui.activity.fragment.homedetail.data

import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class HomeFakeDataTest : TestCase() {
    @Before
    public override fun setUp() {
        super.setUp()
    }
    @Test
    fun testGetHomeBannerList() {
        val expectedData = HomeFakeData.getHomeBannerList()
        val actualResult = HomeFakeData.getHomeBannerList()
        assertEquals(expectedData.get(0).body,actualResult.get(0).body)
    }
    @Test
    fun testGetDetailData1() {
        HomeFakeData.getDetailData1()
    }
    @Test
    fun testGetDetailData2() {
        HomeFakeData.getDetailData2()
    }
    @Test
    fun testGetDetailData3() {
        HomeFakeData.getDetailData3()
    }
}