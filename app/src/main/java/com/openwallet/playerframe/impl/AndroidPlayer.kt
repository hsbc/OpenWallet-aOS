package com.openwallet.playerframe.impl

import android.media.MediaPlayer
import android.net.Uri
import android.view.Surface
import com.openwallet.app.OpenWalletApplication
import com.openwallet.constants.HEADER_TOKEN_START
import com.openwallet.manager.CacheManager
import com.openwallet.playerframe.bean.PlayerParams
import com.openwallet.playerframe.bean.PlayerState
import com.openwallet.playerframe.base.BasePlayer

/**
 *  To use MediaPlayer who is provided by android sdk
 */
class AndroidPlayer(playerParams: PlayerParams) : BasePlayer(playerParams) {

    private var mPlayer: MediaPlayer? = null

    override fun initPlayer() {
        mPlayer = MediaPlayer().apply {
            isLooping = mPlayerParams.loop
            setScreenOnWhilePlaying(true)
            setOnCompletionListener { notifyStateChanged(PlayerState.COMPLETED) }
            setOnErrorListener { _, what, extra ->
                notifyError(what, "Android Player Error,extra : $extra")
                false
            }
            setOnPreparedListener {
                notifyPrepared()
            }
            setOnVideoSizeChangedListener { _, width, height ->
                notifyVideoSizeChanged(
                    width,
                    height
                )
            }
            setOnInfoListener { _, what, extra ->
                when (what) {
                    MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START -> notifyStateChanged(PlayerState.PLAYING)
                }
                false
            }
            setDataSource(
                OpenWalletApplication.instance,
                Uri.parse(mPlayerParams.url),
                mPlayerParams.headers
            )
        }
    }


    override fun doPrepare() {
        mPlayer?.prepareAsync()
    }

    override fun doStop() {
        mPlayer?.stop()
    }

    override fun doPause() {
        mPlayer?.pause()
        notifyStateChanged(PlayerState.PAUSED)
    }

    override fun doPlay() {
        mPlayer?.start()
    }

    override fun doRelease() {
        mPlayer?.run {
            stop()
            setSurface(null)
            setScreenOnWhilePlaying(false)
            setOnCompletionListener(null)
            setOnErrorListener(null)
            setOnInfoListener(null)
            setOnSeekCompleteListener(null)
            setOnVideoSizeChangedListener(null)
            release()
            mPlayer = null
        }
    }

    override fun setSurface(surface: Surface) {
        mPlayer?.setSurface(surface)
    }
}