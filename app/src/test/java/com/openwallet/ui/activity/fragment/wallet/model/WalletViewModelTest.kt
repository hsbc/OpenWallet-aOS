package com.openwallet.ui.activity.fragment.wallet.model

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.openwallet.base.BaseViewModel
import com.openwallet.ext.request
import com.openwallet.network.ApiRepository
import com.openwallet.network.ApiService
import com.openwallet.network.exception.ExceptionEngine
import com.openwallet.network.exception.ExceptionEngineImpl
import com.openwallet.network.state.ResultState
import com.openwallet.ui.activity.fragment.logout.model.LogoutResponse
import com.openwallet.ui.activity.fragment.redeem.model.BankInfo
import com.openwallet.ui.activity.fragment.redeem.model.BankInfoResponse
import com.openwallet.ui.activity.fragment.redeem.model.RedeemResponse
import com.openwallet.ui.activity.fragment.redeem.model.RedeemResult
import com.openwallet.ui.activity.fragment.register.model.RegisterViewModel
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
class WalletViewModelTest : TestCase() {
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule() // 解决  viewModelScope.launch
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testCoroutineDispatcher = mainCoroutineRule.testDispatcher

    var walletDetail: WalletDetail? = null

    lateinit var walletSummary: WalletSummary

    val retrofit = mockk<Retrofit>()
    val ipfsRetrofit = mockk<Retrofit>()
    val apiService = mockk<ApiService>()

    val apiRepository = mockk<ApiRepository>()
    val viewModel = WalletViewModel(apiRepository)

    @Before
    public override fun setUp() {
        super.setUp()
        MockKAnnotations.init(this)
    }

    public override fun tearDown() {}
    @Test
    fun testGetList() = runBlocking {

        viewModel.exceptionEngine = ExceptionEngineImpl()

        val imageList = listOf<String>()

        val walletSummary = WalletSummary("name","edition",imageList,"detailLinkUrl",1,"ownerBy",RedeemStatus.REDEEMED,""+System.currentTimeMillis())
        val dataList = listOf<WalletSummary>(walletSummary)
        var response = NetworkResponse<List<WalletSummary>>("message",true,dataList)
        val resultState = ResultState.onAppSuccess(response)

        var expectedResult = MutableLiveData<ResultState<NetworkResponse<List<WalletSummary>>>>()
        expectedResult.value = resultState

        every { retrofit.create(ApiService::class.java) } returns apiService

        coEvery { apiRepository.getWalletList() } coAnswers { response }

        val actualResult = viewModel.getList()

        //verify { viewModel.getList() }

        assertEquals(expectedResult.value,actualResult.value)
    }
    @Test
    fun testGetWalletDetail() = runBlocking {

        val detailPath = ""

        viewModel.exceptionEngine = ExceptionEngineImpl()

        val imageList = listOf<String>()
        val walletDetail = WalletDetail("name","ownedBy","15911111111","911","edition",
            "material","fineness","50","goldItemInformation",imageList , RedeemStatus.UNREDEEMED,""+System.currentTimeMillis())
        val response = WalletDetailResponse(data = walletDetail)

        val resultState = ResultState.onAppSuccess(response)

        var expectedResult = MutableLiveData<ResultState<WalletDetailResponse>>()
        expectedResult.value = resultState

        every { retrofit.create(ApiService::class.java) } returns apiService

        coEvery { apiRepository.getWalletDetail(detailPath) } coAnswers { response }

        val actualResult = viewModel.getWalletDetail(detailPath)

        //verify { viewModel.getWalletDetail(detailPath) }

        assertEquals(expectedResult.value,actualResult.value)
    }
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
    @Test
    fun testGetBankInfo() = runBlocking {
        val imageList = listOf<String>()

        viewModel.exceptionEngine = ExceptionEngineImpl()

        val response = BankInfoResponse()
        val resultState = ResultState.onAppSuccess(response)

        var expectedResult = MutableLiveData<ResultState<BankInfoResponse>>()
        expectedResult.value = resultState

        every { retrofit.create(ApiService::class.java) } returns apiService

        coEvery { apiRepository.getBankInfo() } coAnswers { response }

        val walletDetail = WalletDetail("name","ownedBy","15911111111","911","edition",
            "material","fineness","50","goldItemInformation",imageList ,RedeemStatus.UNREDEEMED,""+System.currentTimeMillis())
        viewModel.walletDetail = walletDetail

        val bankInfo = BankInfo("","86","")
        viewModel.bankInfo = bankInfo

        val walletSummary = WalletSummary("name","edition",imageList,"detailLinkUrl",1,"ownerBy",RedeemStatus.REDEEMED,""+System.currentTimeMillis())
        viewModel.walletSummary = walletSummary

        viewModel.bankInfo
        viewModel.walletSummary
        viewModel.walletDetail
        viewModel.walletSummary.datetime
        viewModel.getWalletDetail("path")

        val actualResult = viewModel.getBankInfo()

        //verify { viewModel.getBankInfo() }

        assertEquals(expectedResult.value,actualResult.value)
    }
}