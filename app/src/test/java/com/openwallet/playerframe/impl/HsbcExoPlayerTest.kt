//package com.openwallet.playerframe.impl
//
//import android.view.Surface
//import android.view.SurfaceControl
//import com.google.android.exoplayer2.ExoPlayer
//import com.google.android.exoplayer2.source.ProgressiveMediaSource
//import com.openwallet.playerframe.base.BasePlayer
//import com.openwallet.playerframe.bean.PlayerParams
//import com.openwallet.playerframe.bean.PlayerType
//import com.openwallet.playerframe.bean.ScaleType
//import io.mockk.MockKAnnotations
//import io.mockk.every
//import io.mockk.impl.annotations.MockK
//import io.mockk.mockk
//import io.mockk.mockkClass
//import junit.framework.TestCase
//import org.junit.Before
//import org.junit.Test
//import org.junit.runner.RunWith
//import org.mockito.BDDMockito.given
//import org.mockito.Mock
//import org.mockito.junit.MockitoJUnitRunner
//
//@RunWith(MockitoJUnitRunner::class)
//class ExoPlayerTest : TestCase() {
//    @Mock
//    lateinit var player: ExoPlayer
//    @Mock
//    lateinit var videoSource: ProgressiveMediaSource
//    @Mock
//    lateinit var basePlayer: BasePlayer
//
//    lateinit var ExoPlayer: ExoPlayer
//    @Before
//    public override fun setUp() {
//        super.setUp()
//        //MockKAnnotations.init(this)
//        //val playerParams = PlayerParams(false,false,false,"url",
//        //    PlayerType.ANDROID_PLAYER,
//        //    ScaleType.FULL_SCREEN,0,0,null,false,false,null)
//
//        /ExoPlayer = ExoPlayer(playerParams)
//
//        //given(ExoPlayer.player).willReturn(player)
//        //given(ExoPlayer.videoSource).willReturn(videoSource)
//    }
//   // @Test
//    fun testDoRelease() {
//        /ExoPlayer.
//    }
//
//    fun testDoStop() {}
//
//    fun testDoPause() {}
//
//    fun testDoPlay() {}
//
//    fun testDoPrepare() {}
//
//    fun testInitPlayer() {}
//    //@Test
//    fun testSetSurface() {
//        //val surfaceControl = mockkClass(SurfaceControl::class)
//        //val surface = Surface(surfaceControl)
//        /ExoPlayer.setSurface(surface)
//    }
//}