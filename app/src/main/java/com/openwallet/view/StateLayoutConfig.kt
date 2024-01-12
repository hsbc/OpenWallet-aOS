package com.openwallet.view

import com.openwallet.R

/**
 * 全局配置，对所有实例生效，但会被实例自己的配置覆盖
 */
object StateLayoutConfig {
    var animDuration = 120L
    var useContentBgWhenLoading = true //是否在Loading状态使用内容View的背景
    var enableLoadingShadow = false //是否启用加载状态时的半透明阴影
    var emptyText: String = ""
    var emptyIcon: Int = 0
    var enableTouchWhenLoading = false
    var defaultShowLoading = false
    var noEmptyAndError = false //是否去除empty和error状态，有时候只需要一个loading状态，这样减少内存
    var showLoadingOnce = false //是否只显示一次Loading
    var loadingLayoutId = R.layout.loading_layout_loading
    var emptyLayoutId = R.layout.loading_layout_error
    var errorLayoutId = R.layout.loading_layout_error

    fun init(animDuration: Long? = null, useContentBgWhenLoading: Boolean? = null,
             enableLoadingShadow: Boolean? = null, enableTouchWhenLoading: Boolean? = null,
             defaultShowLoading: Boolean? = null, noEmptyAndError: Boolean? = null,
             showLoadingOnce: Boolean? = null, emptyText: String? = null,
             emptyIcon: Int? = null, loadingLayoutId: Int? = null,
             emptyLayoutId: Int? = null, errorLayoutId: Int? = null
    ){
        if(animDuration!=null) StateLayoutConfig.animDuration = animDuration
        if(useContentBgWhenLoading!=null) StateLayoutConfig.useContentBgWhenLoading = useContentBgWhenLoading
        if(enableLoadingShadow!=null) StateLayoutConfig.enableLoadingShadow = enableLoadingShadow
        if(enableTouchWhenLoading!=null) StateLayoutConfig.enableTouchWhenLoading = enableTouchWhenLoading
        if(defaultShowLoading!=null) StateLayoutConfig.defaultShowLoading = defaultShowLoading
        if(noEmptyAndError!=null) StateLayoutConfig.noEmptyAndError = noEmptyAndError
        if(showLoadingOnce!=null) StateLayoutConfig.showLoadingOnce = showLoadingOnce
        if(emptyText!=null) StateLayoutConfig.emptyText = emptyText
        if(emptyIcon!=null) StateLayoutConfig.emptyIcon = emptyIcon
        if(loadingLayoutId!=null) StateLayoutConfig.loadingLayoutId = loadingLayoutId
        if(emptyLayoutId!=null) StateLayoutConfig.emptyLayoutId = emptyLayoutId
        if(errorLayoutId!=null) StateLayoutConfig.errorLayoutId = errorLayoutId
    }
}