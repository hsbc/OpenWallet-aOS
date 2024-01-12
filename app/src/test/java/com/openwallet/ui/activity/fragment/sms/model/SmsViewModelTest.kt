package com.openwallet.ui.activity.fragment.sms.model

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.openwallet.manager.VerificationCodeLockManager
import com.openwallet.network.ApiRepository
import com.openwallet.network.ApiService
import com.openwallet.network.exception.ExceptionEngineImpl
import com.openwallet.network.state.ResultState
import com.openwallet.ui.activity.fragment.wallet.model.NetworkResponse
import com.openwallet.ui.activity.rule.MainCoroutineRule
import io.mockk.*
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Retrofit

@RunWith(MockitoJUnitRunner::class)
class SmsViewModelTest : TestCase() {
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule() // 解决  viewModelScope.launch
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    val retrofit = mockk<Retrofit>()
    val ipfsRetrofit = mockk<Retrofit>()
    val apiService = mockk<ApiService>()

    val verificationCodeLockManager = mockk<VerificationCodeLockManager>()

    val apiRepository = mockk<ApiRepository>()
    val viewModel = SmsViewModel(apiRepository)
    @Before
    public override fun setUp() {
        super.setUp()
        MockKAnnotations.init(this)
    }

    public override fun tearDown() {}
    @Test
    fun testSendSms() = runBlocking{
        val username = "zhangbo3"
        val emailAddress = ""
        val token = ""
        val phoneCountryCode = "1"
        val phoneNumber = "15911111111"
        val captchaScenarioEnum: CaptchaScenario = CaptchaScenario.SIGN_IN
        val captchaTypeEnum: CaptchaType = CaptchaType.SMS_VERIFY

        val smsRequest = SmsRequest(username,emailAddress,token, phoneCountryCode, phoneNumber, captchaScenarioEnum, captchaTypeEnum)

        //val viewModel = mockk<SmsViewModel>()
        viewModel.exceptionEngine = ExceptionEngineImpl()

        val data = SmsResponseData("token","111111")
        var response = NetworkResponse<SmsResponseData>("message",true,data)
        val resultState = ResultState.onAppSuccess(response)

        var expectedResult = MutableLiveData<ResultState<NetworkResponse<SmsResponseData>>>()
        expectedResult.value = resultState

        every { retrofit.create(ApiService::class.java) } returns apiService

        coEvery { apiRepository.sendSms(smsRequest) } coAnswers { response }

        val actualResult = viewModel.sendSms(smsRequest)

        //coVerify { viewModel.sendSms(smsRequest) }

        assertEquals(expectedResult.value,actualResult.value)
    }

    @Test
    fun testVerifySms() = runBlocking{
        val emailAddress = ""
        val username = "zhangbo3"
        val captcha = "000000"
        val token = ""
        val captchaScenarioEnum: CaptchaScenario = CaptchaScenario.SIGN_IN
        val captchaTypeEnum: CaptchaType = CaptchaType.SMS_VERIFY

        val smsVerificationRequest = SmsVerificationRequest(emailAddress,username,captcha,token, captchaScenarioEnum,captchaTypeEnum)

        viewModel.exceptionEngine = ExceptionEngineImpl()

        val roles = listOf<String>()

        val data = SmsVerificationResponseData("111111","token","refreshToken","type","1","username","email",roles)
        var response = NetworkResponse<SmsVerificationResponseData>("message",true,data)
        val resultState = ResultState.onAppSuccess(response)

        var expectedResult = MutableLiveData<ResultState<NetworkResponse<SmsVerificationResponseData>>>()
        expectedResult.value = resultState

        every { retrofit.create(ApiService::class.java) } returns apiService

        coEvery { apiRepository.verifySms(smsVerificationRequest) } coAnswers { response }

        val actualResult = viewModel.verifySms(smsVerificationRequest)

        //verify { viewModel.verifySms(smsVerificationRequest) }

        assertEquals(expectedResult.value,actualResult.value)
    }
    @Test
    fun testLock() = runBlocking{
        mockkObject(VerificationCodeLockManager.getInstance())

        val smsRequest = SmsRequest("username","","token","86","15911111111",CaptchaScenario.SIGN_IN,CaptchaType.SMS_VERIFY)

        viewModel.exceptionEngine = ExceptionEngineImpl()

        viewModel.lock(VerificationCodeLockManager.CodeType.CODE_EMAIL,smsRequest)

        //coVerify { viewModel.lock(VerificationCodeLockManager.CodeType.CODE_EMAIL,smsRequest) }
    }
    @Test
    fun testIsLock() = runBlocking{
        mockkObject(VerificationCodeLockManager.getInstance())

        val smsRequest = SmsRequest("username","test@noteternalmail.com","token","86","",CaptchaScenario.SIGN_IN,CaptchaType.SMS_VERIFY)

        viewModel.exceptionEngine = ExceptionEngineImpl()

        val expectedResult = true

        coEvery { viewModel.isLocked(VerificationCodeLockManager.CodeType.CODE_EMAIL,smsRequest) } coAnswers { expectedResult }

        val actualResult = viewModel.isLocked(VerificationCodeLockManager.CodeType.CODE_EMAIL,smsRequest)

        assert(actualResult)
    }
    @Test
    fun testUnLock() = runBlocking{
        mockkObject(VerificationCodeLockManager.getInstance())

        val smsRequest = SmsRequest("username","","token","86","15911111111",CaptchaScenario.SIGN_IN,CaptchaType.SMS_VERIFY)

        viewModel.exceptionEngine = ExceptionEngineImpl()

        viewModel.unlock(VerificationCodeLockManager.CodeType.CODE_SMS,smsRequest)

        //coVerify { viewModel.lock(VerificationCodeLockManager.CodeType.CODE_EMAIL,smsRequest) }
    }
}