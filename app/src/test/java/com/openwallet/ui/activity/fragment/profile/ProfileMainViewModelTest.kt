package com.openwallet.ui.activity.fragment.profile

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
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Retrofit

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class ProfileMainViewModelTest : TestCase() {
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule() // 解决  viewModelScope.launch
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private val testCoroutineDispatcher = mainCoroutineRule.testDispatcher

    val retrofit = mockk<Retrofit>()
    val ipfsRetrofit = mockk<Retrofit>()
    val apiService = mockk<ApiService>()

    val apiRepository = mockk<ApiRepository>()//ApiRepository(retrofit,ipfsRetrofit)
    val viewModel = ProfileMainViewModel(apiRepository)

    @Before
    public override fun setUp() {
        super.setUp()
    }
    @Test
    fun testGetProfile() {

        viewModel.exceptionEngine = ExceptionEngineImpl()

        val profileInfo = ProfileInfo("username","email","1","15911111111","avatar",false)

        val response = NetworkResponse<ProfileInfo>("Success",true,profileInfo)
        val resultState = ResultState.onAppSuccess(response)
        //val error = AppException()
        //val resultState = ResultState.onAppInnerError<AppException>(error)
        val expectedResult = MutableLiveData<ResultState<NetworkResponse<ProfileInfo>>>()
        expectedResult.value = resultState

        every { retrofit.create(ApiService::class.java) } returns apiService

        coEvery { apiRepository.getProfile() } coAnswers { response }

//        `when`(viewModel.request({ apiRepository.getProfile() }, expectedResult)).thenAnswer { invocation ->
//            val args = invocation.arguments
//            args[0]
//            println("set index ${args[0]}")
//        }

//        verify {
//            viewModel.getProfile()
//        }
        val actualResult = viewModel.getProfile()

        assertEquals(expectedResult.value,actualResult.value)
    }
}