package com.openwallet.network

import com.google.gson.JsonObject
import com.openwallet.model.token.RefreshTokenRequestBody
import com.openwallet.model.token.RefreshTokenResponse
import com.openwallet.network.interceptor.TokenCheckInterceptor
import com.openwallet.ui.activity.fragment.login.model.*
import com.openwallet.ui.activity.fragment.logout.model.LogoutResponse
import com.openwallet.ui.activity.fragment.profile.TermsAndConditionsInfo
import com.openwallet.ui.activity.fragment.profile.changeavater.model.ProfileInfo
import com.openwallet.ui.activity.fragment.profile.changeavater.model.UpdateAvatarRequest
import com.openwallet.ui.activity.fragment.profile.changepassword.model.ChangePasswordResponse
import com.openwallet.ui.activity.fragment.profile.faq.model.FaqData
import com.openwallet.ui.activity.fragment.profile.helpcenter.FeedbackRequestBody
import com.openwallet.ui.activity.fragment.profile.notification.model.NotificationResponse
import com.openwallet.ui.activity.fragment.profile.notification.model.NotificationUpdateStatusResponse
import com.openwallet.ui.activity.fragment.redeem.model.BankInfoRequestBody
import com.openwallet.ui.activity.fragment.redeem.model.BankInfoResponse
import com.openwallet.ui.activity.fragment.redeem.model.RedeemResponse
import com.openwallet.ui.activity.fragment.register.model.*
import com.openwallet.ui.activity.fragment.resetpassword.model.ResetPasswordResponseData
import com.openwallet.ui.activity.fragment.sms.model.SmsRequest
import com.openwallet.ui.activity.fragment.sms.model.SmsResponseData
import com.openwallet.ui.activity.fragment.sms.model.SmsVerificationRequest
import com.openwallet.ui.activity.fragment.sms.model.SmsVerificationResponseData
import com.openwallet.ui.activity.fragment.wallet.model.NetworkResponse
import com.openwallet.ui.activity.fragment.wallet.model.WalletDetailResponse
import com.openwallet.ui.activity.fragment.wallet.model.WalletSummary
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("/api/blockchain/nft/list")
    suspend fun getWalletList(): NetworkResponse<List<WalletSummary>>

    @GET("/{detailPath}")
    suspend fun getWalletDetail(
        @Path("detailPath", encoded = true) detailPath: String,
    ): WalletDetailResponse


    @POST("/api/delivery/create")
    suspend fun requestRedeem(
        @Body request: BankInfoRequestBody
    ): RedeemResponse

    @GET("/api/bank/info")
    suspend fun getBankInfo(): BankInfoResponse

    @GET("/api/blockchain/nft/check/hasExpiringToken")
    suspend fun hasExpiredNFT(): NetworkResponse<Boolean>

    @POST("/api/customer/register/username")
    suspend fun registerUsername(@Body request: JsonObject): NetworkResponse<RegisterUserNameResponseData>

    @POST("/api/customer/register/password")
    suspend fun registerPassword(@Body request: JsonObject): NetworkResponse<RegisterUserPasswordResponseData>

    @POST("/api/captcha/send")
    suspend fun sendEmailSms(@Body request: JsonObject): RegisterSendEmailSmsResponse

    @POST("/api/captcha/send")
    suspend fun sendPhoneSms(@Body request: JsonObject): RegisterSendPhoneSmsResponse

    @POST("/api/captcha/validate")
    suspend fun verifyEmailSms(@Body request: JsonObject): RegisterVerifyEmailSmsResponse

    @POST("/api/captcha/validate")
    suspend fun verifyPhoneSms(@Body request: JsonObject): RegisterVerifyPhoneSmsResponse

    @POST("/api/auth/verify-first-factor/{verifyScenario}")
    suspend fun verifyFirstFactor(
        @Body request: JsonObject,
        @Path("verifyScenario") verifyScenario: String
    ): NetworkResponse<FirstFactorResponseData>

    @POST("/api/captcha/send")
    suspend fun sendLoginEmailSms(@Body request: JsonObject): LoginSendEmailSmsResponse

    @POST("/api/captcha/validate")
    suspend fun verifyLoginEmailSms(@Body request: JsonObject): LoginVerifyEmailSmsResponse

    @POST("/api/captcha/send")
    suspend fun sendLoginPhoneSms(@Body request: JsonObject): LoginSendPhoneSmsResponse

    @POST("/api/captcha/validate")
    suspend fun verifyLoginPhoneSms(@Body request: JsonObject): LoginVerifyPhoneSmsResponse

    @POST("/api/customer/register")
    suspend fun register(@Body request: JsonObject): NetworkResponse<RegisterResponseData>

    @GET("/api/faq/list")
    suspend fun getFaqList(): NetworkResponse<MutableList<FaqData>>

    @GET("/api/customer/profile/get")
    suspend fun getProfile(): NetworkResponse<ProfileInfo>

    @POST("/api/customer/profile/update")
    suspend fun updateAvatar(@Body updateAvatarRequest: UpdateAvatarRequest): NetworkResponse<ProfileInfo>

    @POST("/api/feedback/send")
    suspend fun sendFeedBack(@Body request: FeedbackRequestBody): NetworkResponse<String>

    @POST("/api/captcha/send")
    suspend fun sendSms(@Body request: SmsRequest): NetworkResponse<SmsResponseData>

    @POST("/api/captcha/validate")
    suspend fun verifySms(@Body request: SmsVerificationRequest): NetworkResponse<SmsVerificationResponseData>

    @GET("/api/auth/get-countrycode")
    suspend fun getCountryCodeList(): CountryCodeListResponse

    @POST("api/auth/password/reset")
    suspend fun resetPassword(@Body request: JsonObject): NetworkResponse<ResetPasswordResponseData>

    @POST("api/auth/password/change")
    suspend fun changePassword(@Body request: JsonObject): NetworkResponse<ChangePasswordResponse>

    @GET("/api/notification/list/")
    suspend fun getNotification(): NotificationResponse

    @PUT("/api/notification/status/update/{notificationId}")
    suspend fun updateNotificationStatus(
        @Path("notificationId") notificationId: Int
    ): NotificationUpdateStatusResponse

    @POST("api/tnc/REGISTRATION/EN")
    suspend fun getTermsAndConditions(@Body request: TermsAndConditionRequestBody): NetworkResponse<TermsAndConditionsInfo>

    @POST("api/auth/logout")
    suspend fun logout(): NetworkResponse<LogoutResponse>

    @POST("api/auth/refresh")
    @Headers(TokenCheckInterceptor.isRefreshToken)
    fun refreshToken(@Body body: RefreshTokenRequestBody): Call<NetworkResponse<RefreshTokenResponse>>

    @PUT("api/customer/status/update")
    suspend fun deleteProfile(@Query("status") status: String = "CLOSING" ): NetworkResponse<String>

}
