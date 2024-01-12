package com.openwallet.di

import com.openwallet.ui.activity.fragment.MainFragment
import com.openwallet.ui.activity.fragment.findusername.FindUsernameEmailFragment
import com.openwallet.ui.activity.fragment.findusername.FindUsernameEmailSmsFragment
import com.openwallet.ui.activity.fragment.findusername.FindUsernamePhoneFragment
import com.openwallet.ui.activity.fragment.findusername.FindUsernamePhoneSmsFragment
import com.openwallet.ui.activity.fragment.home.HomeFragment
import com.openwallet.ui.activity.fragment.homedetail.HomeDetailFragment
import com.openwallet.ui.activity.fragment.login.*
import com.openwallet.ui.activity.fragment.product.ProductDetailFragment
import com.openwallet.ui.activity.fragment.profile.ProfileMainFragment
import com.openwallet.ui.activity.fragment.profile.TermsAndConditionsProfileFragment
import com.openwallet.ui.activity.fragment.profile.changeavater.ProfileAvatarFragment
import com.openwallet.ui.activity.fragment.profile.changepassword.*
import com.openwallet.ui.activity.fragment.profile.deleteprofile.DeleteProfileFragment
import com.openwallet.ui.activity.fragment.profile.faq.FaqFragment
import com.openwallet.ui.activity.fragment.profile.helpcenter.HelpCenterFragment
import com.openwallet.ui.activity.fragment.profile.notification.NotificationFragment
import com.openwallet.ui.activity.fragment.profile.securitydigitalidentity.SecurityDigitalIdentityFragment
import com.openwallet.ui.activity.fragment.redeem.RedeemFragment
import com.openwallet.ui.activity.fragment.register.*
import com.openwallet.ui.activity.fragment.resetpassword.*
import com.openwallet.ui.activity.fragment.startregister.StartRegisterFragment
import com.openwallet.ui.activity.fragment.status.StatusFragment
import com.openwallet.ui.activity.fragment.wallet.WalletFragment
import com.openwallet.ui.activity.fragment.wallet.detail.WalletDetailBackgroundFragment
import com.openwallet.ui.activity.fragment.wallet.detail.WalletDetailDeliveryFragment
import com.openwallet.ui.activity.fragment.wallet.detail.WalletDetailFragment
import com.openwallet.ui.activity.fragment.wallet.detail.WalletSuccessRedeemFragment
import com.openwallet.ui.activity.fragment.welcome.WelcomeFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ViewModule {

    @ContributesAndroidInjector
    abstract fun bindMainFragment(): MainFragment

    @ContributesAndroidInjector
    abstract fun bindHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun bindHomeDetailFragment(): HomeDetailFragment

    @ContributesAndroidInjector
    abstract fun bindWalletFragment(): WalletFragment

    @ContributesAndroidInjector
    abstract fun bindWalletDetailFragment(): WalletDetailFragment

    @ContributesAndroidInjector
    abstract fun bindProductDetailFragment(): ProductDetailFragment

    @ContributesAndroidInjector
    abstract fun bindWalletDetailBackgroundFragment(): WalletDetailBackgroundFragment

    @ContributesAndroidInjector
    abstract fun bindWalletSuccessRedeemFragment(): WalletSuccessRedeemFragment

    @ContributesAndroidInjector
    abstract fun bindWalletDetailDeliveryFragment(): WalletDetailDeliveryFragment

    @ContributesAndroidInjector
    abstract fun bindTermsAndConditionsProfileFragment(): TermsAndConditionsProfileFragment

    @ContributesAndroidInjector
    abstract fun bindRedeemFragment(): RedeemFragment

    @ContributesAndroidInjector
    abstract fun bindWelcomeFragment(): WelcomeFragment

    @ContributesAndroidInjector
    abstract fun bindStartRegisterFragment(): StartRegisterFragment

    @ContributesAndroidInjector
    abstract fun bindRegisterConsentFragment(): RegisterConsentFragment

    @ContributesAndroidInjector
    abstract fun bindRegisterNameFragment(): RegisterNameFragment

    @ContributesAndroidInjector
    abstract fun bindRegisterEmailFragment(): RegisterEmailFragment

    @ContributesAndroidInjector
    abstract fun bindRegisterPasswordFragment(): RegisterPasswordFragment

    @ContributesAndroidInjector
    abstract fun bindRegisterSmsFragment(): RegisterEmailSmsFragment

    @ContributesAndroidInjector
    abstract fun bindRegisterPhoneFragment(): RegisterPhoneFragment

    @ContributesAndroidInjector
    abstract fun bindCountryCodeDialogFragment(): CountryCodeDialogFragment

    @ContributesAndroidInjector
    abstract fun bindRegisterPhoneSmsFragment(): RegisterPhoneSmsFragment

    @ContributesAndroidInjector
    abstract fun bindStatusFragment(): StatusFragment

    @ContributesAndroidInjector
    abstract fun bindProfileMainFragment(): ProfileMainFragment

    @ContributesAndroidInjector
    abstract fun bindLoginStartFragment(): LoginStartFragment

    @ContributesAndroidInjector
    abstract fun bindLoginPasswordFragment(): LoginPasswordFragment

    @ContributesAndroidInjector
    abstract fun bindLoginMethodFragment(): LoginMethodFragment

    @ContributesAndroidInjector
    abstract fun bindLoginEmailSmsFragment(): LoginEmailSmsFragment

    @ContributesAndroidInjector
    abstract fun bindLoginPhoneSmsFragment(): LoginPhoneSmsFragment

    @ContributesAndroidInjector
    abstract fun bindResetPasswordEmailFragment(): ResetPasswordEmailFragment

    @ContributesAndroidInjector
    abstract fun bindProfileAvatarFragment(): ProfileAvatarFragment

    @ContributesAndroidInjector
    abstract fun bindFaqFragment(): FaqFragment

    @ContributesAndroidInjector
    abstract fun bindFindUsernameEmailFragment(): FindUsernameEmailFragment

    @ContributesAndroidInjector
    abstract fun bindFindUsernameEmailSmsFragment(): FindUsernameEmailSmsFragment

    @ContributesAndroidInjector
    abstract fun bindFindUsernamePhoneFragment(): FindUsernamePhoneFragment

    @ContributesAndroidInjector
    abstract fun bindFindUsernamePhoneSmsFragment(): FindUsernamePhoneSmsFragment

    @ContributesAndroidInjector
    abstract fun bindSecurityDigitalIdentityFragment(): SecurityDigitalIdentityFragment

    @ContributesAndroidInjector
    abstract fun bindNotificationFragment(): NotificationFragment

    @ContributesAndroidInjector
    abstract fun bindHelpCenterFragment(): HelpCenterFragment

    @ContributesAndroidInjector
    abstract fun bindResetPasswordEmailSmsFragment(): ResetPasswordEmailSmsFragment

    @ContributesAndroidInjector
    abstract fun bindResetPasswordPhoneFragment(): ResetPasswordPhoneFragment

    @ContributesAndroidInjector
    abstract fun bindResetPasswordPhoneSmsFragment(): ResetPasswordPhoneSmsFragment

    @ContributesAndroidInjector
    abstract fun bindResetPasswordConfirmationFragment(): ResetPasswordConfirmationFragment

    @ContributesAndroidInjector
    abstract fun bindEnterCurrentPasswordFragment(): EnterCurrentPasswordFragment

    @ContributesAndroidInjector
    abstract fun bindChangePassWordEmailSmsFragment(): ChangePassWordEmailSmsFragment

    @ContributesAndroidInjector
    abstract fun bindChangePasswordPhoneSmsFragment(): ChangePasswordPhoneSmsFragment

    @ContributesAndroidInjector
    abstract fun bindChangePasswordMethodFragment(): ChangePasswordMethodFragment

    @ContributesAndroidInjector
    abstract fun bindChangePasswordConfirmationFragment(): ChangePasswordConfirmationFragment

    @ContributesAndroidInjector
    abstract fun bindDeleteProfileFragment(): DeleteProfileFragment
}