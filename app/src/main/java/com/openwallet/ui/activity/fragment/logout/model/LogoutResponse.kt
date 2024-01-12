package com.openwallet.ui.activity.fragment.logout.model

import androidx.annotation.Keep

@Keep
data class LogoutResponse(
    val status: Boolean?,
    val message: String?,
    val data: String?
)