package com.openwallet.ui.activity.fragment.sms.model

import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SmsVerificationRequestTest : TestCase() {
    @Before
    public override fun setUp() {
        super.setUp()
    }
    @Test
    fun test_SmsVerificationRequest() {
        val expectedData = SmsVerificationRequest("email","username","111111","token",CaptchaScenario.RESET_PASSWORD,CaptchaType.SMS_VERIFY)
        val smsVerificationRequest = SmsVerificationRequest("email","username","111111","token",CaptchaScenario.RESET_PASSWORD,CaptchaType.SMS_VERIFY)
        smsVerificationRequest.captcha
        smsVerificationRequest.emailAddress
        smsVerificationRequest.username
        smsVerificationRequest.token
        smsVerificationRequest.captchaScenarioEnum
        smsVerificationRequest.captchaTypeEnum
        assertEquals(expectedData.token,smsVerificationRequest.token)
    }
}