package com.openwallet.model

import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CountryCodeModelTest : TestCase() {
    @Before
    public override fun setUp() {
        super.setUp()
    }
    @Test
    fun testGetCountry() {
        val countryCodeModel = CountryCodeModel("86","1")
        countryCodeModel.country
        countryCodeModel.code
    }
}