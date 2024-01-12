package com.openwallet.ui.activity.fragment.login.model

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonObject
import com.openwallet.app.AppViewModel
import com.openwallet.app.OpenWalletApplication
import com.openwallet.ext.request
import com.openwallet.network.ApiRepository
import com.openwallet.network.ApiService
import com.openwallet.network.exception.ExceptionEngineImpl
import com.openwallet.network.state.ResultState
import com.openwallet.ui.activity.fragment.wallet.model.NetworkResponse
import com.openwallet.ui.activity.rule.MainCoroutineRule
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
class LoginViewModelTest : TestCase() {
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


    val viewModel = LoginViewModel(apiRepository)

    @Before
    public override fun setUp() {
        super.setUp()
        MockKAnnotations.init(this)
    }

    public override fun tearDown() {}
    @Test
    fun testLoginwithFirstFactor_Success() = runBlocking{
        val username = "zhangbo3"
        val password = "Aa123456&"

        val request = JsonObject()
        request.addProperty("username", username)
        request.addProperty("password", password)

        val exceptionEngine = ExceptionEngineImpl()
        viewModel.exceptionEngine = exceptionEngine

        val data = FirstFactorResponseData("token","maskedEmail","15911111111","111111")

        var response = NetworkResponse<FirstFactorResponseData>("message",true,data)
        val resultState = ResultState.onAppSuccess(response)

        var expectedResult = MutableLiveData<ResultState<NetworkResponse<FirstFactorResponseData>>>()
        expectedResult.value = resultState

        every { retrofit.create(ApiService::class.java) } returns apiService

        coEvery { apiRepository.loginWithFirstFactor(request,VerifyScenario.LOGIN.scenario) } returns response

        coEvery { viewModel.request({ response }, expectedResult).runCatching {  }.onSuccess { apiRepository.loginWithFirstFactor(request,VerifyScenario.LOGIN.scenario).status } } coAnswers { mockk<Result<Unit>>() }

        coEvery { viewModel.loginwithFirstFactor(username,password) } answers { expectedResult }

        val actualResult = viewModel.loginwithFirstFactor(username,password)
        actualResult.value = resultState

        //'when'.(loginViewModel.loginwithFirstFactor(username,password)).thenReturn()

        //verify { viewModel.loginwithFirstFactor(username,password) }

        assertEquals(expectedResult.value,actualResult.value)

    }

