package com.openwallet.util.viewbinder

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

internal class FragmentViewBindingDelegate<V : ViewBinding>(private val bindingReference: BindingReference<V>) :
    ReadOnlyProperty<Fragment, V> {
    private var observing = false

    override fun getValue(thisRef: Fragment, property: KProperty<*>): V {
        val binding = bindingReference.binding
            ?: throw IllegalStateException("Binding accessed, but it is null. Has inflate been called?")
        if (!thisRef.isInitialized) throw IllegalStateException("Should not attempt to get bindings when Fragment views are destroyed")
        // todo
//        if (!observing) {
//            observing = true
//            bindingReference.registerUnbinder(thisRef)
//        }

        return binding
    }

    private val Fragment.isInitialized: Boolean
        get() {
            val lifecycle = viewLifecycleOwner.lifecycle
            val currentState = lifecycle.currentState
            return currentState.isAtLeast(Lifecycle.State.INITIALIZED)
        }
}
