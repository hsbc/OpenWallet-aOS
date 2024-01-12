package com.openwallet.playerframe.impl

import android.view.Surface
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.video.VideoSize
import com.openwallet.app.OpenWalletApplication
import com.openwallet.playerframe.base.BasePlayer
import com.openwallet.playerframe.bean.PlayerParams
import com.openwallet.playerframe.bean.PlayerState
import com.openwallet.playerframe.utils.LogUtil

class ExoPlayer(playerParams: PlayerParams) : BasePlayer(playerParams) {

    lateinit var player: ExoPlayer
    lateinit var videoSource: ProgressiveMediaSource


    override fun doRelease() {
        player.release()
    }

    override fun doStop() {
        player.stop()
    }

    override fun doPause() {
        player.pause()
    }

    override fun doPlay() {
        player.play()
    }

    override fun doPrepare() {
        player.prepare()
    }

    override fun initPlayer() {
        player = ExoPlayer.Builder(OpenWalletApplication.instance)
            .setLoadControl(
                DefaultLoadControl.Builder()
                    .setBufferDurationsMs(
                        mPlayerParams.minBufferMs,
                        mPlayerParams.maxBufferMs,
                        mPlayerParams.bufferForPlaybackMs,
                        mPlayerParams.bufferForPlaybackAfterRebufferMs
                    )
                    .build()
            )
            .build()
        // if the url is local file we must use DefaultDataSource otherwise we need use DefaultHttpDataSource
        val dataSourceFactory = if (mPlayerParams.url.startsWith("file://")) {
            DefaultDataSource.Factory(OpenWalletApplication.instance)
        } else {
            DefaultHttpDataSource.Factory().apply {
                mPlayerParams.headers?.run {
                    setDefaultRequestProperties(this)
                }
            }
        }
        val mediaItem = MediaItem.Builder()
            .setDrmConfiguration(
                MediaItem.DrmConfiguration.Builder(C.WIDEVINE_UUID)
                    .build()
            )
            .setUri(mPlayerParams.url)
            .build()
        videoSource = ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(mediaItem)
        player.playWhenReady = mPlayerParams.autoPlay
        player.repeatMode =
            if (mPlayerParams.loop) Player.REPEAT_MODE_ONE else Player.REPEAT_MODE_OFF
        player.addListener(object : Player.Listener {

            override fun onPlayerError(error: PlaybackException) {
                super.onPlayerError(error)
                notifyError(error.errorCode, error.message ?: "")
            }

            override fun onRenderedFirstFrame() {
                super.onRenderedFirstFrame()
                notifyStateChanged(PlayerState.PLAYING)
            }

            override fun onPlaybackStateChanged(playbackState: Int) {
                super.onPlaybackStateChanged(playbackState)
                LogUtil.i(msg = "exo  onPlaybackStateChanged,state:$playbackState")
                when (playbackState) {
                    Player.STATE_READY -> notifyPrepared()
                    Player.STATE_ENDED -> notifyStateChanged(PlayerState.COMPLETED)
                }
            }

            override fun onVideoSizeChanged(videoSize: VideoSize) {
                super.onVideoSizeChanged(videoSize)
                notifyVideoSizeChanged(videoSize.width, videoSize.height)
            }

        })
        player.setMediaSource(videoSource)
    }

    override fun setSurface(surface: Surface) {
        player.setVideoSurface(surface)
    }


}