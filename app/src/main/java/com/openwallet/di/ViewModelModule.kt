package com.openwallet.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.openwallet.app.AppViewModel
import com.openwallet.ui.activity.fragment.home.HomeViewModel
import com.openwallet.ui.activity.fragment.login.model.LoginViewModel
import com.openwallet.ui.activity.fragment.logout.model.LogoutViewModel
import com.openwallet.ui.activity.fragment.profile.ProfileMainViewModel
import com.openwallet.ui.activity.fragment.profile.TermsAndConditionsViewModel
import com.openwallet.ui.activity.fragment.profile.changeavater.ProfileAvatarViewModel
import com.openwallet.ui.activity.fragment.profile.changepassword.model.ChangePasswordViewModel
import com.openwallet.ui.activity.fragment.profile.deleteprofile.DeleteProfileViewModel
import com.openwallet.ui.activity.fragment.profile.faq.FaqViewModel
import com.openwallet.ui.activity.fragment.profile.helpcenter.HelpCenterViewModel
import com.openwallet.ui.activity.fragment.profile.notification.NotificationViewModel
import com.openwallet.ui.activity.fragment.profile.securitydigitalidentity.SecurityDigitalIdentityViewModel
import com.openwallet.ui.activity.fragment.redeem.model.RedeemViewModel
import com.openwallet.ui.activity.fragment.register.model.CountryCodeListViewModel
import com.openwallet.ui.activity.fragment.register.model.RegisterViewModel
import com.openwallet.ui.activity.fragment.resetpassword.model.ResetPasswordViewModel
import com.openwallet.ui.activity.fragment.sms.model.SmsViewModel
import com.openwallet.ui.activity.fragment.wallet.model.WalletViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(AppViewModel::class)
    abstract fun bindAppViewModel(appViewModel: AppViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(appViewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(WalletViewModel::class)
    abstract fun bindWalletViewModel(viewModel: WalletViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RedeemViewModel::class)
    abstract fun bindRedeemViewModel(viewModel: RedeemViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RegisterViewModel::class)
    abstract fun bindRegisterViewModel(viewModel: RegisterViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindLoginViewModel(viewModel: LoginViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(FaqViewModel::class)
    abstract fun bindFaqViewModel(viewModel: FaqViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProfileAvatarViewModel::class)
    abstract fun bindProfileAvatarViewModel(viewModel: ProfileAvatarViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HelpCenterViewModel::class)
    abstract fun bindHelpCenterViewModel(viewModel: HelpCenterViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProfileMainViewModel::class)
    abstract fun bindProfileMainViewModel(viewModel: ProfileMainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TermsAndConditionsViewModel::class)
    abstract fun bindTermsAndConditionsViewModel(viewModel: TermsAndConditionsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SmsViewModel::class)
    abstract fun bindSmsViewModel(viewModel: SmsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ResetPasswordViewModel::class)
    abstract fun bindResetPasswordViewModel(viewModel: ResetPasswordViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NotificationViewModel::class)
    abstract fun bindNotificationViewModel(viewModel: NotificationViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ChangePasswordViewModel::class)
    abstract fun bindChangePasswordViewModel(viewModel: ChangePasswordViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LogoutViewModel::class)
    abstract fun bindLogoutViewModel(viewModel: LogoutViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CountryCodeListViewModel::class)
    abstract fun bindCountryCodeModel(viewModel: CountryCodeListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DeleteProfileViewModel::class)
    abstract fun bindDeleteProfileViewModel(viewModel: DeleteProfileViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SecurityDigitalIdentityViewModel::class)
    abstract fun bindSecurityDigitalIdentityViewModel(viewModel: SecurityDigitalIdentityViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactoryModule): ViewModelProvider.Factory


}