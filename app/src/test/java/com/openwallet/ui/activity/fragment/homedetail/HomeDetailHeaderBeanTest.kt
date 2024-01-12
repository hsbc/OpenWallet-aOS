package com.openwallet.ui.activity.fragment.homedetail

import com.openwallet.R
import com.openwallet.ui.activity.fragment.home.HomeBanner
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class HomeDetailHeaderBeanTest : TestCase() {
    @Before
    public override fun setUp() {
        super.setUp()
    }
    @Test
    fun test_HomeDetailHeaderBean() {
        val expectedData = HomeDetailHeaderBean("title",0,"")
        val homeDetailHeaderBean = HomeDetailHeaderBean("",0,"")
        homeDetailHeaderBean.imgRes = R.drawable.bg_border
        homeDetailHeaderBean.title = "title"
        homeDetailHeaderBean.subTitle = "subTitle"

        System.out.println(String.format("title:%s,subTitle:%s,imgRes:%s", homeDetailHeaderBean.title,homeDetailHeaderBean.subTitle,homeDetailHeaderBean.imgRes))
        assertEquals(expectedData.title,homeDetailHeaderBean.title)

        val expectedBean = ImageBean(R.drawable.bg_border)
        val imageBean = ImageBean(0)
        imageBean.imgRes = R.drawable.bg_border
        imageBean.imgRes
        assertEquals(expectedBean.imgRes,imageBean.imgRes)

        val expectedImageUriBean = ImageUriBean("mnt/sdcard/test.png")
        val imageUriBean = ImageUriBean("")
        imageUriBean.imgUrl = "mnt/sdcard/test.png"
        imageUriBean.imgUrl
        assertEquals(expectedImageUriBean.imgUrl,imageUriBean.imgUrl)

        val defaultConstructorMarker = TitleTextBean("")
        var expectedTitleTextBean = TitleTextBean("title",24f,0f,32f)
        var titleTextBean = TitleTextBean("",24f,0f,32f)
        titleTextBean.title = "title"
        titleTextBean.title
        titleTextBean.textSize
        titleTextBean.marginTop
        titleTextBean.marginBottom
        assertEquals(expectedTitleTextBean.title,titleTextBean.title)

        var expectedTextBean = TextBean("body")
        var textBean = TextBean("")
        textBean.body = "body"
        textBean.body
        assertEquals(expectedTextBean.body,textBean.body)
    }
}