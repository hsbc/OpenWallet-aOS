package com.openwallet.ui.activity.fragment.register.model

import androidx.annotation.Keep

@Keep
data class RegisterVerifyEmailSmsResponse(
    val status: Boolean?,
    val message: String?,
    val data: RegisterVerifyEmailSmsData?
)

@Keep
data class RegisterVerifyEmailSmsData(
    val token: String?,
    val captcha: String?
)