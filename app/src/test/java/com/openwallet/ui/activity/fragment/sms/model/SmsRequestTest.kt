package com.openwallet.ui.activity.fragment.sms.model

import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SmsRequestTest : TestCase() {
    @Before
    public override fun setUp() {
        super.setUp()
    }
    @Test
    fun test_SmsRequest() {
        CaptchaType.SMS_VERIFY
        CaptchaType.MAIL_VERIFY

        CaptchaScenario.SIGN_IN
        CaptchaScenario.CHANGE_PASSWORD
        CaptchaScenario.FORGOT_USERNAME
        CaptchaScenario.REGISTER
        CaptchaScenario.RESET_PASSWORD

        val default = SmsRequest(null,null,null,null,null,CaptchaScenario.SIGN_IN,CaptchaType.SMS_VERIFY)
        val expectedData = SmsRequest("username","email","token","86","15911111111",CaptchaScenario.SIGN_IN,CaptchaType.SMS_VERIFY)
        val smsRequest = SmsRequest("username","email","token","86","15911111111",CaptchaScenario.SIGN_IN,CaptchaType.SMS_VERIFY)
        smsRequest.username
        smsRequest.emailAddress
        smsRequest.token
        smsRequest.phoneCountryCode
        smsRequest.phoneNumber
        smsRequest.captchaScenarioEnum
        smsRequest.captchaTypeEnum
        assertEquals(expectedData.token,smsRequest.token)
    }
}