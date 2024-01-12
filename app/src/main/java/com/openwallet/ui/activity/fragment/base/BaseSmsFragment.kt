package com.openwallet.ui.activity.fragment.base

import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import com.openwallet.manager.VerificationCodeLockManager
import com.openwallet.R
import com.openwallet.app.OpenWalletApplication.Companion.appViewModel
import com.openwallet.base.BaseFragment
import com.openwallet.constants.ERROR_CODE_VERIFY_CODE_LOCKED
import com.openwallet.databinding.FragmentSmsBinding
import com.openwallet.ext.gone
import com.openwallet.ext.hideKeyboard
import com.openwallet.ext.parseState
import com.openwallet.ext.visible
import com.openwallet.ui.activity.fragment.sms.model.SmsRequest
import com.openwallet.ui.activity.fragment.sms.model.SmsViewModel

abstract class BaseSmsFragment : BaseFragment() {

    val binding by viewBinding<FragmentSmsBinding>()

    val smsViewModel by viewModelFragment<SmsViewModel>()
    var countDownTimer: CountDownTimer? = null

    abstract fun getParameters()
    abstract fun getSmsReuqest(): SmsRequest
    abstract fun getStepBar()
    abstract fun getReceiverInfo(): String
    abstract fun getSendCodeHint(): String
    abstract fun submit()
    abstract fun navigate()

    open fun getVerificationCodeType() = VerificationCodeLockManager.CodeType.NONE

    override fun initViewAndData(view: View, savedInstanceStat: Bundle?) {
        getParameters()
        binding.toolbar.init(isShowClose = true)
        getStepBar()
        binding.tvVerifyCodeHint.text = getSendCodeHint()
        binding.tvVerifyCode.text = getReceiverInfo()
        binding.sifSms.doAfterTextChanged {
            if (it.toString().length == 6) {
                activity?.hideKeyboard()
                submit()
            } else {
                binding.sifSms.state = StandardInputField.State.INPUT
                binding.sifSms.clearInlineMessageView()
            }
        }
        binding.countdown.setOnClickListener {
            if (getString(R.string.resend_code) == binding.countdown.text.toString()) {
                resendVerificationCode()
            }
        }
        //startCountDownTimer()
    }

    override fun onResume() {
        super.onResume()
        if(smsViewModel.isLocked(getVerificationCodeType(),getSmsReuqest())) {
            binding.sifSms.disable()
        }
    }

    fun resendVerificationCode() {
        //start timer when call send code api
        startCountDownTimer()
        smsViewModel.sendSms(getSmsReuqest()).observe(this) {
            parseState(
                it,
                onLoading = {
                    binding.loading.visible()
                    binding.sifSms.state = StandardInputField.State.INPUT
                    binding.sifSms.clearInlineMessageView()
                    binding.sifSms.text = ""
                },
                onSuccess = {
                    binding.loading.gone()
                    it.data?.token?.let { secretToken ->
                        appViewModel.secretToken.value = secretToken
                    }
                    changeVerifyCodeEditBoxState()
                },
                onNetworkOrExceptionError = {
                    binding.loading.gone()
                    changeVerifyCodeEditBoxState(it.errorMessage)
                },
                onInnerError = {
                    binding.loading.gone()
                    changeVerifyCodeEditBoxState(it.errorMessage)
                },
            )
        }
    }


    private fun checkSms() {
        navigate()
//        smsViewModel.checkEmailVerificationCode(
//            getEmail(),
//            binding.sifSms.text
//        ).observe(this) {
//            parseState(
//                it,
//                onLoading = {
//                    binding.loading.visible()
//                },
//                onSuccess = {
//                    binding.loading.gone()
//                    submit()
//                },
//                onError = {
//                    binding.loading.gone()
//                    binding.sifSms.errorMessage = it.errorMessage.orEmpty()
//                    binding.sifSms.state = StandardInputField.State.ERROR
//                }
//            )
//        }
    }

//    private fun resendVerificationCode() {
//        smsViewModel.sendEmailVerificationCode(getEmail()).observe(this) {
//            parseState(
//                it,
//                onLoading = {
//                    binding.loading.visible()
//                    binding.sifSms.state = StandardInputField.State.INPUT
//                    binding.sifSms.clearInlineMessageView()
//                    binding.sifSms.text = ""
//                },
//                onSuccess = {
//                    binding.loading.gone()
//                    startCountDownTimer()
//                },
//                onError = {
//                    binding.loading.gone()
//                    if (it.errorMessage?.isNotEmpty() == true) {
//                        when (it.errorMessage) {
//                            ERROR_MESSAGE_CAPTCHA_INCORRECT -> {
//                                binding.sifSms.errorMessage = it.errorMessage.orEmpty()
//                                binding.sifSms.state = StandardInputField.State.ERROR
//                            }
//                            else -> {
//                                nav().navigateAction(
//                                    R.id.action_RegisterSmsFragment_to_StatusFragment,
//                                    Bundle().apply {
//                                        putString(
//                                            PARAMS_ACTION_FROM,
//                                            PARAMS_ACTION_FROM_ERROR_VERIFICATION_CODE
//                                        )
//                                        putString(PARAMS_ERROR_MESSAGE, it.errorMessage)
//                                        putString(PARAMS_REGISTER_EMAIL, getEmail())
//                                    }
//                                )
//                            }
//                        }
//                    } else {
//                        CommonUtils.showSystemError(binding.content)
//                    }
//                }
//            )
//        }
//    }

    // 如果服务端返回0x10000002表示连续输入5次错误，被锁定，如果是其他状态则解锁
    protected fun changeVerifyCodeEditBoxState(error : String? = null) {
        if(error?.contains(ERROR_CODE_VERIFY_CODE_LOCKED) == true) {
            binding.sifSms.disable()
            smsViewModel.lock(getVerificationCodeType(),getSmsReuqest())
        } else {
            binding.sifSms.enable()
            smsViewModel.unlock(getVerificationCodeType(),getSmsReuqest())
        }
    }

    abstract fun startCountDownTimer()
//        stopCountDownTimer()
//        countDownTimer = object : CountDownTimer(countDown, 1000) {
//            override fun onTick(millisUntilFinished: Long) {
//                val second = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) + 1
//                if (second < 60) {
//                    binding.countdown.text = if (second > 9) second.toString() else "0$second"
//                } else {
//                    val s: String =
//                        if (second % 60 > 9) (second % 60).toString() else "0${second % 60}"
//                    binding.countdown.text = "${second / 60}:$s"
//                }
//            }
//
//            override fun onFinish() {
//                binding.countdown.text = getString(R.string.resend_code)
//            }
//        }
//        countDownTimer?.start()

    abstract fun stopCountDownTimer()
//        countDownTimer?.cancel()

    override fun onDestroyView() {
        super.onDestroyView()
        stopCountDownTimer()
    }
}
