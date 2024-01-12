package com.openwallet.ui.activity.fragment.sms.model

import androidx.annotation.Keep


@Keep
data class SmsResponseData(
    val token: String?,
    val captcha: String?
)
