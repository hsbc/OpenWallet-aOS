package com.openwallet.playerframe.base

import android.view.ViewGroup.MarginLayoutParams
import android.widget.FrameLayout
import com.openwallet.playerframe.TextureView
import com.openwallet.playerframe.bean.PlayerParams
import com.openwallet.playerframe.bean.PlayerState
import com.openwallet.playerframe.bean.ScaleType
import com.openwallet.playerframe.impl.SimplePlayerListener
import com.openwallet.playerframe.utils.LogUtil
import com.openwallet.playerframe.utils.VideoScaleUtil
import com.openwallet.util.CommonUtils

abstract class BasePlayer(val mPlayerParams: PlayerParams) : IVideoPlayer {

    private var mListenerList = ArrayList<IVideoPlayer.IPlayerListener>()
    private var mState = PlayerState.IDLE
    private var mPlayerContainer: FrameLayout? = null
    private var mTextureView: TextureView? = null

    init {
        setPlayerContainer(mPlayerParams.playerContainer)
        initPlayer()
        if (mPlayerParams.autoPlay) {
            prepare()
        }
    }

    final override fun addPlayerListener(listener: IVideoPlayer.IPlayerListener) {
        if (!mListenerList.contains(listener)) {
            mListenerList.add(listener)
        }
    }

    final override fun removePlayerListener(listener: IVideoPlayer.IPlayerListener) {
        mListenerList.remove(listener)
    }

    final override fun prepare() {
        if (mState == PlayerState.IDLE) {
            notifyStateChanged(PlayerState.PREPARING)
            doPrepare()
        }
    }

    final override fun play() {
        if (mState == PlayerState.PAUSED || mState == PlayerState.PREPARED) {
            doPlay()
        } else if (mState == PlayerState.STOPPED || mState == PlayerState.COMPLETED) {
            // Since the video needs to be replayed, we should call initPlayer and call the prepare method again
            mState = PlayerState.IDLE
            createOrChangeTextureView(true)
            initPlayer()
            prepare()
            addPlayerListener(mPlayerListener)
        }
    }

    private var mPlayerListener: SimplePlayerListener =
        object : SimplePlayerListener() {
            override fun onError(code: Int, msg: String) {
                removePlayerListener(this)
            }

            override fun onStateChanged(state: PlayerState) {
                when (state) {
                    PlayerState.COMPLETED, PlayerState.STOPPED -> removePlayerListener(this)
                    PlayerState.PREPARED -> doPlay()
                    else -> {}
                }
            }
        }

    final override fun getPlayerState(): PlayerState = mState

    final override fun setScaleType(scaleType: ScaleType) {
        mPlayerParams.scaleType = scaleType
        if (mPlayerContainer != null) {
            createOrChangeTextureView(false)
        }
    }

    final override fun pause() {
        if (mState == PlayerState.PLAYING) {
            doPause()
        }
    }

    final override fun stop() {
        if (mState != PlayerState.IDLE
            && mState != PlayerState.STOPPED
            && mState != PlayerState.RELEASED
            && mState != PlayerState.COMPLETED
        ) {
            doStop()
            notifyStateChanged(PlayerState.STOPPED)
        }
    }

    final override fun release() {
        if (mState != PlayerState.RELEASED) {
            doRelease()
            mTextureView?.run {
                mPlayerContainer?.removeView(this)
                mPlayerContainer = null
            }
            mTextureView = null
            notifyStateChanged(PlayerState.RELEASED)
        }
    }

    final override fun setPlayerContainer(container: FrameLayout?) {
        if (mState != PlayerState.RELEASED && mPlayerContainer != container) {
            mTextureView?.run { mPlayerContainer?.removeView(this) }
            mPlayerContainer = container
            createOrChangeTextureView(true)
        }
    }

    private fun createTextureViewLayoutParams(): MarginLayoutParams {
        // 为了防止某些播放器在载体宽高都设置为0的情况下，不会回调onVideoSizeChanged方法
        // 这里判断如果给他设置一个初始的值，onVideoSizeChanged回调后会自动修正
        if (mPlayerParams.videoWidth <= 0) {
            mPlayerParams.videoWidth = 10
        }
        if (mPlayerParams.videoHeight <= 0) {
            mPlayerParams.videoHeight = 10
        }
        val scaleInfo = VideoScaleUtil.calculateScaleInfo(
            mPlayerParams.videoWidth,
            mPlayerParams.videoHeight,
            mPlayerContainer!!.width,
            mPlayerContainer!!.height,
            mPlayerParams.scaleType
        )
        return FrameLayout.LayoutParams(scaleInfo.width, scaleInfo.height).apply {
            leftMargin = scaleInfo.leftMargin
            topMargin = scaleInfo.topMargin
        }
    }

    private fun createOrChangeTextureView(needCreate: Boolean) {
        if (mPlayerContainer == null) {
            return
        }
        val container = mPlayerContainer!!
        val action = {
            if (needCreate) {
                mTextureView = TextureView(container.context).apply {
                    setVideoPlayer(this@BasePlayer)
                    container.addView(this, createTextureViewLayoutParams())
                }
            } else {
                mTextureView?.layoutParams = createTextureViewLayoutParams()
            }
        }

        if (container.width == 0 || container.height == 0) {
            container.post(action)
        } else {
            action()
        }

    }

    protected fun notifyError(code: Int, msg: String) {
        LogUtil.i(msg = "notifyError,code:$code  msg:$msg")
        CommonUtils.runOnUIThread { mListenerList.forEach { it.onError(code, msg) } }
    }

    protected fun notifyStateChanged(state: PlayerState) {
        CommonUtils.runOnUIThread {
            if (mState == state) {
                return@runOnUIThread
            }
            mState = state
            LogUtil.i(msg = "state:$state")
            mListenerList.forEach {
                it.onStateChanged(state)
            }
            if (mState == PlayerState.RELEASED) {
                mListenerList.clear()
            }
        }

    }

    protected fun notifyVideoSizeChanged(width: Int, height: Int) {
        LogUtil.i(msg = "notifyVideoSizeChanged,width:$width  height:$height")
        if (mPlayerParams.videoWidth != width || mPlayerParams.videoHeight != height) {
            CommonUtils.runOnUIThread {
                mPlayerParams.videoWidth = width
                mPlayerParams.videoHeight = height
                createOrChangeTextureView(false)
                mListenerList.forEach {
                    it.onVideoSizeChanged(width, height)
                }
            }
        }
    }

    protected fun notifyPrepared() {
        notifyStateChanged(PlayerState.PREPARED)
        if (mPlayerParams.autoPlay) {
            play()
        }
    }

    protected abstract fun doRelease()

    protected abstract fun doStop()

    protected abstract fun doPause()

    protected abstract fun doPlay()

    protected abstract fun doPrepare()

    protected abstract fun initPlayer()

}