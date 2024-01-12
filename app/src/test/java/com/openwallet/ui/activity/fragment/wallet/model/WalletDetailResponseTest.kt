package com.openwallet.ui.activity.fragment.wallet.model

import com.google.gson.annotations.SerializedName
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class WalletDetailResponseTest : TestCase() {
    @Before
    public override fun setUp() {
        super.setUp()
    }
    @Test
    fun test_WalletDetailResponse() {
        val imageList = listOf<String>()

        val expectedData = WalletDetail("name","ownedBy","15911111111","911","edition",
            "material","fineness","50","goldItemInformation",imageList ,RedeemStatus.UNREDEEMED,""+System.currentTimeMillis())
        val walletDetail = WalletDetail("name","ownedBy","15911111111","911","edition",
            "material","fineness","50","goldItemInformation",imageList ,RedeemStatus.UNREDEEMED,""+System.currentTimeMillis())
        walletDetail.name
        walletDetail.ownedBy
        walletDetail.phoneNumber
        walletDetail.serialNumber
        walletDetail.edition
        walletDetail.material
        walletDetail.fineness
        walletDetail.weight
        walletDetail.goldItemInformation
        walletDetail.imageList
        walletDetail.status
        walletDetail.dateTime
        assertEquals(expectedData.phoneNumber,walletDetail.phoneNumber)
        val walletDetailResponse = WalletDetailResponse(walletDetail)
        walletDetailResponse.data

        RedeemStatus.UNREDEEMED
        RedeemStatus.EXPIRED
        RedeemStatus.PROCESSING
        RedeemStatus.REDEEMED

    }
}