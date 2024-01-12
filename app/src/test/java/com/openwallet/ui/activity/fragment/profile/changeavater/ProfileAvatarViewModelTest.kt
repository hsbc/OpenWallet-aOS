package com.openwallet.ui.activity.fragment.profile.changeavater

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.openwallet.ext.request
import com.openwallet.network.ApiRepository
import com.openwallet.network.ApiService
import com.openwallet.network.exception.ExceptionEngineImpl
import com.openwallet.network.state.ResultState
import com.openwallet.ui.activity.fragment.profile.changeavater.model.ProfileInfo
import com.openwallet.ui.activity.fragment.profile.changeavater.model.UpdateAvatarRequest
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
class ProfileAvatarViewModelTest : TestCase() {
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule() // 解决  viewModelScope.launch
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testCoroutineDispatcher = mainCoroutineRule.testDispatcher

    val retrofit = mockk<Retrofit>()
    val ipfsRetrofit = mockk<Retrofit>()
    val apiService = mockk<ApiService>()

    val apiRepository = mockk<ApiRepository>()
    val viewModel = ProfileAvatarViewModel(apiRepository)
    @Before
    public override fun setUp() {
        super.setUp()
        MockKAnnotations.init(this)
    }

    public override fun tearDown() {}
    @Test
    fun testUpdateAvatar() = runBlocking {
        val avatar = ""

        viewModel.exceptionEngine = ExceptionEngineImpl()

        val data = ProfileInfo("username","email","1","15911111111","avatar",false)

        var response = NetworkResponse<ProfileInfo>("message",true,data)
        val resultState = ResultState.onAppSuccess(response)

        var expectedResult = MutableLiveData<ResultState<NetworkResponse<ProfileInfo>>>()
        expectedResult.value = resultState

        every { retrofit.create(ApiService::class.java) } returns apiService

        coEvery { apiRepository.updateProfile(UpdateAvatarRequest(avatar)) } coAnswers { response }

        val actualResult = viewModel.updateAvatar(avatar)

        //verify { viewModel.updateAvatar(avatar) }

        assertEquals(expectedResult.value,actualResult.value)
    }
}