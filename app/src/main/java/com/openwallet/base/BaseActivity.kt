package com.openwallet.base

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Rect
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment

abstract class BaseActivity : AppCompatActivity() {

    override fun onBackPressed() {
        val navHostFragment = supportFragmentManager.fragments.first() as NavHostFragment
        val currentFragment =
            navHostFragment.childFragmentManager.primaryNavigationFragment as OnBackPressedListener
            //navHostFragment.childFragmentManager.primaryNavigationFragment as OnBackPressedListener
        val handled = currentFragment.navigateBack()
        if (handled) return

        super.onBackPressed()
    }

    @SuppressLint("ServiceCast")
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if(ev?.action == MotionEvent.ACTION_DOWN){
            var view  = currentFocus
            if(view is EditText){
                var outRect = Rect()
                view.getGlobalVisibleRect(outRect)
                if(!outRect.contains(ev.rawX.toInt(),ev.rawY.toInt())){
                    view.clearFocus()
                    val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(view.windowToken,0)
                }
            }
        }
        return super.dispatchTouchEvent(ev)
    }



}

interface OnBackPressedListener {
    fun navigateBack(): Boolean
}


