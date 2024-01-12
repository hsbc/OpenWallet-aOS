package com.openwallet.ext

import android.os.Bundle
import android.os.SystemClock
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment

fun Fragment.nav(): NavController {
    return NavHostFragment.findNavController(this)
}

fun nav(view: View): NavController {
    return Navigation.findNavController(view)
}

var lastNavTime = 0L

fun NavController.navigateAction(resId: Int, bundle: Bundle? = null, interval: Long = 500) {
    val currentTime = SystemClock.elapsedRealtime()
    if (currentTime >= lastNavTime + interval) {
        lastNavTime = currentTime
        try {
            navigate(resId, bundle)
        } catch (ignore: Exception) {
        }
    }
}

fun NavController.isFragmentInBackStack(destinationId: Int): Boolean {
    return try {
        getBackStackEntry(destinationId)
        true
    } catch (e: Exception) {
        false
    }
}

fun NavController.safeNavigate(destinationId: Int, inclusive: Boolean = false): Unit {
    if (isFragmentInBackStack(destinationId)) {
        popBackStack(destinationId, inclusive)
    } else {
        navigate(destinationId)
    }
}



