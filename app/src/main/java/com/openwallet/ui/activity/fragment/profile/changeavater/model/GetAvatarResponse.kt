package com.openwallet.ui.activity.fragment.profile.changeavater.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ProfileInfo(
    @SerializedName("username")
    val userName: String?,
    val emailAddress: String?,
    val phoneCountryCode: String?,
    val phoneNumber: String?,
    val avatar: String?,
    val marketingEnabled: Boolean?
)

