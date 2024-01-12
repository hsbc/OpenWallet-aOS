package com.openwallet.playerframe.utils

import com.danikula.videocache.CacheListener
import com.danikula.videocache.HttpProxyCacheServer
import com.openwallet.app.OpenWalletApplication
import com.openwallet.playerframe.base.IRequestHeaderListener

class VideoCacheUtil(private val mHeaderListener: IRequestHeaderListener) {


    private var mCacheServer: HttpProxyCacheServer =
        HttpProxyCacheServer.Builder(OpenWalletApplication.instance)
            .headerInjector {
                val header = mHeaderListener.getHeaders(it)
                LogUtil.i(msg = "VideoCacheUtil,header:$header")
                header
            }
            .build()


    fun getProxyUrl(sourceUrl: String) = mCacheServer.getProxyUrl(sourceUrl)

    fun isCached(sourceUrl: String): Boolean = mCacheServer.isCached(sourceUrl)

    fun registerCacheListener(sourceUrl: String, cacheListener: CacheListener) {
        mCacheServer.registerCacheListener(cacheListener, sourceUrl)
    }

    fun removeCacheListener(cacheListener: CacheListener) {
        mCacheServer.unregisterCacheListener(cacheListener)
    }

    fun shutdown() {
        mCacheServer.shutdown()
    }

}