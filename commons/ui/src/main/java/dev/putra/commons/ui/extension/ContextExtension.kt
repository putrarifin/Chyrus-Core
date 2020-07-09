package dev.putra.commons.ui.extension

/**
 * Created by Chyrus on 3/28/2020.
 */

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Color
import android.net.Uri
import android.nfc.NfcManager
import android.util.TypedValue
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.AttrRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import kotlin.reflect.KProperty1

fun Context.toast(@StringRes resId: Int, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, getString(resId), length).show()
}

fun Context.toast(message: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, length).show()
}

inline fun <reified T : AppCompatActivity> Context.startActivity(
    vararg params: Pair<KProperty1<out Any?, Any?>, Any?>
) {
    val extras = params.map { it.first.name to it.second }.toTypedArray()
    val intent = Intent(this, T::class.java)
    intent.putExtras(bundleOf(*extras))
    startActivity(intent)
}

fun Context.openGooglePlayToUpdate(url: String?) {
    this.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
}

fun Context.openExternalContent(url: String?) {
    this.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
}

fun Context.openExternalContentAndExit(url: String?) {
    (this as? Activity)?.apply {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        finish()
    }
}

fun Context.isAndroidDeviceSupportNFC(): Boolean {
    val manager = this.getSystemService(Context.NFC_SERVICE) as NfcManager
    return manager.defaultAdapter != null
}

fun Context.isAndroidDeviceActiveNFC(): Boolean {
    val manager = this.getSystemService(Context.NFC_SERVICE) as NfcManager
    return manager.defaultAdapter?.isEnabled == true
}

fun Context.isFlashAvailable() =
    this.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)

fun Context.screenWidth() = Resources.getSystem().displayMetrics.widthPixels

fun Context.screenHeight() = Resources.getSystem().displayMetrics.heightPixels

fun Context.stringAt(@StringRes resId: Int?) =
    resId?.let {
        getString(it)
    } ?: run {
        ""
    }

/**
 * Convenience method for [ContextCompat.getColor]
 */
@ColorInt
fun Context.colorAt(@ColorRes color: Int) = ContextCompat.getColor(this, color)

/**
 * Convenience method for [ContextCompat.getDrawable]
 */
fun Context.drawableAt(@DrawableRes drawable: Int) = ContextCompat.getDrawable(this, drawable)

@ColorInt
fun Context.themeColorAt(@AttrRes colorAttr: Int): Int = TypedValue().run typedValue@{
    this@themeColorAt.theme.resolveAttribute(colorAttr, this@typedValue, true)
        .run { if (this) data else Color.BLACK }
}