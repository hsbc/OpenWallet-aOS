package com.openwallet.network

import com.google.gson.JsonObject
import com.openwallet.constants.RETROFIT_IPFS
import com.openwallet.model.token.RefreshTokenRequestBody
import com.openwallet.ui.activity.fragment.profile.changeavater.model.UpdateAvatarRequest
import com.openwallet.ui.activity.fragment.profile.helpcenter.FeedbackRequestBody
import com.openwallet.ui.activity.fragment.redeem.model.BankInfoRequestBody
import com.openwallet.ui.activity.fragment.register.model.TermsAndConditionRequestBody
import com.openwallet.ui.activity.fragment.sms.model.SmsRequest
import com.openwallet.ui.activity.fragment.sms.model.SmsVerificationRequest
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Named

class ApiRepository @Inject constructor(
    private val retrofit: Retrofit, @Named(RETROFIT_IPFS) private val ipfsRetrofit: Retrofit
) {

    private val apiService by lazy {
        retrofit.create(ApiService::class.java)
    }

    private val ipfsApiService by lazy {
        ipfsRetrofit.create(ApiService::class.java)
    }

    suspend fun getWalletList() = apiService.getWalletList()

    suspend fun getWalletDetail(detailPath: String) = apiService.getWalletDetail(detailPath)

    suspend fun requestRedeem(nftId: Int) = apiService.requestRedeem(BankInfoRequestBody(nftId))

    suspend fun getBankInfo() = apiService.getBankInfo()

    suspend fun hasExpiredNFT() = apiService.hasExpiredNFT()

    suspend fun registerUserName(request: JsonObject) = apiService.registerUsername(request)

    suspend fun registerPassword(request: JsonObject) = apiService.registerPassword(request)

    suspend fun registerSendEmailSms(request: JsonObject) = apiService.sendEmailSms(request)

    suspend fun registerSendPhoneSms(request: JsonObject) = apiService.sendPhoneSms(request)

    suspend fun verifyEmailSms(request: JsonObject) = apiService.verifyEmailSms(request)

    suspend fun verifyPhoneSms(request: JsonObject) = apiService.verifyPhoneSms(request)

    suspend fun loginWithFirstFactor(request: JsonObject, verifyScenario: String) =
        apiService.verifyFirstFactor(request, verifyScenario)

    suspend fun sendLoginEmailSms(request: JsonObject) = apiService.sendLoginEmailSms(request)

    suspend fun verifyLoginEmailSms(request: JsonObject) = apiService.verifyLoginEmailSms(request)

    suspend fun sendLoginPhoneSms(request: JsonObject) = apiService.sendLoginPhoneSms(request)

    suspend fun verifyLoginPhoneSms(request: JsonObject) = apiService.verifyLoginPhoneSms(request)

    suspend fun getFaqList() = apiService.getFaqList()

    suspend fun getProfile() = apiService.getProfile()

    suspend fun updateProfile(updateAvatarRequest: UpdateAvatarRequest) =
        apiService.updateAvatar(updateAvatarRequest)

    suspend fun sendFeedback(request: FeedbackRequestBody) = apiService.sendFeedBack(request)

    suspend fun register(request: JsonObject) = apiService.register(request)

    suspend fun getCountryCodeInfo() = apiService.getCountryCodeList()

    suspend fun sendSms(request: SmsRequest) = apiService.sendSms(request)

    suspend fun verifySms(request: SmsVerificationRequest) = apiService.verifySms(request)

    suspend fun resetPassword(request: JsonObject) = apiService.resetPassword(request)

    suspend fun changePassword(request: JsonObject) = apiService.changePassword(request)

    suspend fun changePasswordWithFirstFactor(request: JsonObject, verifyScenario: String) =
        apiService.verifyFirstFactor(request, verifyScenario)

    suspend fun getNotification() = apiService.getNotification()

    suspend fun updateNotificationStatus(
        notificationId: Int
    ) = apiService.updateNotificationStatus(notificationId)

    suspend fun getTermAndConditions(request: TermsAndConditionRequestBody) = apiService.getTermsAndConditions(request)

    suspend fun logout() = apiService.logout()

    fun refreshToken(body: RefreshTokenRequestBody) = apiService.refreshToken(body)

    suspend fun deleteUserProfile() = apiService.deleteProfile()

}
