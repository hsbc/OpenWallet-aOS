package com.openwallet.di

import android.content.Context
import com.openwallet.app.OpenWalletApplication
import com.openwallet.network.exception.ExceptionEngine
import com.openwallet.network.exception.ExceptionEngineImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideContext(application: OpenWalletApplication): Context {
        return application
    }

    @Provides
    @Singleton
    fun provideExceptionEngine(): ExceptionEngine = ExceptionEngineImpl()
}