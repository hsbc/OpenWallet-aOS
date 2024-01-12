package com.openwallet.playerframe.base

import android.view.Surface
import android.widget.FrameLayout
import com.openwallet.playerframe.bean.PlayerState
import com.openwallet.playerframe.bean.ScaleType

interface IVideoPlayer {

    interface IPlayerListener {

        fun onError(code: Int, msg: String)

        fun onStateChanged(state: PlayerState)

        /**
         *  This method will be call when the video size changed
         */
        fun onVideoSizeChanged(width: Int, height: Int)

        /**
         * When the network is not good, the player may be in the state of loading buffer
         */
        fun onLoadingStart()

        /**
         * the player loading buffer completed
         */
        fun onLoadingEnd()

    }

    fun getPlayerState(): PlayerState

    fun addPlayerListener(listener: IPlayerListener)

    fun removePlayerListener(listener: IPlayerListener)

    fun prepare()

    fun play()

    fun stop()

    fun release()

    fun pause()

    fun setSurface(surface: Surface)

    fun setPlayerContainer(container: FrameLayout?)

    fun setScaleType(scaleType: ScaleType)
}