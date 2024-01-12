package com.openwallet.playerframe.utils

import com.openwallet.playerframe.base.IVideoPlayer
import com.openwallet.playerframe.bean.PlayerParams
import com.openwallet.playerframe.bean.PlayerType
import com.openwallet.playerframe.bean.ScaleType
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class VideoPlayerFactoryTest : TestCase() {
    @Before
    public override fun setUp() {
        super.setUp()
    }
    @Test
    fun testCreatePlayer() {
        val playerParams = PlayerParams(false,false,false,"url",
            PlayerType.ANDROID_PLAYER,
            ScaleType.FULL_SCREEN,0,0,null,false,false,null)
        val player = mockk<IVideoPlayer>()
        //every { VideoPlayerFactory. }
        //VideoPlayerFactory.createPlayer(playerParams)
    }
}