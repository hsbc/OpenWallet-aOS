package com.openwallet.ui.activity.fragment.profile.changeavater

import androidx.lifecycle.MutableLiveData
import com.openwallet.base.BaseViewModel
import com.openwallet.ext.request
import com.openwallet.network.ApiRepository
import com.openwallet.network.state.ResultState
import com.openwallet.ui.activity.fragment.profile.changeavater.model.ProfileInfo
import com.openwallet.ui.activity.fragment.profile.changeavater.model.UpdateAvatarRequest
import com.openwallet.ui.activity.fragment.wallet.model.NetworkResponse
import javax.inject.Inject

class ProfileAvatarViewModel @Inject constructor(
    private val repository: ApiRepository
) : BaseViewModel() {

    fun updateAvatar(avatarName: String): MutableLiveData<ResultState<NetworkResponse<ProfileInfo>>> {
        val response = MutableLiveData<ResultState<NetworkResponse<ProfileInfo>>>()
        request({ repository.updateProfile(UpdateAvatarRequest(avatarName)) }, response)
        return response
    }
}