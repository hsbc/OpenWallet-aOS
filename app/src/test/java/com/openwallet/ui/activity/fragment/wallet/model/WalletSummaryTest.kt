package com.openwallet.ui.activity.fragment.wallet.model

import junit.framework.TestCase
import org.junit.Test

class WalletSummaryTest : TestCase() {

    public override fun setUp() {
        super.setUp()
    }
    @Test
    fun test_WalletSummary() {
        val imageList = listOf<String>()
        val expectedData = WalletSummary("name","edition",imageList,"detailLinkUrl",1,"ownerBy",RedeemStatus.REDEEMED,""+System.currentTimeMillis())
        val walletSummary = WalletSummary("name","edition",imageList,"detailLinkUrl",1,"ownerBy",RedeemStatus.REDEEMED,""+System.currentTimeMillis())
        walletSummary.name
        walletSummary.status
        walletSummary.imgUrl
        walletSummary.edition
        walletSummary.detailLinkUrl
        walletSummary.nftId
        walletSummary.ownedBy
        walletSummary.datetime
        assertEquals(expectedData.name,walletSummary.name)
    }
}