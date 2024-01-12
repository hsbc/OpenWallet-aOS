package com.openwallet.ui.activity.fragment.redeem.model

import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class BankInfoResponseTest : TestCase() {
    @Before
    public override fun setUp() {
        super.setUp()
    }
    @Test
    fun test_BankInfoResponse() {
        val expectedData = BankInfo("","86","")
        val bankInfo = BankInfo("","86","")
        bankInfo.legalName
        bankInfo.phoneCountryCode
        bankInfo.phoneNumber
        assertEquals(expectedData.phoneCountryCode,bankInfo.phoneCountryCode)


        val bankInfoResponse = BankInfoResponse()
        bankInfoResponse.data
    }
}