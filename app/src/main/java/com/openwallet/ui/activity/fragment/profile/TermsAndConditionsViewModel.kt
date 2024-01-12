package com.openwallet.ui.activity.fragment.profile

import androidx.lifecycle.MutableLiveData
import com.openwallet.base.BaseViewModel
import com.openwallet.ext.request
import com.openwallet.network.ApiRepository
import com.openwallet.network.state.ResultState
import com.openwallet.ui.activity.fragment.register.model.TermsAndConditionRequestBody
import com.openwallet.ui.activity.fragment.wallet.model.NetworkResponse
import javax.inject.Inject

class TermsAndConditionsViewModel @Inject constructor(
    private val repository: ApiRepository
) : BaseViewModel() {

    fun getTermAndConditions(request: TermsAndConditionRequestBody): MutableLiveData<ResultState<NetworkResponse<TermsAndConditionsInfo>>> {
        val response = MutableLiveData<ResultState<NetworkResponse<TermsAndConditionsInfo>>>()
        request({ repository.getTermAndConditions(request) }, response)
        return response
    }
}