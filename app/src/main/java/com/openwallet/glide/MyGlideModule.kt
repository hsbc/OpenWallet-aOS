package com.openwallet.glide

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.annotation.GlideOption
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.BaseRequestOptions
import com.bumptech.glide.request.RequestOptions
import com.openwallet.R
import okhttp3.OkHttpClient
import javax.inject.Inject


@GlideModule
class MyGlideModule() : AppGlideModule() {

    @Inject
    lateinit var okHttpClient: OkHttpClient

    override fun isManifestParsingEnabled(): Boolean {
        return false
    }

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {


    }

}

@GlideOption
fun commonOptions(): BaseRequestOptions<*> {
    return RequestOptions()
        .placeholder(R.drawable.ic_bg_img_loading)
        .error(R.drawable.ic_bg_img_load_fail)
        .fallback(R.drawable.ic_bg_img_load_fail)
}
