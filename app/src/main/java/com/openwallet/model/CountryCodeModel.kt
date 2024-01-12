package com.openwallet.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class CountryCodeModel(

    @SerializedName("COUNTRY")
    val country: String?,
    @SerializedName("CODE")
    val code: String?
)
