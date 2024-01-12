package com.openwallet.ui.activity.fragment.login.model

import junit.framework.TestCase
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FirstFactorResponseDataTest : TestCase() {
    public override fun setUp() {
        super.setUp()
    }
    @Test
    fun test_firstFactorResponseData() {
        val expectedData = FirstFactorResponseData("token","","","111111")

        val firstFactorResponseData = FirstFactorResponseData("","","","111111")
        firstFactorResponseData.captcha
        firstFactorResponseData.maskedEmail
        firstFactorResponseData.maskedPhoneNumber
        firstFactorResponseData.token

        Color.BLUE
        Color.GREEN
        Color.RED

        VerifyScenario.CHANGE_PASSWORD
        VerifyScenario.LOGIN

        assertEquals(expectedData.captcha,expectedData.captcha)
    }
}