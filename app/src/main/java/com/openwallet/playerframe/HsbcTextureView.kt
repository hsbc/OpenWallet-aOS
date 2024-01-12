package com.openwallet.playerframe

import android.content.Context
import android.graphics.SurfaceTexture
import android.view.Surface
import android.view.TextureView
import com.openwallet.playerframe.base.IVideoPlayer

class TextureView(context: Context) : TextureView(context),
    TextureView.SurfaceTextureListener {

    private var mPlayer: IVideoPlayer? = null
    private var mSurface: Surface? = null
    private var mSurfaceTexture: SurfaceTexture? = null

    init {
        surfaceTextureListener = this
    }

    fun setVideoPlayer(player: IVideoPlayer) {
        mPlayer = player
    }

    override fun onSurfaceTextureAvailable(surface: SurfaceTexture, width: Int, height: Int) {
        if (mSurfaceTexture != null && !mSurfaceTexture!!.isReleased) {
            setSurfaceTexture(mSurfaceTexture!!)
        } else {
            mSurfaceTexture = surface
            mSurface = Surface(surface)
            mPlayer?.setSurface(mSurface!!)
        }
    }

    override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture, width: Int, height: Int) {
    }

    override fun onSurfaceTextureDestroyed(surface: SurfaceTexture): Boolean {
        mSurfaceTexture = surface
        return false
    }

    override fun onSurfaceTextureUpdated(surface: SurfaceTexture) {
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        surfaceTextureListener = null
        mSurfaceTexture?.release()
        mSurfaceTexture = null
        mSurface?.release()
        mSurface = null
    }
}