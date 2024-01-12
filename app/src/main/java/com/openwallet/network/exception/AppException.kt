package com.openwallet.network.exception

class AppException : Exception() {
    var errorMessage: String? = ""
    var errorCode = -1
}