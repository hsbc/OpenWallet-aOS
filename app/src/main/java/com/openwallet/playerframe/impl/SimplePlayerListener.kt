package com.openwallet.playerframe.impl

import com.openwallet.playerframe.base.IVideoPlayer
import com.openwallet.playerframe.bean.PlayerState

open class SimplePlayerListener : IVideoPlayer.IPlayerListener {

    override fun onError(code: Int, msg: String) {
    }

    override fun onStateChanged(state: PlayerState) {
    }

    override fun onVideoSizeChanged(width: Int, height: Int) {
    }

    override fun onLoadingStart() {
    }

    override fun onLoadingEnd() {
    }

}