package com.openwallet.playerframe.bean

import com.openwallet.playerframe.base.IVideoPlayer
import io.mockk.mockk
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PlayInfoTest : TestCase() {
    @Before
    public override fun setUp() {
        super.setUp()
    }
    @Test
    fun test_PlayInfo() {
        val playerParams = PlayerParams(false,false,false,"url",PlayerType.ANDROID_PLAYER,ScaleType.FULL_SCREEN,0,0,null,false,false,null)
        val player = mockk<IVideoPlayer>()
        val playInfo = PlayInfo(player,playerParams)
        playInfo.params.toString()
        playInfo.player.toString()
    }
}