    @Test
    fun testLoginSendEmailSms() = runBlocking{

        val username = "zhangbo3"

        mockkObject(OpenWalletApplication)
        every { OpenWalletApplication.Companion.appViewModel } answers { appViewModel }
        every { appViewModel.loginToken} answers { "token" }
        every { appViewModel.secretToken.value} answers { "token" }

        val request = JsonObject()
        request.addProperty("username", username)
        request.addProperty("captchaScenarioEnum", "SIGN_IN")
        request.addProperty("captchaTypeEnum", "MAIL_VERIFY")
        request.addProperty("token", OpenWalletApplication.appViewModel.secretToken.value)

        val loginSendEmailSmsData = LoginSendEmailSmsData("token","111111")
        var response = LoginSendEmailSmsResponse(true,"message",loginSendEmailSmsData)
        val resultState = ResultState.onAppSuccess(response)

        var expectedResult = MutableLiveData<ResultState<LoginSendEmailSmsResponse>>()
        expectedResult.value = resultState

        every { retrofit.create(ApiService::class.java) } returns apiService

        coEvery { apiRepository.sendLoginEmailSms(request) } returns response

        // 使用 slot 来准备获取值
        //val mySlot = slot()
        // 方法调用时， 抓取传参
        //every { engine.setSpeed(capture(mySlot)) } just Runs
        //given(viewModel.request({ apiRepository.sendLoginEmailSms(request) }, expectedResult).runCatching {  }).willReturn(response)
        val actualResult = viewModel.loginSendEmailSms(username)

        //verify { viewModel.loginSendEmailSms(username) }

        assertEquals(expectedResult.value,actualResult.value)
    }
    @Test
    fun testLoginSendPhoneSms() = runBlocking{
        val username = "zhangbo3"

        mockkObject(OpenWalletApplication)
        every { OpenWalletApplication.Companion.appViewModel } answers { appViewModel }
        every { appViewModel.loginToken} answers { "token" }
        every { appViewModel.secretToken.value} answers { "token" }

        val request = JsonObject()
        request.addProperty("username", username)
        request.addProperty("captchaScenarioEnum", "SIGN_IN")
        request.addProperty("captchaTypeEnum", "SMS_VERIFY")
        request.addProperty("token", OpenWalletApplication.appViewModel.secretToken.value)

        val loginSendPhoneSmsData = LoginSendPhoneSmsData("token","111111")
        val response = LoginSendPhoneSmsResponse(true,"message",loginSendPhoneSmsData)
        val resultState = ResultState.onAppSuccess(response)

        var expectedResult = MutableLiveData<ResultState<LoginSendPhoneSmsResponse>>()
        expectedResult.value = resultState

        every { retrofit.create(ApiService::class.java) } returns apiService

        coEvery { apiRepository.sendLoginPhoneSms(request) } returns response

        val actualResult = viewModel.loginSendPhoneSms(username)

        //verify { viewModel.loginSendPhoneSms(username) }

        //assertEquals(expectedResult,actualResult)
        assertEquals(expectedResult.value,actualResult.value)
    }
    @Test
    fun testVerifyLoginEmailSms() = runBlocking {
        val username = "zhangbo3"
        val captcha = "000000"

        mockkObject(OpenWalletApplication)
        every { OpenWalletApplication.Companion.appViewModel } answers { appViewModel }
        every { appViewModel.loginToken} answers { "token" }
        every { appViewModel.secretToken.value} answers { "token" }

        val request = JsonObject()
        request.addProperty("username", username)
        request.addProperty("captcha", captcha)
        request.addProperty("captchaScenarioEnum", "SIGN_IN")
        request.addProperty("captchaTypeEnum", "MAIL_VERIFY")
        request.addProperty("token", OpenWalletApplication.appViewModel.secretToken.value)

        val roles = listOf<String>()

        val loginVerifyData = LoginVerifyData("token","type","1","username","email",roles)
        var response = LoginVerifyEmailSmsResponse(false,"message",loginVerifyData)
        val resultState = ResultState.onAppSuccess(response)

        var expectedResult = MutableLiveData<ResultState<LoginVerifyEmailSmsResponse>>()
        expectedResult.value = resultState

        every { retrofit.create(ApiService::class.java) } returns apiService

        coEvery { apiRepository.verifyLoginEmailSms(request) } returns response

        val actualResult = viewModel.verifyLoginEmailSms(username,captcha)

        //verify { viewModel.verifyLoginEmailSms(username,captcha) }

        assertEquals(expectedResult.value,actualResult.value)
    }
    @Test
    fun testVerifyLoginPhoneSms() = runBlocking {
        val username = "zhangbo3"
        val captcha = "000000"

        mockkObject(OpenWalletApplication)
        every { OpenWalletApplication.Companion.appViewModel } answers { appViewModel }
        every { appViewModel.loginToken} answers { "token" }
        every { appViewModel.secretToken.value} answers { "token" }

        val request = JsonObject()
        request.addProperty("username", username)
        request.addProperty("captcha", captcha)
        request.addProperty("captchaScenarioEnum", "SIGN_IN")
        request.addProperty("captchaTypeEnum", "SMS_VERIFY")
        request.addProperty("token", OpenWalletApplication.appViewModel.secretToken.value)

        val roles = listOf<String>()

        val loginVerifyData = LoginVerifyData("token","type","1",username,"email",roles)

        var response = LoginVerifyPhoneSmsResponse(false,"message",loginVerifyData)
        val resultState = ResultState.onAppSuccess(response)

        var expectedResult = MutableLiveData<ResultState<LoginVerifyPhoneSmsResponse>>()
        expectedResult.value = resultState

        every { retrofit.create(ApiService::class.java) } returns apiService

        coEvery { apiRepository.verifyLoginPhoneSms(request) } returns response

        val actualResult = viewModel.verifyLoginPhoneSms(username,captcha)
        //verify { viewModel.verifyLoginPhoneSms(username,captcha) }

        assertEquals(expectedResult.value,actualResult.value)
    }
}