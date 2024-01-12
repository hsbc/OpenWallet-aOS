package com.openwallet.ui.activity.fragment.resetpassword.model

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
import com.openwallet.ui.activity.fragment.register.model.RegisterUserNameResponseData
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
class ResetPasswordViewModelTest : TestCase() {
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule() // 解决  viewModelScope.launch
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testCoroutineDispatcher = mainCoroutineRule.testDispatcher

    val retrofit = mockk<Retrofit>()
    val ipfsRetrofit = mockk<Retrofit>()

    val apiService = mockk<ApiService>()
    val apiRepository = mockk<ApiRepository>()

    val viewModel = ResetPasswordViewModel(apiRepository)

    val appViewModel = mockk<AppViewModel>()
    var secretToken = UnPeekLiveData.Builder<String>().setAllowNullValue(true).create()

    @Before
    public override fun setUp() {
        super.setUp()
        MockKAnnotations.init(this)
    }
    //@After
    public override fun tearDown() {
    }
    @Test
    fun testResetPassword() = runBlocking {
        //val callMock = Mockito.mock(MutableLiveData::class.java) as MutableLiveData<ResultState<NetworkResponse<ResetPasswordResponseData>>>

        val resetPassword = "123456&"

        viewModel.exceptionEngine = ExceptionEngineImpl()

        mockkObject(OpenWalletApplication)
        every { OpenWalletApplication.Companion.appViewModel } returns appViewModel
        every { OpenWalletApplication.appViewModel.loginToken} returns "token"
        every { OpenWalletApplication.appViewModel.secretToken } returns secretToken
        every { OpenWalletApplication.appViewModel.secretToken.value} returns "token"

        val request = JsonObject()
        request.addProperty("newPassword", resetPassword)
        request.addProperty("token",OpenWalletApplication.appViewModel.secretToken.value)

        val data = ResetPasswordResponseData("data")
        var response = NetworkResponse<ResetPasswordResponseData>("message",true,data)
        val resultState = ResultState.onAppSuccess(response)

        var expectedResult = MutableLiveData<ResultState<NetworkResponse<ResetPasswordResponseData>>>()
        expectedResult.value = resultState

        every { retrofit.create(ApiService::class.java) } answers { apiService }

        every { retrofit.create(ApiService::class.java) } returns apiService

        coEvery { apiRepository.resetPassword(request) } coAnswers { response }

//        coEvery { viewModel.request({ response }, expectedResult).runCatching {  }
//            .onSuccess {
//                val request = JsonObject()
//                request.addProperty("newPassword", resetPassword)
//                request.addProperty("token",OpenWalletApplication.appViewModel.secretToken.value)
//                apiRepository.resetPassword(request).status
//            }
//        } coAnswers { mockk<Result<Unit>>() }


        //coEvery { viewModel.resetPassword(resetPassword) } returns expectedResult
//        Mockito.`when`(
//            viewModel.resetPassword(
//                resetPassword
//            )
//        ).thenReturn(callMock)

        val actualResult = viewModel.resetPassword(resetPassword)

        //verify { viewModel.resetPassword(resetPassword) }
        assertEquals(expectedResult.value,actualResult.value)

    }
}