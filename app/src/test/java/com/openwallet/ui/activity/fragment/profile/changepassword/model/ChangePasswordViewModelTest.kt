package com.openwallet.ui.activity.fragment.profile.changepassword.model

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
import com.openwallet.ui.activity.fragment.login.model.FirstFactorResponseData
import com.openwallet.ui.activity.fragment.login.model.VerifyScenario
import com.openwallet.ui.activity.fragment.register.model.RegisterUserPasswordResponseData
import com.openwallet.ui.activity.fragment.wallet.model.NetworkResponse
import com.openwallet.ui.activity.rule.MainCoroutineRule
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
class ChangePasswordViewModelTest : TestCase() {
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule() // 解决  viewModelScope.launch
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testCoroutineDispatcher = mainCoroutineRule.testDispatcher

    val retrofit = mockk<Retrofit>()
    val ipfsRetrofit = mockk<Retrofit>()
    val apiService = mockk<ApiService>()

    val apiRepository = mockk<ApiRepository>()//ApiRepository(retrofit,ipfsRetrofit)
    val appViewModel = mockk<AppViewModel>()

    val viewModel = ChangePasswordViewModel(apiRepository)
    var secretToken = UnPeekLiveData.Builder<String>().setAllowNullValue(true).create()

    @Before
    public override fun setUp() {
        super.setUp()
        MockKAnnotations.init(this)
    }

    public override fun tearDown() {}
    @Test
    fun testChangePasswordWithFirstFactor() {

        viewModel.exceptionEngine = ExceptionEngineImpl()
        val password = "Aa123456&"
        val request = JsonObject()
        request.addProperty("password", password)

        val data = FirstFactorResponseData("token","maskedEmail","15911111111","111111")
        var response = NetworkResponse<FirstFactorResponseData>("message",true,data)
        val resultState = ResultState.onAppSuccess(response)

        var expectedResult = MutableLiveData<ResultState<NetworkResponse<FirstFactorResponseData>>>()
        expectedResult.value = resultState

        every { retrofit.create(ApiService::class.java) } returns apiService

        coEvery { apiRepository.changePasswordWithFirstFactor(request,VerifyScenario.CHANGE_PASSWORD.scenario) } coAnswers { response }

        val actualResult = viewModel.changePasswordWithFirstFactor(password)

        //verify { viewModel.changePasswordWithFirstFactor(password) }

        assertEquals(expectedResult.value,actualResult.value)
    }
    @Test
    fun testChangePassword() = runBlocking {
        val password = "Aa123456&"

        viewModel.exceptionEngine = ExceptionEngineImpl()

        mockkObject(OpenWalletApplication)
        every { OpenWalletApplication.Companion.appViewModel } returns appViewModel
        every { OpenWalletApplication.appViewModel.loginToken} returns "token"
        every { OpenWalletApplication.appViewModel.secretToken } returns secretToken
        every { OpenWalletApplication.appViewModel.secretToken.value} answers { "token" }

        val request = JsonObject()
        request.addProperty("password", password)
        request.addProperty("token",OpenWalletApplication.appViewModel.secretToken.value)

        val data = ChangePasswordResponse("data")
        var response = NetworkResponse<ChangePasswordResponse>("message",true,data)
        val resultState = ResultState.onAppSuccess(response)

        var expectedResult = MutableLiveData<ResultState<NetworkResponse<ChangePasswordResponse>>>()
        expectedResult.value = resultState

        every { retrofit.create(ApiService::class.java) } returns apiService

        coEvery { apiRepository.changePassword(request) } coAnswers { response }

        val actualResult = viewModel.changePassword(password)

        //verify { viewModel.changePassword(password) }

        assertEquals(expectedResult.value,actualResult.value)
    }
}