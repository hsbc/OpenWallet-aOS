package com.openwallet.ui.activity.fragment.redeem.model

import com.openwallet.ui.activity.fragment.profile.TermsAndConditionsInfo
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class BankInfoRequestBodyTest : TestCase() {
    @Before
    public override fun setUp() {
        super.setUp()
    }
    @Test
    fun test_BankInfoRequestBody() {
        val expectedData = BankInfoRequestBody(1)
        val bankInfoRequestBody = BankInfoRequestBody(1)
        assertEquals(expectedData.nftId,bankInfoRequestBody.nftId)
    }
}