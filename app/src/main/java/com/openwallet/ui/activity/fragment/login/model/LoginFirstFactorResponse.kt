package com.openwallet.ui.activity.fragment.login.model

import androidx.annotation.Keep

@Keep
data class FirstFactorResponseData(
    val token: String?,
    val maskedEmail: String? = null,
    val maskedPhoneNumber: String? = null,
    val captcha: String?
)

enum class Color(val rgb: Int) {
    RED(0xFF0000),
    GREEN(0x00FF00),
    BLUE(0x0000FF)
}
enum class VerifyScenario ( val scenario: String) {
    CHANGE_PASSWORD("change-password"),
    LOGIN("login")
}

