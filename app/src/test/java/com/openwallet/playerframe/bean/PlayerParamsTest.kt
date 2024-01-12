package com.openwallet.playerframe.bean

import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PlayerParamsTest : TestCase() {
    @Before
    public override fun setUp() {
        super.setUp()
    }
    @Test
    fun test_PlayerParams() {
        val playerParams = PlayerParams(false,false,false,"url",PlayerType.ANDROID_PLAYER,ScaleType.FULL_SCREEN,0,0,null,false,false,null)
        playerParams.url = "setUrl"
        playerParams.scaleType = ScaleType.CENTER
        playerParams.videoHeight = 800
        playerParams.videoWidth = 480
        playerParams.playerContainer = null

        playerParams.url
        playerParams.autoCache
        playerParams.loop
        playerParams.autoPlay
        playerParams.playerType
        playerParams.scaleType
        playerParams.videoWidth
        playerParams.videoHeight
        playerParams.needCurrentProgressNotify
        playerParams.playerContainer
        playerParams.headers
        playerParams.cacheVideo
    }
}