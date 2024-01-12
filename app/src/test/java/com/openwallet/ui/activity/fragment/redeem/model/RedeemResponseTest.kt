package com.openwallet.ui.activity.fragment.redeem.model

import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RedeemResponseTest : TestCase() {
    @Before
    public override fun setUp() {
        super.setUp()
    }
    @Test
    fun test_RedeemResponse() {
        val expectedData = RedeemResult("1",""+System.currentTimeMillis(),""+System.currentTimeMillis())
        val redeemResult = RedeemResult("1",""+System.currentTimeMillis(),""+System.currentTimeMillis())
        redeemResult.createTime
        redeemResult.id
        redeemResult.updateTime
        assertEquals(expectedData.id,redeemResult.id)

        val default = RedeemResponse()
        val expectedResponse = RedeemResponse(redeemResult)
        val redeemResponse = RedeemResponse(redeemResult)
        redeemResponse.data
        assertEquals(expectedResponse.data,redeemResponse.data)
    }
}