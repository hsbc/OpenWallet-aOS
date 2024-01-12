package com.openwallet.ui.activity.fragment.profile

import androidx.lifecycle.MutableLiveData
import com.openwallet.base.BaseViewModel
import com.openwallet.ext.request
import com.openwallet.network.ApiRepository
import com.openwallet.network.state.ResultState
import com.openwallet.ui.activity.fragment.profile.changeavater.model.ProfileInfo
import com.openwallet.ui.activity.fragment.wallet.model.NetworkResponse
import javax.inject.Inject

class ProfileMainViewModel @Inject constructor(
    private val repository: ApiRepository
) : BaseViewModel() {

    fun getProfile(): MutableLiveData<ResultState<NetworkResponse<ProfileInfo>>> {
        val response = MutableLiveData<ResultState<NetworkResponse<ProfileInfo>>>()
        request({ repository.getProfile() }, response)
        return response
    }

}