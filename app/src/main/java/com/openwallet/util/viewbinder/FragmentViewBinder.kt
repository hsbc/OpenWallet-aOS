package com.openwallet.util.viewbinder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KClass

internal typealias InflateViewBinding<V> = (LayoutInflater, ViewGroup?, Boolean) -> V

private const val METHOD = "inflate"
private val ARGUMENTS =
    arrayOf(LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java)

class FragmentViewBinder {
    private lateinit var bindingReference: BindingReference<ViewBinding>

    fun inflate(inflater: LayoutInflater, viewGroup: ViewGroup?): View {
        val binding = bindingReference.inflate(inflater, viewGroup, false)
        return binding.root
    }

    fun <V : ViewBinding> viewBinding(viewBindingKClass: KClass<V>): ReadOnlyProperty<Fragment, V> {
        val inflate = viewBindingKClass.inflateMethod
        this.bindingReference = BindingReference(inflate)
        return FragmentViewBindingDelegate(bindingReference) as ReadOnlyProperty<Fragment, V>
    }

    private val <V : ViewBinding> KClass<V>.inflateMethod: InflateViewBinding<V>
        get() = { inflater, viewGroup, attachToParent ->
            val method = this.java.getMethod(METHOD, *ARGUMENTS)
            method.invoke(null, inflater, viewGroup, attachToParent) as V
        }
}