package com.openwallet.playerframe.utils

import io.mockk.every
import io.mockk.mockkObject
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LogUtilTest : TestCase() {
    @Before
    public override fun setUp() {
        super.setUp()
    }
    @Test
    fun testIsCanShowLog() {
        LogUtil.isCanShowLog()
    }
    @Test
    fun testI_isCanShowLog_true() {
        //Object 私有函数
        mockkObject(LogUtil, recordPrivateCalls = true)
        //isCanShowLog 设置为false
        every { LogUtil["isCanShowLog"]() } returns true

        LogUtil.i("tag","log i")
    }
    @Test
    fun testI_isCanShowLog_false() {
        //Object 私有函数
        mockkObject(LogUtil, recordPrivateCalls = true)
        //isCanShowLog 设置为false
        every { LogUtil["isCanShowLog"]() } returns false

        LogUtil.i("tag","log i")
    }
    @Test
    fun testE_isCanShowLog_true() {
        //Object 私有函数
        mockkObject(LogUtil, recordPrivateCalls = true)
        //isCanShowLog 设置为false
        every { LogUtil["isCanShowLog"]() } returns true

        LogUtil.e("tag","log e")
    }
    @Test
    fun testE_isCanShowLog_false() {
        //Object 私有函数
        mockkObject(LogUtil, recordPrivateCalls = true)
        //isCanShowLog 设置为false
        every { LogUtil["isCanShowLog"]() } returns false

        LogUtil.e("tag","log e")
    }
    @Test
    fun testD_isCanShowLog_true() {
        //Object 私有函数
        mockkObject(LogUtil, recordPrivateCalls = true)
        //isCanShowLog 设置为false
        every { LogUtil["isCanShowLog"]() } returns true

        LogUtil.d("tag","log d")
    }
    @Test
    fun testD_isCanShowLog_false() {
        //Object 私有函数
        mockkObject(LogUtil, recordPrivateCalls = true)
        //isCanShowLog 设置为false
        every { LogUtil["isCanShowLog"]() } returns false

        LogUtil.d("tag","log d")
    }
    @Test
    fun testW_isCanShowLog_true() {
        //Object 私有函数
        mockkObject(LogUtil, recordPrivateCalls = true)
        //isCanShowLog 设置为false
        every { LogUtil["isCanShowLog"]() } returns true

        LogUtil.w("tag","log w")

    }
    @Test
    fun testW_isCanShowLog_false() {
        //Object 私有函数
        mockkObject(LogUtil, recordPrivateCalls = true)
        //isCanShowLog 设置为false
        every { LogUtil["isCanShowLog"]() } returns false

        LogUtil.w("tag","log w")
    }
}