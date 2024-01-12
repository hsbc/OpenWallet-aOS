package com.openwallet.playerframe.impl

import android.media.MediaPlayer
import com.openwallet.playerframe.bean.PlayerParams
import com.openwallet.playerframe.bean.PlayerType
import com.openwallet.playerframe.bean.ScaleType
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AndroidPlayerTest : TestCase() {
    @Mock
    lateinit var mediaPlayer: MediaPlayer
    lateinit var androidPlayer: AndroidPlayer
    @Before
    public override fun setUp() {
        super.setUp()
        val playerParams = PlayerParams(false,false,false,"url",
            PlayerType.ANDROID_PLAYER,
            ScaleType.FULL_SCREEN,0,0,null,false,false,null)

        //androidPlayer = AndroidPlayer(playerParams)
    }
    @Test
    fun testInitPlayer() {
    }
    @Test
    fun testDoPrepare() {
        //androidPlayer.prepare()
    }
    @Test
    fun testDoStop() {}
    @Test
    fun testDoPause() {}
    @Test
    fun testDoPlay() {}
    @Test
    fun testDoRelease() {}
    @Test
    fun testSetSurface() {}
}