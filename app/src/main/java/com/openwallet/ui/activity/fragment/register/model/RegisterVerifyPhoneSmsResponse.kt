package com.openwallet.ui.activity.fragment.register.model

import androidx.annotation.Keep

@Keep
data class RegisterVerifyPhoneSmsResponse(
    val status: Boolean?,
    val message: String?,
    val data: RegisterVerifyPhoneSmsData?
)

@Keep
data class RegisterVerifyPhoneSmsData(
    val token: String?,
    val captcha: String?
)