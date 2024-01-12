package com.openwallet.ui.activity.fragment.profile.changepassword.model

import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonObject
import com.openwallet.app.OpenWalletApplication
import com.openwallet.base.BaseViewModel
import com.openwallet.ext.request
import com.openwallet.network.ApiRepository
import com.openwallet.network.state.ResultState
import com.openwallet.ui.activity.fragment.login.model.FirstFactorResponseData
import com.openwallet.ui.activity.fragment.login.model.VerifyScenario
import com.openwallet.ui.activity.fragment.wallet.model.NetworkResponse
import javax.inject.Inject

class ChangePasswordViewModel @Inject constructor(
    private val repository: ApiRepository,
) : BaseViewModel() {


    fun changePasswordWithFirstFactor(
        password: String
    ): MutableLiveData<ResultState<NetworkResponse<FirstFactorResponseData>>> {
        val response: MutableLiveData<ResultState<NetworkResponse<FirstFactorResponseData>>> = MutableLiveData()
        request(
            {
                val request = JsonObject()
                request.addProperty("password", password)
                repository.changePasswordWithFirstFactor(request, VerifyScenario.CHANGE_PASSWORD.scenario)
            },
            response
        )
        return response
    }

    fun changePassword(password: String): MutableLiveData<ResultState<NetworkResponse<ChangePasswordResponse>>> {
        val response: MutableLiveData<ResultState<NetworkResponse<ChangePasswordResponse>>> = MutableLiveData()
        request(
            {
                val request = JsonObject()
                request.addProperty("password", password)
                request.addProperty("token",OpenWalletApplication.appViewModel.secretToken.value)
                repository.changePassword(request)
            },
            response
        )
        return response
    }
}