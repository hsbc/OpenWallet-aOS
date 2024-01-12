package com.openwallet.base

import androidx.lifecycle.ViewModel
import com.openwallet.network.exception.ExceptionEngine
import javax.inject.Inject

open class BaseViewModel : ViewModel() {
    @Inject
    lateinit var exceptionEngine: ExceptionEngine
}