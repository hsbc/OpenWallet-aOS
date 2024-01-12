package com.openwallet.ui.activity.fragment.sms.model

import androidx.lifecycle.MutableLiveData
import com.openwallet.manager.VerificationCodeLockManager
import com.openwallet.base.BaseViewModel
import com.openwallet.ext.request
import com.openwallet.network.ApiRepository
import com.openwallet.network.state.ResultState
import com.openwallet.ui.activity.fragment.wallet.model.NetworkResponse
import javax.inject.Inject

class SmsViewModel @Inject constructor(
    private val repository: ApiRepository
) : BaseViewModel() {

    fun sendSms(smsRequest: SmsRequest): MutableLiveData<ResultState<NetworkResponse<SmsResponseData>>> {
        val response = MutableLiveData<ResultState<NetworkResponse<SmsResponseData>>>()
        request({ repository.sendSms(smsRequest) }, response)
        return response
    }

    fun verifySms(smsRequest: SmsVerificationRequest): MutableLiveData<ResultState<NetworkResponse<SmsVerificationResponseData>>> {
        val response = MutableLiveData<ResultState<NetworkResponse<SmsVerificationResponseData>>>()
        request({ repository.verifySms(smsRequest) }, response)
        return response
    }

    fun lock(codeType: VerificationCodeLockManager.CodeType, request: SmsRequest) {
        val key = request.emailAddress ?: request.phoneNumber
        VerificationCodeLockManager.getInstance().lock(codeType, key)
    }

    fun unlock(codeType: VerificationCodeLockManager.CodeType, request: SmsRequest) {
        val key = request.emailAddress ?: request.phoneNumber
        VerificationCodeLockManager.getInstance().unlock(codeType, key)
    }

    fun isLocked(codeType: VerificationCodeLockManager.CodeType, request: SmsRequest): Boolean {
        val key = request.emailAddress ?: request.phoneNumber
        return VerificationCodeLockManager.getInstance().isLocked(codeType, key)
    }
}