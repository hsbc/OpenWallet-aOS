package com.openwallet.ext

import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.updateLayoutParams
import com.openwallet.util.DisplayUtil

fun StandardInputField.showPassword(isShowPassWord: Boolean) {
    val selectEnd = selectionEnd
    if (isShowPassWord) {
        setTransformationMethod(HideReturnsTransformationMethod.getInstance())
        ContextCompat.getDrawable(context, R.drawable.ic_view_active)
            ?.let { setIcon(it) }
    } else {
        setTransformationMethod(PasswordTransformationMethod.getInstance())
        ContextCompat.getDrawable(context, R.drawable.ic_view)
            ?.let { setIcon(it) }
    }
    setEditTextSelection(selectEnd)
}

fun StandardInputField.showLablewithIcon(lable: String, icon: Int, onClick: () -> Unit) {
    val tv_label: TextView = this.findViewById(R.id.sInputFieldLabel)
    tv_label.apply {
        setCompoundDrawablesWithIntrinsicBounds(0, 0, icon, 0)
        text = lable
        compoundDrawablePadding = DisplayUtil.dip2px(context, 4f)
        updateLayoutParams<ConstraintLayout.LayoutParams> {
            this.endToEnd = ConstraintLayout.LayoutParams.UNSET
        }
        setOnClickListener {
            onClick()
        }
    }
}
