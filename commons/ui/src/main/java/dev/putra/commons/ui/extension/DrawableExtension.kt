package dev.putra.commons.ui.extension

import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.graphics.drawable.toBitmap

/**
 * Created by Chyrus on 3/28/2020.
 */

fun Drawable.withTint(@ColorInt tint: Int): Drawable = this.run {
    wrapped.apply { DrawableCompat.setTint(this, tint) }
}

@Suppress("unused")
fun Drawable.withTintList(tint: ColorStateList): Drawable = this.run {
    wrapped.apply { DrawableCompat.setTintList(this, tint) }
}

@Suppress("unused")
fun Drawable.withTintMode(tintMode: PorterDuff.Mode): Drawable = this.run {
    wrapped.apply { DrawableCompat.setTintMode(this, tintMode) }
}

val Drawable.wrapped: Drawable get() = DrawableCompat.wrap(this)

infix fun Drawable?.sameAs(other: Drawable?): Boolean {
    if (this == null || other == null) return false
    if (constantState != null && constantState == other.constantState) return true
    return toBitmap().sameAs(other.toBitmap())
}