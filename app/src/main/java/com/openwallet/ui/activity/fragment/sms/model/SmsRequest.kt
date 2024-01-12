package com.openwallet.ui.activity.fragment.sms.model

import androidx.annotation.Keep

enum class CaptchaScenario {
    REGISTER, SIGN_IN, CHANGE_PASSWORD, RESET_PASSWORD, FORGOT_USERNAME
}

enum class CaptchaType {
    MAIL_VERIFY, SMS_VERIFY
}

@Keep
data class SmsRequest(
    val username: String? = null,
    val emailAddress: String? = null,
    val token: String? = null,
    val phoneCountryCode: String? = null,
    val phoneNumber: String? = null,
    val captchaScenarioEnum: CaptchaScenario?,
    val captchaTypeEnum: CaptchaType
)
