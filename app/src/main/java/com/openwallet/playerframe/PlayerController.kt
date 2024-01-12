package com.openwallet.playerframe

import com.openwallet.playerframe.base.IRequestHeaderListener
import com.openwallet.playerframe.base.IVideoPlayer
import com.openwallet.playerframe.bean.PlayInfo
import com.openwallet.playerframe.bean.PlayerParams
import com.openwallet.playerframe.utils.LogUtil
import com.openwallet.playerframe.utils.VideoCacheUtil
import com.openwallet.playerframe.utils.VideoPlayerFactory

class PlayerController private constructor() {

    // key:tag
    private val mPlayerMap: MutableMap<String, PlayInfo> = HashMap()
    private var mVideoCacheUtil: VideoCacheUtil? = null

    companion object {
        private val INSTANCE = PlayerController()

        fun getInstance(): PlayerController = INSTANCE
    }

    private fun initVideoCacheUtil() {
        if (mVideoCacheUtil == null) {
            mVideoCacheUtil = VideoCacheUtil(mHeaderListener)
        }
    }

    fun initPlayer(tag: String, playerParams: PlayerParams): IVideoPlayer {
        if (playerParams.autoCache) {
            initVideoCacheUtil()
        }
        val url = playerParams.url
        return if (mPlayerMap.containsKey(tag)) {
            mPlayerMap[tag]!!.player
        } else {
            if (playerParams.autoCache) {
                LogUtil.i(
                    msg = "initPlayer,url:${playerParams.url} is cached :${
                        mVideoCacheUtil!!.isCached(
                            playerParams.url
                        )
                    }"
                )
                playerParams.url = mVideoCacheUtil!!.getProxyUrl(url)
            }
            VideoPlayerFactory.createPlayer(playerParams).apply {
                mPlayerMap[tag] = PlayInfo(this, playerParams)
            }
        }
    }

    fun getPlayer(tag: String): IVideoPlayer? =
        if (mPlayerMap.containsKey(tag)) mPlayerMap[tag]!!.player else null

    fun destroyPlayer(tag: String) {
        if (mPlayerMap.containsKey(tag)) {
            val playerInfo = mPlayerMap[tag]!!
            playerInfo.player.release()
            mPlayerMap.remove(tag)

        }
    }

    fun destroyPlayer(player: IVideoPlayer?) {
        player?.run {
            for (data in mPlayerMap) {
                if (data.value.player == this) {
                    mPlayerMap.remove(data.key)
                }
            }
        }
    }

    fun destroyAllPlayer() {
        mPlayerMap.forEach { (t, u) ->
            u.player.release()
        }
        mPlayerMap.clear()
    }

    private val mHeaderListener = object : IRequestHeaderListener {
        override fun getHeaders(url: String): Map<String, String>? {
            LogUtil.i(msg = "PlayerController --> getHeaders:${url}")
            var map: Map<String, String>? = null
            for (data in mPlayerMap) {
                if (data.value.params.url == mVideoCacheUtil!!.getProxyUrl(url)) {
                    map = data.value.params.headers
                    break
                }
            }
            return map ?: HashMap()
        }

    }
}