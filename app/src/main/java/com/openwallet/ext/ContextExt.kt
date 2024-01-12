package com.openwallet.ext

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat

fun Context.getStringById(resName: String): String {
    return resources.getString(
        resources.getIdentifier(
            resName,
            "string",
            applicationInfo.packageName
        )
    )
}

fun Context.getColorFromAttr(
    @AttrRes attrColor: Int,
    resolveRefs: Boolean = true
): Int {
    val typedValue = TypedValue()
    theme.resolveAttribute(attrColor, typedValue, resolveRefs)
    return typedValue.data
}

fun Context.getDrawableFromAttr(
    @AttrRes attrId: Int,
    typedValue: TypedValue = TypedValue(),
    resolveRefs: Boolean = true
): Drawable? {
    theme.resolveAttribute(attrId, typedValue, resolveRefs)
    return ContextCompat.getDrawable(this, typedValue.resourceId)
}

fun Context.getDrawableCompat(@DrawableRes resId: Int): Drawable? {
    return ContextCompat.getDrawable(this, resId)
}

fun Context.getDrawableByName(resName: String): Int {
    return resources.getIdentifier(
        resName,
        "drawable",
        applicationInfo.packageName
    )
}

fun Context.dp2px(dp: Float): Float = dp * resources.displayMetrics.density + 0.5f
