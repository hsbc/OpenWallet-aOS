package com.openwallet.ui.activity.fragment.register.model

import androidx.annotation.Keep

@Keep
data class RegisterSendPhoneSmsResponse(
    val status: Boolean?,
    val message: String?,
    val data: RegisterVerifyEmailSmsData?
)

@Keep
data class RegisterSendPhoneSmsData(
    val token: String?,
    val captcha: String?
)