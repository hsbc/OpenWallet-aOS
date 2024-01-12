package com.openwallet.ui.activity.fragment.login.model

import androidx.annotation.Keep

@Keep
class LoginSendPhoneSmsResponse(
    val status: Boolean?,
    val message: String?,
    val data: LoginSendPhoneSmsData?
)

@Keep
data class LoginSendPhoneSmsData(
    val token: String?,
    val captcha: String?
)