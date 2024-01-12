package com.openwallet.ui.activity.fragment.wallet.detail.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.openwallet.R
import com.openwallet.app.OpenWalletApplication
import com.openwallet.constants.HEADER_TOKEN_START
import com.openwallet.manager.CacheManager
import com.openwallet.playerframe.PlayerController
import com.openwallet.playerframe.bean.PlayerParams
import com.openwallet.playerframe.bean.PlayerState
import com.openwallet.playerframe.bean.PlayerType
import com.openwallet.playerframe.bean.ScaleType
import com.openwallet.playerframe.impl.SimplePlayerListener

class WalletNftVideoView(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {

    private var mTag: String? = null
    private var mMp4Url = "";

    private val mVideoContainer: FrameLayout
    private val mLoadingView: View

    init {
        LayoutInflater.from(context).inflate(R.layout.view_wallet_nft_video_view, this, true)
        mVideoContainer = findViewById(R.id.fl_video_container)
        mLoadingView = findViewById(R.id.loading_view)
    }

    fun setVideoInfo(tag: String, url: String) {
        mTag = tag
        mMp4Url = url
        createPlayer()
    }

    private fun createPlayer() {
        mTag?.run {
            PlayerController.getInstance().initPlayer(this,
                PlayerParams(
                    videoWidth = 0,
                    videoHeight = 0,
                    url = mMp4Url,
                    loop = true,
                    autoPlay = true,
                    autoCache = true,
                    scaleType = ScaleType.FIT_SHORT_SIDE,
                    playerContainer = mVideoContainer,
                    playerType = PlayerType.EXO_PLAYER,
                    minBufferMs = 1000,
                    maxBufferMs = 2000,
                    bufferForPlaybackMs = 500,
                    bufferForPlaybackAfterRebufferMs = 500,
                    headers = HashMap<String, String>().apply {
                        put(
                            "Authorization",
                            HEADER_TOKEN_START + OpenWalletApplication.appViewModel.loginToken
                        )
                        put("Source", CacheManager.getUUID())
                        put("Cookie", "1865OH=True")
                    }
                )).apply {
                addPlayerListener(mListener)
            }
        }
    }

    private val mListener = object : SimplePlayerListener() {
        override fun onStateChanged(state: PlayerState) {
            when (state) {
                PlayerState.PLAYING -> mLoadingView.visibility = View.GONE
                else -> {}
            }
        }
    }


    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        mLoadingView.visibility = View.VISIBLE
        createPlayer()

    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mTag?.run {
            PlayerController.getInstance().destroyPlayer(this)
        }
    }

}
