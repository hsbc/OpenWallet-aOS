package com.openwallet.ui.activity.fragment.login.model

import android.annotation.SuppressLint
import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import kotlinx.parcelize.Parcelize

@Keep
data class LoginVerifyPhoneSmsResponse(
    val status: Boolean?,
    val message: String?,
    val data: LoginVerifyData?
)

@SuppressLint("ParcelCreator")
@Keep
@Parcelize
data class LoginVerifyData(
    @Expose(serialize = false)
    val token: String?,
    @Expose
    val type: String?,
    @Expose
    val accountId: String?,
    @Expose
    val username: String?,
    @Expose
    val email: String?,
    @Expose
    val roles: List<String?>?
) : Parcelable