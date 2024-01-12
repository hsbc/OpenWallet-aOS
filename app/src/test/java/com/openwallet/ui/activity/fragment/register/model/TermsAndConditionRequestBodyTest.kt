package com.openwallet.ui.activity.fragment.register.model

import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TermsAndConditionRequestBodyTest : TestCase() {
    @Before
    public override fun setUp() {
        super.setUp()
    }
    @Test
    fun test_TermsAndConditionRequestBody() {
        val expectedData = TermsAndConditionRequestBody("token")
        val termsAndConditionRequestBody = TermsAndConditionRequestBody("token")
        termsAndConditionRequestBody.token
        assertEquals(expectedData.token,termsAndConditionRequestBody.token)
    }
}