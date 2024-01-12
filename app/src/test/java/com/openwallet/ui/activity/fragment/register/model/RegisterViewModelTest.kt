package com.openwallet.ui.activity.fragment.register.model

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonObject
import com.openwallet.app.AppViewModel
import com.openwallet.app.OpenWalletApplication
import com.openwallet.base.BaseViewModel
import com.openwallet.ext.request
import com.openwallet.manager.CacheManager
import com.openwallet.network.ApiRepository
import com.openwallet.network.ApiService
import com.openwallet.network.exception.ExceptionEngineImpl
import com.openwallet.network.state.ResultState
import com.openwallet.ui.activity.fragment.logout.model.LogoutResponse
import com.openwallet.ui.activity.fragment.redeem.model.RedeemViewModel
import com.openwallet.ui.activity.fragment.resetpassword.model.ResetPasswordResponseData
import com.openwallet.ui.activity.fragment.wallet.model.NetworkResponse
import com.openwallet.ui.activity.rule.MainCoroutineRule
import com.openwallet.util.CommonUtils
import com.kunminx.architecture.ui.callback.UnPeekLiveData
import io.mockk.*
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Retrofit

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class RegisterViewModelTest : TestCase() {
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule() // 解决  viewModelScope.launch
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testCoroutineDispatcher = mainCoroutineRule.testDispatcher

    val retrofit = mockk<Retrofit>()
    val ipfsRetrofit = mockk<Retrofit>()
    val apiService = mockk<ApiService>()

    val apiRepository = mockk<ApiRepository>()
    val appViewModel = mockk<AppViewModel>()

    val viewModel = RegisterViewModel(apiRepository)

    var secretToken = UnPeekLiveData.Builder<String>().setAllowNullValue(true).create()

    @Before
    public override fun setUp() {
        super.setUp()
        MockKAnnotations.init(this)
    }

    public override fun tearDown() {

    }
    @Test
    fun testRegisterUserName() = runBlocking {
        val username = "zhangbo3"

        val request = JsonObject()
        request.addProperty("username", username)

        viewModel.exceptionEngine = ExceptionEngineImpl()

        val data = RegisterUserNameResponseData("token","111111")
        var response = NetworkResponse<RegisterUserNameResponseData>("message",true,data)
        val resultState = ResultState.onAppSuccess(response)

        var expectedResult = MutableLiveData<ResultState<NetworkResponse<RegisterUserNameResponseData>>>()
        expectedResult.value = resultState

        every { retrofit.create(ApiService::class.java) } returns apiService

        coEvery { apiRepository.registerUserName(request) } coAnswers { response }

        val actualResult = viewModel.registerUserName(username)

        //verify { viewModel.registerUserName(username) }

        assertEquals(expectedResult.value,actualResult.value)
    }
    @Test
    fun testRegisterPassword() = runBlocking {

        val username = "zhangbo3"
        val password = "Aa123456&"

        viewModel.exceptionEngine = ExceptionEngineImpl()

        mockkObject(OpenWalletApplication)
        every { OpenWalletApplication.Companion.appViewModel } returns appViewModel
        every { OpenWalletApplication.appViewModel.loginToken} returns "token"
        every { OpenWalletApplication.appViewModel.secretToken } returns secretToken
        every { OpenWalletApplication.appViewModel.secretToken.value} answers { "token" }

        val request = JsonObject()
        request.addProperty("username", username)
        request.addProperty("password", password)
        request.addProperty("token", OpenWalletApplication.appViewModel.secretToken.value)

        val data = RegisterUserPasswordResponseData("token","111111")
        var response = NetworkResponse<RegisterUserPasswordResponseData>("message",true,data)
        val resultState = ResultState.onAppSuccess(response)

        var expectedResult = MutableLiveData<ResultState<NetworkResponse<RegisterUserPasswordResponseData>>>()
        expectedResult.value = resultState

        every { retrofit.create(ApiService::class.java) } returns apiService

        coEvery { apiRepository.registerPassword(request) } coAnswers { response }

//        coEvery { viewModel.request({ response }, expectedResult).runCatching {  }
//            .onSuccess {
//                val request = JsonObject()
//                request.addProperty("username", username)
//                request.addProperty("password", password)
//                request.addProperty("token", OpenWalletApplication.appViewModel.secretToken.value)
//                apiRepository.registerPassword(request).status
//            }
//        } coAnswers { mockk<Result<Unit>>() }

        //coEvery { viewModel.registerPassword(username, password) } answers { expectedResult }

        val actualResult = viewModel.registerPassword(username, password)

        //verify { viewModel.registerPassword(username, password) }

        assertEquals(expectedResult.value,actualResult.value)
    }
    @Test
    fun testSendEmailSms() {
        val username = "testing user"
        val email = "testing email"

        mockkObject(OpenWalletApplication)
        every { OpenWalletApplication.Companion.appViewModel } answers { appViewModel }
        every { appViewModel.loginToken} answers { "token" }
        every { appViewModel.secretToken.value} answers { "token" }

        val request = JsonObject()
        request.addProperty("username", username)
        request.addProperty("emailAddress", email)
        request.addProperty("captchaScenarioEnum", "REGISTER")
        request.addProperty("captchaTypeEnum", "MAIL_VERIFY")
        request.addProperty("token", OpenWalletApplication.appViewModel.secretToken.value)

        val registerVerifyEmailSmsData = RegisterVerifyEmailSmsData("token","111111")
        val response = RegisterSendEmailSmsResponse(false,"message",registerVerifyEmailSmsData)
        val resultState = ResultState.onAppSuccess(response)

        var expectedResult = MutableLiveData<ResultState<RegisterSendEmailSmsResponse>>()
        expectedResult.value = resultState

        //coEvery { viewModel.sendEmailSms(username, email) } answers { expectedResult }

        every { retrofit.create(ApiService::class.java) } returns apiService

        coEvery { apiRepository.registerSendEmailSms(request) } coAnswers { response }

        val actualResult = viewModel.sendEmailSms(username, email)

        //verify { viewModel.sendEmailSms(username, email) }

        assertEquals(expectedResult.value,actualResult.value)
    }
    @Test
    fun testSendPhoneSms() = runBlocking {
        val username = "zhangbo3"
        val phone = "222222"
        val countryCode = "1"

        mockkObject(OpenWalletApplication)
        every { OpenWalletApplication.Companion.appViewModel } answers { appViewModel }
        every { appViewModel.loginToken} answers { "token" }
        every { appViewModel.secretToken.value} answers { "token" }

        val request = JsonObject()
        request.addProperty("username", username)
        request.addProperty("phoneNumber", phone)
        request.addProperty("phoneCountryCode", countryCode)
        request.addProperty("captchaScenarioEnum", "REGISTER")
        request.addProperty("captchaTypeEnum", "SMS_VERIFY")
        request.addProperty("token", OpenWalletApplication.appViewModel.secretToken.value)


        val registerVerifyEmailSmsData = RegisterVerifyEmailSmsData("token","111111")
        val response = RegisterSendPhoneSmsResponse(false,"message",registerVerifyEmailSmsData)
        val resultState = ResultState.onAppSuccess(response)

        var expectedResult = MutableLiveData<ResultState<RegisterSendPhoneSmsResponse>>()
        expectedResult.value = resultState

        every { retrofit.create(ApiService::class.java) } returns apiService

        coEvery { apiRepository.registerSendPhoneSms(request) } coAnswers { response }

        //coEvery { viewModel.sendPhoneSms(username, phone, countryCode) } answers { expectedResult }

        val actualResult = viewModel.sendPhoneSms(username, phone, countryCode)

        //verify { viewModel.sendPhoneSms(username, phone, countryCode) }

        assertEquals(expectedResult.value,actualResult.value)
    }
    @Test
    fun testVerifyEmailSms() = runBlocking {
        val username = "zhangbo3"
        val captcha = "222222"

        mockkObject(OpenWalletApplication)
        every { OpenWalletApplication.Companion.appViewModel } answers { appViewModel }
        every { appViewModel.loginToken} answers { "token" }
        every { appViewModel.secretToken.value} answers { "token" }


        val request = JsonObject()
        request.addProperty("username", username)
        request.addProperty("captcha", captcha)
        request.addProperty("captchaScenarioEnum", "REGISTER")
        request.addProperty("captchaTypeEnum", "MAIL_VERIFY")
        request.addProperty("token", OpenWalletApplication.appViewModel.secretToken.value)


        val registerVerifyEmailSmsData = RegisterVerifyEmailSmsData("token","111111")
        val response = RegisterVerifyEmailSmsResponse(false,"message",registerVerifyEmailSmsData)
        val resultState = ResultState.onAppSuccess(response)

        var expectedResult = MutableLiveData<ResultState<RegisterVerifyEmailSmsResponse>>()
        expectedResult.value = resultState

        every { retrofit.create(ApiService::class.java) } returns apiService

        coEvery { apiRepository.verifyEmailSms(request) } coAnswers { response }

        //coEvery { viewModel.verifyEmailSms(username, captcha) } answers { expectedResult }

        val actualResult = viewModel.verifyEmailSms(username, captcha)

        //verify { viewModel.verifyEmailSms(username, captcha) }

        assertEquals(expectedResult.value,actualResult.value)
    }
    @Test
    fun testVerifyPhoneSms() = runBlocking {
        val username = "zhangbo3"
        val captcha = "222222"

        mockkObject(OpenWalletApplication)
        every { OpenWalletApplication.Companion.appViewModel } answers { appViewModel }
        every { OpenWalletApplication.appViewModel.loginToken} answers { "token" }
        every { OpenWalletApplication.appViewModel.secretToken.value} answers { "token" }


        val request = JsonObject()
        request.addProperty("username", username)
        request.addProperty("captcha", captcha)
        request.addProperty("captchaScenarioEnum", "REGISTER")
        request.addProperty("captchaTypeEnum", "SMS_VERIFY")
        request.addProperty("token", OpenWalletApplication.appViewModel.secretToken.value)


        val registerVerifyPhoneSmsData = RegisterVerifyPhoneSmsData("token","111111")
        val response = RegisterVerifyPhoneSmsResponse(false,"message",registerVerifyPhoneSmsData)
        val resultState = ResultState.onAppSuccess(response)

        var expectedResult = MutableLiveData<ResultState<RegisterVerifyPhoneSmsResponse>>()
        expectedResult.value = resultState

        every { retrofit.create(ApiService::class.java) } returns apiService

        coEvery { apiRepository.verifyPhoneSms(request) } coAnswers { response }

        //coEvery { viewModel.verifyPhoneSms(username, captcha) } returns expectedResult

        val actualResult = viewModel.verifyPhoneSms(username, captcha)

        //verify { viewModel.verifyPhoneSms(username, captcha) }

        assertEquals(expectedResult.value,actualResult.value)
    }
    @Test
    fun testRegister() = runBlocking {
        val username = "zhangbo3"

        mockkObject(OpenWalletApplication)
        every { OpenWalletApplication.Companion.appViewModel } answers { appViewModel }
        every { OpenWalletApplication.appViewModel.loginToken} answers { "token" }
        every { OpenWalletApplication.appViewModel.secretToken.value} answers { "token" }

        val request = JsonObject()
        request.addProperty("username", username)
        request.addProperty("token", OpenWalletApplication.appViewModel.secretToken.value)


        val data = RegisterResponseData("data")
        val response = NetworkResponse<RegisterResponseData>("message",true,data)
        val resultState = ResultState.onAppSuccess(response)

        var expectedResult = MutableLiveData<ResultState<NetworkResponse<RegisterResponseData>>>()
        expectedResult.value = resultState

        every { retrofit.create(ApiService::class.java) } returns apiService

        coEvery { apiRepository.register(request) } coAnswers { response }

        //coEvery { viewModel.register(username) } returns expectedResult

        val actualResult = viewModel.register(username)

        //verify { viewModel.register(username) }

        assertEquals(expectedResult.value,actualResult.value)
    }
}