package com.openwallet.ui.activity.fragment.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
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
class HomeViewModelTest : TestCase() {
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule() // 解决  viewModelScope.launch
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    val retrofit = mockk<Retrofit>()
    val ipfsRetrofit = mockk<Retrofit>()
    val apiService = mockk<ApiService>()
    val apiRepository = mockk<ApiRepository>()

    val viewModel = HomeViewModel(apiRepository)

    @Before
    public override fun setUp() {
        super.setUp()
        MockKAnnotations.init(this)
        //mockkStatic(ArchTaskExecutor::class)
        //every { ArchTaskExecutor.getInstance().isMainThread } answers { true }
    }

    public override fun tearDown() {}
    @Test
    fun testHasExpiredNFT_Success() = runBlocking {

        viewModel.exceptionEngine = ExceptionEngineImpl()

        var response = NetworkResponse<Boolean>("message",true,true)
        val resultState = ResultState.onAppSuccess(response)

        var expectedResult = MutableLiveData<ResultState<NetworkResponse<Boolean>>>()
        expectedResult.value = resultState

        every { retrofit.create(ApiService::class.java) } answers { apiService }

        coEvery { apiRepository.hasExpiredNFT() } returns response

        val actualResult = viewModel.hasExpiredNFT()

//        val callMock = Mockito.mock(MutableLiveData::class.java) as MutableLiveData<ResultState<NetworkResponse<Boolean>>>
//        Mockito.`when`(retrofit.create(ApiService::class.java))
//            .thenReturn(apiService)
//        Mockito.`when`(
//            homeViewModel.hasExpiredNFT()
//        ).thenReturn(callMock)

        //verify { viewModel.hasExpiredNFT() }

        assertEquals(expectedResult.value,actualResult.value)


    }
//    @Test(expected = Exception::class)
//    fun testHasExpiredNFT_Exception() {
//        val viewModel = mockk<HomeViewModel>()
//        var response = MutableLiveData<ResultState<NetworkResponse<Boolean>>>()
//        coEvery { viewModel.hasExpiredNFT() } answers { response }
//        val actualResult = viewModel.hasExpiredNFT()
//        assertEquals(NullPointerException(),actualResult)
//    }
}