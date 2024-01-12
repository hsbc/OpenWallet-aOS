package com.openwallet.network.state

import androidx.lifecycle.MutableLiveData
import com.openwallet.network.exception.AppException
import com.openwallet.network.exception.ErrorDispatcher

sealed class ResultState<out T> {
    companion object {
        fun <T> onAppSuccess(data: T): ResultState<T> = Success(data)
        fun <T> onAppLoading(): ResultState<T> = Loading
        fun <T> onAppInnerError(error: AppException): ResultState<T> = InnerError(error, ErrorDispatcher.getErrorCode(error.errorMessage.orEmpty()))
        fun <T> onNetworkOrExceptionError(error: AppException): ResultState<T> = NetworkOrExceptionError(error)
        fun <T> onAppEmpty(): ResultState<T> = Empty

    }

    object Loading : ResultState<Nothing>()
    data class Success<out T>(val data: T) : ResultState<T>()
    data class InnerError(val error: AppException, val innerErrorCode: String?) : ResultState<Nothing>()
    data class NetworkOrExceptionError(val error: AppException) : ResultState<Nothing>()
    object Empty : ResultState<Nothing>()
    object HTTP401 : ResultState<Nothing>()
}

fun <T> MutableLiveData<ResultState<T>>.paresResult(result: T) {
    value = ResultState.onAppSuccess(result)
}

fun <T> MutableLiveData<ResultState<T>>.paresException(error: AppException) {
    if (error.errorCode == 401) {
        value = ResultState.HTTP401
        return
    }
    value = ResultState.onNetworkOrExceptionError(error)
}



