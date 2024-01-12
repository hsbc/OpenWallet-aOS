package com.openwallet.ui.activity.fragment.resetpassword.model

import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonObject
import com.openwallet.app.OpenWalletApplication
import com.openwallet.base.BaseViewModel
import com.openwallet.ext.request
import com.openwallet.network.ApiRepository
import com.openwallet.network.state.ResultState
import com.openwallet.ui.activity.fragment.wallet.model.NetworkResponse
import javax.inject.Inject

class ResetPasswordViewModel @Inject constructor(
    private val repository: ApiRepository,
) : BaseViewModel() {
    fun resetPassword(newPassword: String): MutableLiveData<ResultState<NetworkResponse<ResetPasswordResponseData>>> {
        val response: MutableLiveData<ResultState<NetworkResponse<ResetPasswordResponseData>>> = MutableLiveData()
        request(
            {
                val request = JsonObject()
                request.addProperty("newPassword", newPassword)
                request.addProperty("token",OpenWalletApplication.appViewModel.secretToken.value)
                repository.resetPassword(request)
            },
            response
        )
        return response
    }
}