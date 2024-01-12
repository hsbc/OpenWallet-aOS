package com.openwallet.util

import android.os.Handler
import android.os.Looper
import android.view.View
import com.google.gson.Gson
import com.openwallet.R
import com.openwallet.app.OpenWalletApplication.Companion.appViewModel
import com.openwallet.constants.EMAIL_REGEX
import com.openwallet.constants.USERNAME_REGEX
import com.openwallet.manager.CacheManager
import com.openwallet.model.token.TokenInfoEntity
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.text.DecimalFormat
import java.util.*

object CommonUtils {

    private val mHandler = Handler(Looper.getMainLooper())

    fun runOnUIThread(block: () -> Unit) {
        if (Thread.currentThread() == Looper.getMainLooper().thread) {
            block()
        } else {
            mHandler.post { block() }
        }
    }

    fun runOnUIThread(delayMillis: Long, block: () -> Unit) {
        if (Thread.currentThread() == Looper.getMainLooper().thread) {
            block()
        } else {
            mHandler.postDelayed(block, delayMillis)
        }
    }

    fun cancelRunOnUIThread(block: () -> Unit) {
        mHandler.removeCallbacks(block)
    }

    fun runOnIOThread(block: () -> Unit): Disposable {
        return io.reactivex.Observable.just(1).subscribeOn(Schedulers.io()).subscribe { block() }
    }

    fun maskEmailaddress(email: String): String {
        val sb = StringBuilder()
        val firstPart = email.substringBefore('@')
        val secondPart = email.substringAfter('@')
        if (firstPart.length < 3) {
            sb.append(firstPart.take(1)).append("******").append("@").append(secondPart)
        } else {
            sb.append(firstPart.substring(0, 3)).append("******").append("@").append(secondPart)
        }
        return sb.toString()
    }

    fun maskPhoneNumber(phone: String): String {
        val sb = StringBuilder()
        val secondPart = phone.takeLast(4)

        return sb.append("******").append(secondPart).toString()
    }

    fun getFullPhoneNumber(): String {
        return appViewModel.profileInfo.value?.run {
            phoneCountryCode.orEmpty() + phoneNumber.orEmpty()
        }.toString()
    }

    fun isVaildUserName(username: String): Boolean {
        val regex = Regex(USERNAME_REGEX)
        return regex.matches(username)
    }

    fun isValidEmailAddress(email: String): Boolean {
        val regex = Regex(EMAIL_REGEX)
        return regex.matches(email)
    }

    fun showSystemError(view: View) {
        TopSnackbar.make(
            view,
            view.resources.getString(R.string.error_service_unavailable),
            TopSnackbar.Style.WARNING
        ).setTopMargin(
            view.resources.getDimension(R.dimen.dimen_52dp).toInt()
        ).show()
    }

    fun isLogin(): Boolean {
        return !appViewModel.loginToken.isNullOrBlank()
    }

    fun isRefreshTokenExist(): Boolean {
        return !appViewModel.refreshToken.isNullOrBlank()
    }

    fun isRememberedMe(): Boolean {
        return CacheManager.isRememberMe()
    }

    fun getRememberedUserName(): String {
        return CacheManager.getUser()?.username.orEmpty()
    }

    fun clearRememberMeState() {
        CacheManager.clearRememberMe()
    }

    fun clearInput() {
        appViewModel.userInput?.reset()
    }

    fun clearLoginState() {
        appViewModel.userInput?.reset()
        appViewModel.loginToken = ""
        appViewModel.refreshToken = ""
        appViewModel.loginStatus.value = false

        appViewModel.profileInfo.value = null
        appViewModel.sessionTimeOut.value = false
        appViewModel.tabIndex.value = 0
    }

    fun getNftIdString(number: String?): String {
        return if (number.isNullOrBlank()) {
            "N/A"
        } else {
            val decimalFormat = DecimalFormat("0000000000")
            decimalFormat.format(number.toLong())
        }
    }

    fun getLocalHour(): Int {
        val calendar = Calendar.getInstance();
        val hour = calendar.get(Calendar.HOUR_OF_DAY);
        return hour
    }

    /**
     * time stamp unit is second.
     */
    fun getTokenExpTime(token: String?): Long {
        if (token.isNullOrBlank()) {
            return 0
        }
        val strList = token.split(".")
        if (strList.isEmpty() || strList.size < 3) {
            return 0
        }
        val tokenInfoStr = strList[1]
        val tokenInfo =
            Gson().fromJson(Base64Utils.decodeStr(tokenInfoStr), TokenInfoEntity::class.java)
        return tokenInfo.expirationTime
    }


}