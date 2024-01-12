package com.openwallet.ui.activity.fragment.sms.model

import android.annotation.SuppressLint
import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Keep
@Parcelize
data class SmsVerificationResponseData(
    val captcha: String?,
    @Expose(serialize = false)
    val token: String?,
    @Expose(serialize = false)
    val refreshToken: String?,
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