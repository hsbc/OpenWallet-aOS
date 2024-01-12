package com.openwallet.ui.activity.fragment.login.model

import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonObject
import com.openwallet.app.OpenWalletApplication.Companion.appViewModel
import com.openwallet.base.BaseViewModel
import com.openwallet.ext.request
import com.openwallet.network.ApiRepository
import com.openwallet.network.state.ResultState
import com.openwallet.ui.activity.fragment.wallet.model.NetworkResponse
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val repository: ApiRepository,
) : BaseViewModel() {

    fun loginwithFirstFactor(
        username: String,
        password: String
    ): MutableLiveData<ResultState<NetworkResponse<FirstFactorResponseData>>> {
        val response: MutableLiveData<ResultState<NetworkResponse<FirstFactorResponseData>>> = MutableLiveData()
        request(
            {
                val request = JsonObject()
                request.addProperty("username", username)
                request.addProperty("password", password)
                repository.loginWithFirstFactor(request, VerifyScenario.LOGIN.scenario)
            },
            response
        )
        return response
    }


    fun loginSendEmailSms(
        username: String,
    ): MutableLiveData<ResultState<LoginSendEmailSmsResponse>> {
        val loginSendEmailSmsLiveData: MutableLiveData<ResultState<LoginSendEmailSmsResponse>> = MutableLiveData()
        request(
            {
                val request = JsonObject()
                request.addProperty("username", username)
                request.addProperty("token", appViewModel.secretToken.value)
                request.addProperty("captchaScenarioEnum", "SIGN_IN")
                request.addProperty("captchaTypeEnum", "MAIL_VERIFY")

                repository.sendLoginEmailSms(request)
            },
            loginSendEmailSmsLiveData
        )
        return loginSendEmailSmsLiveData
    }


    fun loginSendPhoneSms(
        username: String,
    ): MutableLiveData<ResultState<LoginSendPhoneSmsResponse>> {
        val loginSendEmailSmsLiveData: MutableLiveData<ResultState<LoginSendPhoneSmsResponse>> = MutableLiveData()
        request(
            {
                val request = JsonObject()
                request.addProperty("username", username)
                request.addProperty("token", appViewModel.secretToken.value)
                request.addProperty("captchaScenarioEnum", "SIGN_IN")
                request.addProperty("captchaTypeEnum", "SMS_VERIFY")

                repository.sendLoginPhoneSms(request)
            },
            loginSendEmailSmsLiveData
        )
        return loginSendEmailSmsLiveData
    }

    fun verifyLoginEmailSms(
        username: String,
        captcha: String
    ): MutableLiveData<ResultState<LoginVerifyEmailSmsResponse>> {
        val loginVerifyEmailSmsLiveData: MutableLiveData<ResultState<LoginVerifyEmailSmsResponse>> = MutableLiveData()
        request(
            {
                val request = JsonObject()
                request.addProperty("username", username)
                request.addProperty("captcha", captcha)
                request.addProperty("token", appViewModel.secretToken.value)
                request.addProperty("captchaScenarioEnum", "SIGN_IN")
                request.addProperty("captchaTypeEnum", "MAIL_VERIFY")
                repository.verifyLoginEmailSms(request)
            },
            loginVerifyEmailSmsLiveData
        )
        return loginVerifyEmailSmsLiveData
    }

    fun verifyLoginPhoneSms(
        username: String,
        captcha: String
    ): MutableLiveData<ResultState<LoginVerifyPhoneSmsResponse>> {
        val loginVerifyPhoneSmsLiveData: MutableLiveData<ResultState<LoginVerifyPhoneSmsResponse>> = MutableLiveData()
        request(
            {
                val request = JsonObject()
                request.addProperty("username", username)
                request.addProperty("captcha", captcha)
                request.addProperty("token", appViewModel.secretToken.value)
                request.addProperty("captchaScenarioEnum", "SIGN_IN")
                request.addProperty("captchaTypeEnum", "SMS_VERIFY")
                repository.verifyLoginPhoneSms(request)
            },
            loginVerifyPhoneSmsLiveData
        )
        return loginVerifyPhoneSmsLiveData
    }
}

