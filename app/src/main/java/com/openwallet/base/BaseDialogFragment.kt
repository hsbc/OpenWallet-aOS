package com.openwallet.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.viewbinding.ViewBinding
import com.openwallet.util.viewbinder.FragmentViewBinder
import com.openwallet.view.StateLayout
import dagger.android.support.DaggerDialogFragment
import javax.inject.Inject
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KClass


abstract class BaseDialogFragment : DaggerDialogFragment() {

    protected val binder = FragmentViewBinder()
    protected lateinit var stateLayout: StateLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (isNeedStatusLayout()) {
            val fragmentView = binder.inflate(inflater, container)
            stateLayout = StateLayout(requireContext()).wrap(fragmentView)
            stateLayout.useContentBgWhenLoading = true
            stateLayout.noEmptyAndError = isNoNeedEmptyAndErrorLayout()
            stateLayout.mRetryAction = { requestData() }
            return stateLayout
        } else {
            return binder.inflate(inflater, container)
        }
    }

    fun <V : ViewBinding> viewBinding(viewBindingKClass: KClass<V>): ReadOnlyProperty<Fragment, V> {
        return binder.viewBinding(viewBindingKClass)
    }

    inline fun <reified V : ViewBinding> viewBinding(): ReadOnlyProperty<Fragment, V> =
        viewBinding(V::class)

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    inline fun <reified T : ViewModel> viewModelActivity() =
        lazy {
            requireActivity().let { ViewModelProvider(it, viewModelFactory).get(T::class.java) }
        }

    inline fun <reified T : ViewModel> viewModelFragment() =
        lazy { ViewModelProviders.of(this, viewModelFactory).get(T::class.java) }

    abstract fun initViewAndData(view: View, savedInstanceStat: Bundle?)

    open fun isNeedStatusLayout(): Boolean = false

    open fun isNoNeedEmptyAndErrorLayout(): Boolean = false

    open fun requestData() {}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.isClickable = true
        initViewAndData(view, savedInstanceState)
    }


}