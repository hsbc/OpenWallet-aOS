package com.openwallet.app

import com.openwallet.base.BaseViewModel
import com.openwallet.model.UserInput
import com.openwallet.network.ApiRepository
import com.openwallet.playerframe.PlayerController
import com.openwallet.ui.activity.fragment.profile.changeavater.model.ProfileInfo
import com.openwallet.ui.activity.fragment.profile.notification.model.NotificationResponse
import com.kunminx.architecture.ui.callback.UnPeekLiveData
import javax.inject.Inject

class AppViewModel @Inject constructor(private val repository: ApiRepository) : BaseViewModel() {

    var tabIndex = UnPeekLiveData<Int>()
    var secretToken = UnPeekLiveData.Builder<String>().setAllowNullValue(true).create()

    var avatar = UnPeekLiveData<String>()
    var selectAvatar = UnPeekLiveData<String>()

    var profileInfo = UnPeekLiveData<ProfileInfo>()
    var isRedeemStatusChanged = UnPeekLiveData<Boolean>()

    @Volatile
    var loginToken: String? = ""
    var refreshToken: String? = ""

    var sessionTimeOut = UnPeekLiveData<Boolean>()
    val notificationList = UnPeekLiveData<NotificationResponse>()
    var userInput: UserInput? = null

    var loginStatus = UnPeekLiveData<Boolean>()

    init {
        loginStatus.value = false
        userInput = UserInput()
    }

    fun getApiRepository(): ApiRepository {
        return repository
    }

    fun destroyAllPlayer() {
        PlayerController.getInstance().destroyAllPlayer()
    }
}