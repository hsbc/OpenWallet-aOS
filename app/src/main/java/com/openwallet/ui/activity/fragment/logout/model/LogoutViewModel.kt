package com.openwallet.ui.activity.fragment.logout.model

import androidx.lifecycle.MutableLiveData
import com.openwallet.base.BaseViewModel
import com.openwallet.ext.request
import com.openwallet.network.ApiRepository
import com.openwallet.network.state.ResultState
import com.openwallet.ui.activity.fragment.wallet.model.NetworkResponse
import javax.inject.Inject

class LogoutViewModel @Inject constructor(
    private val repository: ApiRepository,
) : BaseViewModel() {
    fun logout(): MutableLiveData<ResultState<NetworkResponse<LogoutResponse>>> {
        val response: MutableLiveData<ResultState<NetworkResponse<LogoutResponse>>> = MutableLiveData()
        request(
            {
                repository.logout()
            },
            response
        )
        return response
    }
}

