package com.openwallet.ui.activity.fragment.wallet.model

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Keep
data class WalletDetailResponse(
    val data: WalletDetail
)

@Keep
@Parcelize
data class WalletDetail(
    val name: String = "",
    val ownedBy: String = "",
    val phoneNumber: String = "",
    val serialNumber: String? = "",
    val edition: String = "",

    val material: String = "",
    val fineness: String = "",
    val weight: String = "",
    val goldItemInformation: String = "",

    val imageList: List<String> = listOf(),

    val status: RedeemStatus = RedeemStatus.UNREDEEMED,
    @SerializedName("datetime")
    val dateTime: String = ""

) : Parcelable


enum class RedeemStatus(val value: Int, val displayName:String) {
    @SerializedName("0")
    UNREDEEMED(0, "Redeemable"),

    @SerializedName("1")
    PROCESSING(1, "Inflight" ),

    @SerializedName("2")
    REDEEMED(2,"Redeemed"),

    @SerializedName("3")
    EXPIRED(3, "Expired"),

}