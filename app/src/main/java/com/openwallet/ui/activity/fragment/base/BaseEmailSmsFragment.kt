package com.openwallet.ui.activity.fragment.base

import android.os.Bundle
import android.os.CountDownTimer
import android.os.SystemClock
import android.view.View
import com.openwallet.R
import com.openwallet.constants.RESND_EMAIL_MILLI_SECONDS
import com.openwallet.ext.hideKeyboard
import com.openwallet.util.SmsTimerUtil
import java.util.concurrent.TimeUnit

abstract class BaseEmailSmsFragment : BaseSmsFragment() {

    override fun initViewAndData(view: View, savedInstanceStat: Bundle?) {
        getParameters()
        binding.toolbar.init(isShowClose = true)
        getStepBar()

        if (SmsTimerUtil.isEmailSmsCountDownReset) {
            resendVerificationCode()
            SmsTimerUtil.enterEmailSmsTimeStamp = SystemClock.elapsedRealtime()
        }
        //within 1 counting period, not send sms code request
        else {
            SmsTimerUtil.reEnterEmailSmsTimeStamp = SystemClock.elapsedRealtime()
            if (SmsTimerUtil.calculateEmailSmsTimeDiff() > RESND_EMAIL_MILLI_SECONDS) {
                resetTimer()
                resendVerificationCode()
                SmsTimerUtil.enterEmailSmsTimeStamp = SystemClock.elapsedRealtime()
            } else {
                startCountDownTimer()
            }
        }

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
                resetTimer()
                resendVerificationCode()
                SmsTimerUtil.enterEmailSmsTimeStamp = SystemClock.elapsedRealtime()
            }
        }
    }

    override fun startCountDownTimer() {
        stopCountDownTimer()
        binding.countdown.setTextColor(resources.getColor(R.color.black))
        var countDownTime: Long = 0
        if (SmsTimerUtil.isEmailSmsCountDownReset) {
            countDownTime = RESND_EMAIL_MILLI_SECONDS
        } else {
            if (SmsTimerUtil.calculateEmailSmsTimeDiff() < RESND_EMAIL_MILLI_SECONDS) {
                countDownTime = RESND_EMAIL_MILLI_SECONDS - SmsTimerUtil.calculateEmailSmsTimeDiff()
            }
        }


        countDownTimer = object : CountDownTimer(countDownTime, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val second = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) + 1
                if (second < 60) {
                    val s: String = if (second < 10) "0$second" else "$second"
                    binding.countdown.text = "00:${s} s"
                } else {
                    val s: String =
                        if (second % 60 > 9) (second % 60).toString() else "0${second % 60}"
                    val m: String = if (second / 60 > 9) "${second / 60}" else "0${second / 60}"
                    binding.countdown.text = "$m:$s"
                }
            }

            override fun onFinish() {
                binding.countdown.apply {
                    text = getString(R.string.resend_code)
                    setTextColor(resources.getColor(R.color.red))
                }
            }
        }
        countDownTimer?.start()
        SmsTimerUtil.isEmailSmsCountDownReset = false
    }

    override fun stopCountDownTimer() {
        countDownTimer?.cancel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        stopCountDownTimer()
    }

    fun resetTimer() {
        SmsTimerUtil.isEmailSmsCountDownReset = true
    }
}
