package com.openwallet.playerframe.base

interface IRequestHeaderListener {

    fun getHeaders(url: String): Map<String, String>?

}