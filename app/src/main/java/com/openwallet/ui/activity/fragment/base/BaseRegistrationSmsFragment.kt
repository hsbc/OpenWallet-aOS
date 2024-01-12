package com.openwallet.ui.activity.fragment.base

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import com.openwallet.R
import com.openwallet.base.BaseFragment
import com.openwallet.databinding.FragmentSmsBinding
import com.openwallet.ext.hideKeyboard
import java.util.concurrent.TimeUnit

abstract class BaseRegistrationSmsFragment : BaseFragment() {

    val binding by viewBinding<FragmentSmsBinding>()

    //private val smsViewModel by viewModelFragment<SmsViewModel>()
    private var countDownTimer: CountDownTimer? = null

    abstract fun getParameters()
    abstract fun getTitle(): String
    abstract fun getStepBar()
    abstract fun getEmail(): String
    abstract fun submit()
    abstract fun getCountDown(): Long

    override fun initViewAndData(view: View, savedInstanceStat: Bundle?) {
        getParameters()
        binding.toolbar.init(title = getTitle(), isShowBack = true)
        getStepBar()
        binding.tvVerifyCode.text = getEmail()
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
        startCountDownTimer(getCountDown())
    }

    private fun checkSms() {
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

    private fun resendVerificationCode() {
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
    }

    private fun startCountDownTimer(countDown:Long) {
        stopCountDownTimer()
        countDownTimer = object : CountDownTimer(countDown, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val second = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) + 1
                if (second < 60) {
                    binding.countdown.text = if (second > 9) second.toString() else "0$second"
                } else {
                    val s: String =
                        if (second % 60 > 9) (second % 60).toString() else "0${second % 60}"
                    binding.countdown.text = "${second / 60}:$s"
                }
            }

            override fun onFinish() {
                binding.countdown.text = getString(R.string.resend_code)
            }
        }
        countDownTimer?.start()
    }

    private fun stopCountDownTimer() {
        countDownTimer?.cancel()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        stopCountDownTimer()
    }
}
