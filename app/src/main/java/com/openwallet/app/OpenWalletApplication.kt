com.openwallet

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.bumptech.glide.Glide
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.model.GlideUrl
import com.openwallet.di.DaggerOpenWalletComponent
import com.openwallet.network.mock.RetrofitMock
import com.tencent.mmkv.MMKV
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.HasSupportFragmentInjector
import okhttp3.OkHttpClient
import java.io.InputStream
import javax.inject.Inject


class OpenWalletApplication : DaggerApplication(), ViewModelStoreOwner, HasActivityInjector,
    HasSupportFragmentInjector {

    companion object {
        const val APP_ID = "open_wallet"
        lateinit var instance: OpenWalletApplication
        lateinit var appViewModel: AppViewModel
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var okHttpClient: OkHttpClient

    override fun onCreate() {

        DaggerOpenWalletComponent.builder().application(this).build().inject(this)
        super.onCreate()
        instance = this
        MMKV.initialize(this, this.filesDir.absolutePath + "/mmkv")
        appViewModel = ViewModelProvider(this, viewModelFactory).get(AppViewModel::class.java)
        RetrofitMock.init(this, "response_demo.json");
        Glide.get(this).registry.replace(
            GlideUrl::class.java, InputStream::class.java, OkHttpUrlLoader.Factory(okHttpClient)
        )




    }

    override fun getViewModelStore(): ViewModelStore = ViewModelStore()

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerOpenWalletComponent.builder().application(this).build()
    }

    @Inject
    internal lateinit var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): DispatchingAndroidInjector<Activity> {
        return activityDispatchingAndroidInjector
    }

    @Inject
    internal lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return fragmentDispatchingAndroidInjector
    }


}