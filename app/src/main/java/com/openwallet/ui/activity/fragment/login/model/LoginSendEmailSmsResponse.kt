package com.openwallet.ui.activity.fragment.login.model

import androidx.annotation.Keep

@Keep
data class LoginSendEmailSmsResponse(
    val status: Boolean?,
    val message: String?,
    val data: LoginSendEmailSmsData?
)

@Keep
data class LoginSendEmailSmsData(
    val token: String?,
    val captcha: String?
)

