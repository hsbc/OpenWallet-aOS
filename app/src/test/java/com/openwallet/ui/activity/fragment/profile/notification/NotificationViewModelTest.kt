package com.openwallet.ui.activity.fragment.profile.notification

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.openwallet.app.AppViewModel
import com.openwallet.app.OpenWalletApplication
import com.openwallet.ext.request
import com.openwallet.network.ApiRepository
import com.openwallet.network.ApiService
import com.openwallet.network.exception.ExceptionEngineImpl
import com.openwallet.network.state.ResultState
import com.openwallet.ui.activity.fragment.profile.notification.model.NotificationResponse
import com.openwallet.ui.activity.fragment.profile.notification.model.NotificationUpdateStatusResponse
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
class NotificationViewModelTest : TestCase() {
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule() // 解决  viewModelScope.launch
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testCoroutineDispatcher = mainCoroutineRule.testDispatcher

    val retrofit = mockk<Retrofit>()
    val ipfsRetrofit = mockk<Retrofit>()
    val apiService = mockk<ApiService>()

    val apiRepository = mockk<ApiRepository>()
    val viewModel = NotificationViewModel(apiRepository)

    val appViewModel = mockk<AppViewModel>()
    var secretToken = UnPeekLiveData.Builder<String>().setAllowNullValue(true).create()
    @Before
    public override fun setUp() {
        super.setUp()
        MockKAnnotations.init(this)
    }

    public override fun tearDown() {}
    @Test
    fun testReadNotification() = runBlocking {
        val notificationId = 1

        viewModel.exceptionEngine = ExceptionEngineImpl()

        val response = NotificationUpdateStatusResponse("1","data","message")
        val resultState = ResultState.onAppSuccess(response)

        var expectedResult = MutableLiveData<ResultState<NotificationUpdateStatusResponse>>()
        expectedResult.value = resultState

        every { retrofit.create(ApiService::class.java) } returns apiService

        coEvery { apiRepository.updateNotificationStatus(notificationId) } coAnswers { response }

        val actualResult = viewModel.readNotification(notificationId)

        //verify { viewModel.readNotification(notificationId) }

        assertEquals(expectedResult.value,actualResult.value)
    }
    @Test
    fun testGetNotification() = runBlocking {

        viewModel.exceptionEngine = ExceptionEngineImpl()

        mockkObject(OpenWalletApplication)
        every { OpenWalletApplication.Companion.appViewModel } returns appViewModel
        every { OpenWalletApplication.appViewModel.loginToken} returns "token"
        every { OpenWalletApplication.appViewModel.secretToken } returns secretToken

        var list = ArrayList<NotificationResponse.NotificationResponseItem>()
        val response = NotificationResponse("1","message",list)
        val resultState = ResultState.onAppSuccess(response)

        val notificationList = UnPeekLiveData<NotificationResponse>()

        every { OpenWalletApplication.appViewModel.notificationList } returns notificationList

        every { OpenWalletApplication.appViewModel.notificationList.value } returns response

        var expectedResult = MutableLiveData<ResultState<NotificationResponse>>()
        expectedResult.value = resultState

        val mockViewModel = mockk<NotificationViewModel>()
        every { retrofit.create(ApiService::class.java) } returns apiService

        coEvery { apiRepository.getNotification() } coAnswers { response }

        coEvery { viewModel.getNotification() } coAnswers { expectedResult }
        coEvery { mockViewModel.getNotification() } coAnswers { expectedResult }

        val actualResult = mockViewModel.getNotification()

        //verify { viewModel.getNotification() }

        assertEquals(expectedResult.value,actualResult.value)
    }
}