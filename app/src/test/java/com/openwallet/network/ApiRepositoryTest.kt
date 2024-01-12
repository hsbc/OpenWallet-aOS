package com.openwallet.network

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.gson.JsonObject
import com.openwallet.base.BaseViewModel
import com.openwallet.model.token.RefreshTokenRequestBody
import com.openwallet.model.token.RefreshTokenResponse
import com.openwallet.ui.activity.fragment.home.HomeViewModel
import com.openwallet.ui.activity.fragment.login.model.*
import com.openwallet.ui.activity.fragment.logout.model.LogoutResponse
import com.openwallet.ui.activity.fragment.profile.TermsAndConditionsInfo
import com.openwallet.ui.activity.fragment.profile.changeavater.model.ProfileInfo
import com.openwallet.ui.activity.fragment.profile.changeavater.model.UpdateAvatarRequest
import com.openwallet.ui.activity.fragment.profile.changepassword.model.ChangePasswordResponse
import com.openwallet.ui.activity.fragment.profile.faq.model.FaqData
import com.openwallet.ui.activity.fragment.profile.helpcenter.FeedbackRequestBody
import com.openwallet.ui.activity.fragment.profile.notification.model.NotificationResponse
import com.openwallet.ui.activity.fragment.profile.notification.model.NotificationUpdateStatusResponse
import com.openwallet.ui.activity.fragment.redeem.model.BankInfoRequestBody
import com.openwallet.ui.activity.fragment.redeem.model.BankInfoResponse
import com.openwallet.ui.activity.fragment.redeem.model.RedeemResponse
import com.openwallet.ui.activity.fragment.redeem.model.RedeemResult
import com.openwallet.ui.activity.fragment.register.model.*
import com.openwallet.ui.activity.fragment.resetpassword.model.ResetPasswordResponseData
import com.openwallet.ui.activity.fragment.sms.model.*
import com.openwallet.ui.activity.fragment.wallet.model.*
import com.openwallet.ui.activity.rule.MainCoroutineRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Call
import retrofit2.Retrofit
@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class ApiRepositoryTest : TestCase() {
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule() // 解决  viewModelScope.launch
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private val testCoroutineDispatcher = mainCoroutineRule.testDispatcher

    val retrofit = mockk<Retrofit>()
    val ipfsRetrofit = mockk<Retrofit>()
    val apiService = mockk<ApiService>()

    val apiRepository = ApiRepository(retrofit,ipfsRetrofit)
    val baseViewModel = BaseViewModel()


    @Before
    public override fun setUp() {
        super.setUp()
    }
    @Test
    fun testGetWalletList() = runBlocking {
        val data = mockk<List<WalletSummary>>()
        val expectedResul = NetworkResponse<List<WalletSummary>>("",true,data)
//        Mockito.`when`(retrofit.create(ApiService::class.java))
//            .thenReturn(apiService)
//        Mockito.`when`(ipfsRetrofitMock.create(ApiService::class.java))
//            .thenReturn(apiService)
//        val apiRepository = ApiRepository(retrofitMock,ipfsRetrofitMock)
//
//        given(apiRepository.getWalletList()).willReturn(list)
        coEvery { apiService.getWalletList() } answers { expectedResul }
        coEvery { apiRepository.getWalletList() } answers { expectedResul }

        //coVerify { apiRepository.getWalletList() }
        val actualResult = apiService.getWalletList()
        assertEquals(expectedResul,actualResult)
    }
    @Test
    fun testGetWalletDetail() = runBlocking {
        val path = "path"
        val imageList = listOf<String>()
        val walletDetail = WalletDetail("name","ownedBy","15911111111","911","edition",
            "material","fineness","50","goldItemInformation",imageList , RedeemStatus.UNREDEEMED,""+System.currentTimeMillis())
        val expectedResul = WalletDetailResponse(data = walletDetail)
        coEvery { apiService.getWalletDetail(path) } answers { expectedResul }
        coEvery { apiRepository.getWalletDetail(path) } answers { expectedResul }

        val actualResult = apiService.getWalletDetail(path)
        assertEquals(expectedResul,actualResult)
    }
    @Test
    fun testRequestRedeem() = runBlocking {
        val redeemResult = RedeemResult("1",""+System.currentTimeMillis(),""+System.currentTimeMillis())

        val expectedResul = RedeemResponse(redeemResult)
        val bankInfoRequestBody = BankInfoRequestBody(1)
        coEvery { apiService.requestRedeem(bankInfoRequestBody) } answers { expectedResul }
        coEvery { apiRepository.requestRedeem(1) } answers { expectedResul }

        val actualResult = apiService.requestRedeem(bankInfoRequestBody)
        assertEquals(expectedResul,actualResult)

    }
    @Test
    fun testGetBankInfo() = runBlocking {
        val expectedResul = BankInfoResponse()
        coEvery { apiService.getBankInfo() } answers { expectedResul }
        coEvery { apiRepository.getBankInfo() } answers { expectedResul }

        val actualResult = apiService.getBankInfo()
        assertEquals(expectedResul,actualResult)
    }
    @Test
    fun testHasExpiredNFT() = runBlocking {
        val expectedResul = NetworkResponse<Boolean>("",false,false)
        coEvery { apiService.hasExpiredNFT() } answers { expectedResul }
        coEvery { apiRepository.hasExpiredNFT() } answers { expectedResul }

        val actualResult = apiService.hasExpiredNFT()
        assertEquals(expectedResul,actualResult)
    }
    @Test
    fun testRegisterUserName() = runBlocking {
        val request = JsonObject()
        val data = RegisterUserNameResponseData("token","111111")

        val expectedResul = NetworkResponse<RegisterUserNameResponseData>("",false,data)
        coEvery { apiService.registerUsername(request) } answers { expectedResul }
        coEvery { apiRepository.registerUserName(request) } answers { expectedResul }

        val actualResult = apiService.registerUsername(request)
        assertEquals(expectedResul,actualResult)
    }
    @Test
    fun testRegisterPassword() = runBlocking {
        val request = JsonObject()
        val data = RegisterUserPasswordResponseData("token","111111")
        val expectedResul = NetworkResponse<RegisterUserPasswordResponseData>("",false,data)

        coEvery { apiService.registerPassword(request) } answers { expectedResul }
        coEvery { apiRepository.registerPassword(request) } answers { expectedResul }

        val actualResult = apiService.registerPassword(request)
        assertEquals(expectedResul,actualResult)
    }
    @Test
    fun testRegisterSendEmailSms() = runBlocking {
        val request = JsonObject()
        val registerVerifyEmailSmsData = RegisterVerifyEmailSmsData("token","111111")
        val expectedResul = RegisterSendEmailSmsResponse(false,"message",registerVerifyEmailSmsData)
        coEvery { apiService.sendEmailSms(request) } answers { expectedResul }
        coEvery { apiRepository.registerSendEmailSms(request) } answers { expectedResul }

        val actualResult = apiService.sendEmailSms(request)
        assertEquals(expectedResul,actualResult)
    }
    @Test
    fun testRegisterSendPhoneSms() = runBlocking {
        val request = JsonObject()
        val registerVerifyEmailSmsData = RegisterVerifyEmailSmsData("token","111111")

        val expectedResul = RegisterSendPhoneSmsResponse(false,"message",registerVerifyEmailSmsData)

        coEvery { apiService.sendPhoneSms(request) } answers { expectedResul }
        coEvery { apiRepository.registerSendPhoneSms(request) } answers { expectedResul }

        val actualResult = apiService.sendPhoneSms(request)
        assertEquals(expectedResul,actualResult)
    }
    @Test
    fun testVerifyEmailSms() = runBlocking {
        val request = JsonObject()

        val registerVerifyEmailSmsData = RegisterVerifyEmailSmsData("token","111111")
        val expectedResul = RegisterVerifyEmailSmsResponse(false,"message",registerVerifyEmailSmsData)

        coEvery { apiService.verifyEmailSms(request) } answers { expectedResul }
        coEvery { apiRepository.verifyEmailSms(request) } answers { expectedResul }

        val actualResult = apiService.verifyEmailSms(request)
        assertEquals(expectedResul,actualResult)
    }
    @Test
    fun testVerifyPhoneSms() = runBlocking {
        val request = JsonObject()

        val registerVerifyPhoneSmsData = RegisterVerifyPhoneSmsData("token","111111")

        val expectedResul = RegisterVerifyPhoneSmsResponse(false,"message",registerVerifyPhoneSmsData)

        coEvery { apiService.verifyPhoneSms(request) } answers { expectedResul }
        coEvery { apiRepository.verifyPhoneSms(request) } answers { expectedResul }

        val actualResult = apiService.verifyPhoneSms(request)
        assertEquals(expectedResul,actualResult)
    }
    @Test
    fun testLoginWithFirstFactor() = runBlocking {
        val request = JsonObject()
        val path = "path"

        val data = FirstFactorResponseData("","","","111111")

        val expectedResul = NetworkResponse<FirstFactorResponseData>("",false,data)
        coEvery { apiService.verifyFirstFactor(request,path) } answers { expectedResul }
        coEvery { apiRepository.loginWithFirstFactor(request,path) } answers { expectedResul }

        val actualResult = apiService.verifyFirstFactor(request,path)
        assertEquals(expectedResul,actualResult)
    }
    @Test
    fun testSendLoginEmailSms() = runBlocking {
        val request = JsonObject()
        val loginSendEmailSmsData = LoginSendEmailSmsData("token","111111")
        val expectedResul = LoginSendEmailSmsResponse(false,"message",loginSendEmailSmsData)

        coEvery { apiService.sendLoginEmailSms(request) } answers { expectedResul }
        coEvery { apiRepository.sendLoginEmailSms(request) } answers { expectedResul }

        val actualResult = apiService.sendLoginEmailSms(request)
        assertEquals(expectedResul,actualResult)
    }
    @Test
    fun testVerifyLoginEmailSms() = runBlocking {
        val request = JsonObject()
        val roles = listOf<String>()

        val loginVerifyData = LoginVerifyData("token","type","1","username","email",roles)
        val expectedResul = LoginVerifyEmailSmsResponse(false,"message",loginVerifyData)
        coEvery { apiService.verifyLoginEmailSms(request) } answers { expectedResul }
        coEvery { apiRepository.verifyLoginEmailSms(request) } answers { expectedResul }

        val actualResult = apiService.verifyLoginEmailSms(request)
        assertEquals(expectedResul,actualResult)
    }
    @Test
    fun testSendLoginPhoneSms() = runBlocking {
        val request = JsonObject()
        val loginSendPhoneSmsData = LoginSendPhoneSmsData("token","111111")
        val expectedResul = LoginSendPhoneSmsResponse(false,"message",loginSendPhoneSmsData)

        coEvery { apiService.sendLoginPhoneSms(request) } answers { expectedResul }
        coEvery { apiRepository.sendLoginPhoneSms(request) } answers { expectedResul }

        val actualResult = apiService.sendLoginPhoneSms(request)
        assertEquals(expectedResul,actualResult)
    }
    @Test
    fun testVerifyLoginPhoneSms() = runBlocking {
        val request = JsonObject()
        val roles = listOf<String>()

        val loginVerifyData = LoginVerifyData("token","type","1","username","email",roles)
        val expectedResul = LoginVerifyPhoneSmsResponse(false,"message",loginVerifyData)

        coEvery { apiService.verifyLoginPhoneSms(request) } answers { expectedResul }
        coEvery { apiRepository.verifyLoginPhoneSms(request) } answers { expectedResul }

        val actualResult = apiService.verifyLoginPhoneSms(request)
        assertEquals(expectedResul,actualResult)
    }
    @Test
    fun testGetFaqList() = runBlocking {
        val faqDataList = mockk<MutableList<FaqData>>()
        val expectedResul = NetworkResponse<MutableList<FaqData>>("",false,faqDataList)
        coEvery { apiService.getFaqList() } answers { expectedResul }
        coEvery { apiRepository.getFaqList() } answers { expectedResul }

        val actualResult = apiService.getFaqList()
        assertEquals(expectedResul,actualResult)
    }
    @Test
    fun testGetProfile() = runBlocking {
        val profileInfo = ProfileInfo("username","email","1","15911111111","avatar",false)

        val expectedResul = NetworkResponse<ProfileInfo>("",false,profileInfo)
        coEvery { apiService.getProfile() } answers { expectedResul }
        coEvery { apiRepository.getProfile() } answers { expectedResul }

        val actualResult = apiService.getProfile()
        assertEquals(expectedResul,actualResult)
    }
    @Test
    fun testUpdateProfile() = runBlocking {
        val updateAvatarRequest = UpdateAvatarRequest("avatar")
        val profileInfo = ProfileInfo("username","email","1","15911111111","avatar",false)

        val expectedResul = NetworkResponse<ProfileInfo>("",false,profileInfo)
        coEvery { apiService.updateAvatar(updateAvatarRequest) } answers { expectedResul }
        coEvery { apiRepository.updateProfile(updateAvatarRequest) } answers { expectedResul }

        val actualResult = apiService.updateAvatar(updateAvatarRequest)
        assertEquals(expectedResul,actualResult)
    }
    @Test
    fun testSendFeedback() = runBlocking {
        val feedbackRequestBody = FeedbackRequestBody("content");

        val expectedResul = NetworkResponse<String>("",false,"")
        coEvery { apiService.sendFeedBack(feedbackRequestBody) } answers { expectedResul }
        coEvery { apiRepository.sendFeedback(feedbackRequestBody) } answers { expectedResul }

        val actualResult = apiService.sendFeedBack(feedbackRequestBody)
        assertEquals(expectedResul,actualResult)
    }
    @Test
    fun testRegister() = runBlocking {
        val request = JsonObject()
        val data = RegisterResponseData("data")

        val expectedResul = NetworkResponse<RegisterResponseData>("",false,data)

        coEvery { apiService.register(request) } answers { expectedResul }
        coEvery { apiRepository.register(request) } answers { expectedResul }

        val actualResult = apiService.register(request)
        assertEquals(expectedResul,actualResult)
    }
    @Test
    fun testGetCountryCodeInfo() = runBlocking {
        val data = ArrayList<CountryCodeInfo>()

        val expectedResul = CountryCodeListResponse(false,"message",data)

        coEvery { apiService.getCountryCodeList() } answers { expectedResul }
        coEvery { apiRepository.getCountryCodeInfo() } answers { expectedResul }

        val actualResult = apiService.getCountryCodeList()
        assertEquals(expectedResul,actualResult)
    }
    @Test
    fun testSendSms() = runBlocking {
        val smsRequest = SmsRequest("username","email","token","86","15911111111",CaptchaScenario.SIGN_IN,CaptchaType.SMS_VERIFY)
        val data = SmsResponseData("token","111111")

        val expectedResul = NetworkResponse<SmsResponseData>("",false,data)
        coEvery { apiService.sendSms(smsRequest) } answers { expectedResul }
        coEvery { apiRepository.sendSms(smsRequest) } answers { expectedResul }

        val actualResult = apiService.sendSms(smsRequest)
        assertEquals(expectedResul,actualResult)
    }
    @Test
    fun testVerifySms() = runBlocking {
        val roles = listOf<String>()

        val smsVerificationRequest = SmsVerificationRequest("email","username","111111","token",
            CaptchaScenario.RESET_PASSWORD,
            CaptchaType.SMS_VERIFY)
        val data = SmsVerificationResponseData("111111","token","refreshToken","type","1","username","email",roles)

        val expectedResul = NetworkResponse<SmsVerificationResponseData>("",false,data)
        coEvery { apiService.verifySms(smsVerificationRequest) } answers { expectedResul }
        coEvery { apiRepository.verifySms(smsVerificationRequest) } answers { expectedResul }

        val actualResult = apiService.verifySms(smsVerificationRequest)
        assertEquals(expectedResul,actualResult)
    }
    @Test
    fun testResetPassword() = runBlocking {
        val request = JsonObject()
        val data = ResetPasswordResponseData("data")
        val expectedResul = NetworkResponse<ResetPasswordResponseData>("",false,data)
        coEvery { apiService.resetPassword(request) } answers { expectedResul }
        coEvery { apiRepository.resetPassword(request) } answers { expectedResul }

        val actualResult = apiService.resetPassword(request)
        assertEquals(expectedResul,actualResult)
    }
    @Test
    fun testChangePassword() = runBlocking {
        val request = JsonObject()
        val data = FirstFactorResponseData("","","","111111")

        val expectedResul = NetworkResponse<FirstFactorResponseData>("",false,data)
        coEvery { apiService.verifyFirstFactor(request,"") } answers { expectedResul }
        coEvery { apiRepository.loginWithFirstFactor(request,"") } answers { expectedResul }

        val actualResult = apiService.verifyFirstFactor(request,"")
        assertEquals(expectedResul,actualResult)
    }
    @Test
    fun testChangePasswordWithFirstFactor() = runBlocking {
        val request = JsonObject()
        val firstFactorResponseData = FirstFactorResponseData("","","","111111")
        val expectedResul = NetworkResponse<FirstFactorResponseData>("",false,firstFactorResponseData)
        coEvery { apiService.verifyFirstFactor(request,"") } answers { expectedResul }
        coEvery { apiRepository.changePasswordWithFirstFactor(request,"") } answers { expectedResul }

        val actualResult = apiService.verifyFirstFactor(request,"")
        assertEquals(expectedResul,actualResult)
    }
    @Test
    fun testGetNotification() = runBlocking {
        var list = ArrayList<NotificationResponse.NotificationResponseItem>()

        val expectedResul = NotificationResponse("1","message",list)

        coEvery { apiService.getNotification() } answers { expectedResul }
        coEvery { apiRepository.getNotification() } answers { expectedResul }

        val actualResult = apiService.getNotification()
        assertEquals(expectedResul,actualResult)
    }
    @Test
    fun testUpdateNotificationStatus() = runBlocking {
        val expectedResul = NotificationUpdateStatusResponse("1","data","message")
        coEvery { apiService.updateNotificationStatus(1) } answers { expectedResul }
        //coEvery { apiRepository.updateNotificationStatus(1) } answers { expectedResul }

        val actualResult = apiService.updateNotificationStatus(1)
        assertEquals(expectedResul,actualResult)
    }
    @Test
    fun testGetTermAndConditions() = runBlocking {
        val requestBody = TermsAndConditionRequestBody("token")
        val termsAndConditionsInfo = TermsAndConditionsInfo("content")
        val expectedResul = NetworkResponse<TermsAndConditionsInfo>("",false,termsAndConditionsInfo)
        coEvery { apiService.getTermsAndConditions(requestBody) } answers { expectedResul }
        coEvery { apiRepository.getTermAndConditions(requestBody) } answers { expectedResul }

        val actualResult = apiService.getTermsAndConditions(requestBody)
        assertEquals(expectedResul,actualResult)
    }
    @Test
    fun testLogout() = runBlocking {
        val data = LogoutResponse(false,"message","data")

        val expectedResul = NetworkResponse<LogoutResponse>("",false,data)
        coEvery { apiService.logout() } answers { expectedResul }
        coEvery { apiRepository.logout() } answers { expectedResul }

        val actualResult = apiService.logout()
        assertEquals(expectedResul,actualResult)
    }
    @Test
    fun testRefreshToken() {
        val request = JsonObject()

        val refreshTokenRequestBody = RefreshTokenRequestBody("refreshToken")

        val data = RefreshTokenResponse("token")

        //val registerUserPasswordResponseData = Call<NetworkResponse<RefreshTokenResponse>>("",false,data)
        val expectedResul = mockk<Call<NetworkResponse<RefreshTokenResponse>>>()

        coEvery { apiService.refreshToken(refreshTokenRequestBody) } answers { expectedResul }
        coEvery { apiRepository.refreshToken(refreshTokenRequestBody) } answers { expectedResul }

        val actualResult = apiService.refreshToken(refreshTokenRequestBody)
        assertEquals(expectedResul,actualResult)
    }
    @Test
    fun testDeleteUserProfile() = runBlocking {
        val expectedResul = NetworkResponse<String>("",false,"")
        coEvery { apiService.deleteProfile() } answers { expectedResul }
        coEvery { apiRepository.deleteUserProfile() } answers { expectedResul }

        val actualResult = apiService.deleteProfile()
        assertEquals(expectedResul,actualResult)
    }
}