package com.openwallet.ui.activity.fragment.login.model

import androidx.annotation.Keep

@Keep
data class LoginVerifyEmailSmsResponse(
    val status: Boolean?,
    val message: String?,
    val data: LoginVerifyData?
)

