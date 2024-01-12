package com.openwallet.network.exception

import android.accounts.NetworkErrorException
import com.openwallet.R
import com.openwallet.app.OpenWalletApplication
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

class ExceptionEngineImpl : ExceptionEngine {
    override fun handleException(throwable: Throwable?): AppException {
        val ex = AppException()
        throwable?.let {
            try {
                when (it) {
                    is HttpException -> {
                        val errorResponse = (it.response() as Response).errorBody()?.string().orEmpty()
                        ex.errorMessage = JSONObject(errorResponse).get("message").toString()
                        ex.errorCode = it.code()
                    }

                    is ConnectException, is TimeoutException, is NetworkErrorException, is SocketTimeoutException,
                    is UnknownHostException, is java.io.IOException -> ex.errorMessage = "Network Exception!"

                }

            } catch (e: Exception) {
                e.printStackTrace()
                ex.errorMessage = OpenWalletApplication.instance.resources.getString(R.string.common_error_tips)
            }
        }
        return ex
    }

    override fun handleErrorMessage(message: String): AppException {
        val ex = AppException()
        ex.errorMessage = message
        var errorCode = ErrorDispatcher.getErrorCode(message)
        errorCode?.let {
            ErrorDispatcher.handleErrorByCode(it)
        }
        return ex
    }
}