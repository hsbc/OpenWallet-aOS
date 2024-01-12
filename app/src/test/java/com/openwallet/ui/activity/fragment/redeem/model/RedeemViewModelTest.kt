package com.openwallet.ui.activity.fragment.redeem.model

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.openwallet.base.BaseViewModel
import com.openwallet.ext.request
import com.openwallet.network.ApiRepository
import com.openwallet.network.ApiService
import com.openwallet.network.exception.ExceptionEngineImpl
import com.openwallet.network.state.ResultState
import com.openwallet.ui.activity.fragment.logout.model.LogoutResponse
import com.openwallet.ui.activity.fragment.profile.notification.NotificationViewModel
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
class RedeemViewModelTest : TestCase() {
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule() // 解决  viewModelScope.launch
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    val retrofit = mockk<Retrofit>()
    val ipfsRetrofit = mockk<Retrofit>()
    val apiService = mockk<ApiService>()

    val apiRepository = mockk<ApiRepository>()
    val viewModel = RedeemViewModel(apiRepository)

    @Before
    public override fun setUp() {
        super.setUp()
        MockKAnnotations.init(this)
    }

    public override fun tearDown() {}
    @Test
    fun testRequestRedeem() = runBlocking {
        val nftId = 1

        viewModel.exceptionEngine = ExceptionEngineImpl()

        val redeemResult = RedeemResult("1",""+System.currentTimeMillis(),""+System.currentTimeMillis())
        val response = RedeemResponse(redeemResult)
        val resultState = ResultState.onAppSuccess(response)

        var expectedResult = MutableLiveData<ResultState<RedeemResponse>>()
        expectedResult.value = resultState

        every { retrofit.create(ApiService::class.java) } returns apiService

        coEvery { apiRepository.requestRedeem(nftId) } coAnswers { response }

        val actualResult = viewModel.requestRedeem(nftId)

        //verify { viewModel.requestRedeem(nftId) }

        assertEquals(expectedResult.value,actualResult.value)
    }
}