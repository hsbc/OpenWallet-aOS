package com.openwallet.ui.activity.fragment.logout.model

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.openwallet.ext.request
import com.openwallet.network.ApiRepository
import com.openwallet.network.ApiService
import com.openwallet.network.exception.ExceptionEngineImpl
import com.openwallet.network.state.ResultState
import com.openwallet.ui.activity.fragment.profile.changeavater.model.ProfileInfo
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
class LogoutViewModelTest : TestCase() {
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule() // 解决  viewModelScope.launch
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    val retrofit = mockk<Retrofit>()
    val ipfsRetrofit = mockk<Retrofit>()
    val apiService = mockk<ApiService>()

    val apiRepository = mockk<ApiRepository>()//ApiRepository(retrofit,ipfsRetrofit)
    val viewModel = LogoutViewModel(apiRepository)
    @Before
    public override fun setUp() {
        super.setUp()
        MockKAnnotations.init(this)
    }

    @Test
    fun testLogout_Success() = runBlocking {
        //val viewModel = mockk<LogoutViewModel>()
        viewModel.exceptionEngine = ExceptionEngineImpl()

        val data = LogoutResponse(false,"message","data")
        var response = NetworkResponse<LogoutResponse>("message",true,data)
        val resultState = ResultState.onAppSuccess(response)

        val expectedResult = MutableLiveData<ResultState<NetworkResponse<LogoutResponse>>>()
        expectedResult.value = resultState

        every { retrofit.create(ApiService::class.java) } returns apiService

        coEvery { apiRepository.logout() } coAnswers { response }

        //coEvery { viewModel.request({ response }, expectedResult).runCatching {  }.onSuccess { apiRepository.logout().status } } coAnswers { mockk<Result<Unit>>() }

        //coEvery { viewModel.logout() } returns expectedResult

        var actualResult = viewModel.logout()

        //verify { viewModel.logout() }

        assertEquals(expectedResult.value,actualResult.value)
    }
}