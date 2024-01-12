package com.openwallet.model

import androidx.annotation.Keep

@Keep
data class UserInput(
    var email: String? = null,
    var name: String? = null,
    var password: String? = null,
    var confirmedPassword: String? = null,
    var phone: String? = null,
    var countryCode: String? = "+971",
    var loginName: String = "",
    var loginPassword: String ="",
    var isRememberMeChecked: Boolean = false
) {
    fun reset() {
        email = null
        name = null
        password = null
        confirmedPassword = null
        phone = null
        countryCode = "+971"
        loginName =""
        loginPassword = ""
        isRememberMeChecked = false
    }
}
