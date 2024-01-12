package com.openwallet.ui.activity.fragment.register.model

import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CountryCodeListResponseTest : TestCase() {
    @Before
    public override fun setUp() {
        super.setUp()
    }
    @Test
    fun test_CountryCodeListResponse() {
        val data = ArrayList<CountryCodeInfo>()
        val expectedData = CountryCodeInfo("1",86)
        val countryCodeInfo = CountryCodeInfo("1",86)
        countryCodeInfo.country
        countryCodeInfo.code
        assertEquals(expectedData.code,countryCodeInfo.code)

        data.add(countryCodeInfo)
        val expectedResponse = CountryCodeListResponse(false,"message",data)
        val countryCodeListResponse = CountryCodeListResponse(false,"message",data)
        countryCodeListResponse.message
        countryCodeListResponse.status
        countryCodeListResponse.data
        assertEquals(expectedResponse.data,countryCodeListResponse.data)
    }
}