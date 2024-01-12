package com.openwallet.playerframe.bean

import android.widget.FrameLayout

/**
 *  The player initialize params
 *  if you dont know videoWidth and videoHeight you can set zero,
 *  player frame will auto change the scale type by video size listener
 */
data class PlayerParams(
    val loop: Boolean = false,
    val autoPlay: Boolean = false,
    /**
     *  If true, the player will download while playing
     */
    val autoCache: Boolean = false,
    var url: String,
    val playerType: PlayerType = PlayerType.ANDROID_PLAYER,
    var scaleType: ScaleType = ScaleType.FULL_SCREEN,
    var videoWidth: Int = 0,
    var videoHeight: Int = 0,
    var playerContainer: FrameLayout? = null,
    /**
     *  if false the onProgressChanged method of IPlayerListener will not be call
     */
    val needCurrentProgressNotify: Boolean = false,

    /**
     * If true, the player will download while playing
     */
    val cacheVideo: Boolean = false,
    val headers: Map<String, String>? = null,

    /**
     * The minimum duration of media that the player will attempt to ensure
     * is buffered at all times, in milliseconds
     */
    val minBufferMs: Int = 5000,
    /**
     * The maximum duration of media that the player will attempt to buffer, in milliseconds
     */
    val maxBufferMs: Int = 5000,
    /**
     * The duration of media that must be buffered for playback to start or resume following
     * a user action such as a seek, in milliseconds
     */
    val bufferForPlaybackMs: Int = 5000,
    /**
     * The default duration of media that must be buffered for playback to resume after
     * a rebuffer, in milliseconds. A rebuffer is defined to be caused by buffer depletion
     *  rather than a user action
     */
    val bufferForPlaybackAfterRebufferMs: Int = 5000

)
