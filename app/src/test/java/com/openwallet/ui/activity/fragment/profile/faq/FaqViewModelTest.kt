package com.openwallet.ui.activity.fragment.profile.faq

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.openwallet.base.BaseViewModel
import com.openwallet.ext.request
import com.openwallet.network.ApiRepository
import com.openwallet.network.ApiService
import com.openwallet.network.exception.ExceptionEngineImpl
import com.openwallet.network.state.ResultState
import com.openwallet.network.state.paresResult
import com.openwallet.ui.activity.fragment.logout.model.LogoutResponse
import com.openwallet.ui.activity.fragment.profile.changepassword.model.ChangePasswordViewModel
import com.openwallet.ui.activity.fragment.profile.faq.model.FaqData
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
class FaqViewModelTest : TestCase() {
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule() // 解决  viewModelScope.launch
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testCoroutineDispatcher = mainCoroutineRule.testDispatcher

    val retrofit = mockk<Retrofit>()
    val ipfsRetrofit = mockk<Retrofit>()
    val apiService = mockk<ApiService>()

    val apiRepository = mockk<ApiRepository>()
    val viewModel = FaqViewModel(apiRepository)

    @Before
    public override fun setUp() {
        super.setUp()
        MockKAnnotations.init(this)
    }

    public override fun tearDown() {}
    @Test
    fun testGetFaqList() = runBlocking {

        viewModel.exceptionEngine = ExceptionEngineImpl()

        val faqData = FaqData("answer",1,"question","1",false)
        val dataList = mutableListOf(faqData)

        var response = NetworkResponse<MutableList<FaqData>>("message",true,dataList)
        val resultState = ResultState.onAppSuccess(response)

        var expectedResult = MutableLiveData<ResultState<NetworkResponse<MutableList<FaqData>>>>()
        expectedResult.value = resultState

        every { retrofit.create(ApiService::class.java) } returns apiService

        coEvery { apiRepository.getFaqList() } coAnswers { response }

        val actualResult = viewModel.getFaqList()

        //verify { viewModel.getFaqList() }

        assertEquals(expectedResult.value,actualResult.value)
    }

}