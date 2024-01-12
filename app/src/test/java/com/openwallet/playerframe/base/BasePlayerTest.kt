package com.openwallet.playerframe.base

import com.openwallet.playerframe.bean.PlayerParams
import com.openwallet.playerframe.bean.PlayerType
import com.openwallet.playerframe.bean.ScaleType
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class BasePlayerTest : TestCase() {
    @Before
    public override fun setUp() {
        super.setUp()
    }
    @Test
    fun testPlay() {
        val playerParams = PlayerParams(false,false,false,"url",
            PlayerType.ANDROID_PLAYER,
            ScaleType.FULL_SCREEN,0,0,null,false,false,null)
        //val basePlayer = BasePlayer(playerParams)
        //basePlayer.pause()
    }
}