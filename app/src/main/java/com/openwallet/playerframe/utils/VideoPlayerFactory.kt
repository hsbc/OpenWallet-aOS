package com.openwallet.playerframe.utils

import com.openwallet.playerframe.bean.PlayerParams
import com.openwallet.playerframe.bean.PlayerType
import com.openwallet.playerframe.base.IVideoPlayer
import com.openwallet.playerframe.impl.AndroidPlayer
import com.openwallet.playerframe.impl.ExoPlayer

object VideoPlayerFactory {

    fun createPlayer(params: PlayerParams) : IVideoPlayer {
        return when(params.playerType){
            PlayerType.ANDROID_PLAYER -> AndroidPlayer(params)
            PlayerType.EXO_PLAYER -> ExoPlayer(params)
            else -> throw java.lang.RuntimeException("The ${params.playerType} not support")
        }
    }

}