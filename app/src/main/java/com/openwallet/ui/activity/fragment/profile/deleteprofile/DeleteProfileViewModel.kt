package com.openwallet.ui.activity.fragment.profile.deleteprofile

import androidx.lifecycle.MutableLiveData
import com.openwallet.base.BaseViewModel
import com.openwallet.ext.request
import com.openwallet.network.ApiRepository
import com.openwallet.network.state.ResultState
import com.openwallet.ui.activity.fragment.wallet.model.NetworkResponse
import javax.inject.Inject

class DeleteProfileViewModel @Inject constructor(
    private val repository: ApiRepository
) : BaseViewModel() {

    fun requestDeleteProfile(): MutableLiveData<ResultState<NetworkResponse<String>>> {
        val response = MutableLiveData<ResultState<NetworkResponse<String>>>()
        request({ repository.deleteUserProfile() }, response)
        return response
    }


}
