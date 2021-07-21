package dev.putra.commons.utilities.extension

/**
 * Created by Chyrus on 3/28/2020.
 */

import android.annotation.SuppressLint
import android.text.Spanned
import androidx.core.text.HtmlCompat
import java.security.MessageDigest
import java.util.*
import java.util.regex.Pattern

@SuppressLint("DefaultLocale")
fun String.formatInitial(): String? {
    val pattern = Pattern.compile("((^| )[A-Za-z])")
    val matcher = pattern.matcher(this)
    var initials = String()
    while (matcher.find()) {
        initials += matcher.group().trim()
    }
    return initials.uppercase(Locale.getDefault())
}

fun String.toHtmlFormat(): Spanned {
    var value = this
    value = value.replace("\\n", "<br>")
    value = value.replace("\\t", '\u0009'.toString())
    return HtmlCompat.fromHtml(value, HtmlCompat.FROM_HTML_MODE_LEGACY)
}

fun String.removeNewLine(): String {
    return when {
        contains("\\n") -> replace("\\n", "")
        contains("\n") -> replace("\n", "")
        contains("\\\n") -> replace("\\\n", "")
        else -> this
    }
}

fun String?.addHttps(): String {
    if (this == null) return ""
    if (!contains("https", ignoreCase = true)) return "https://$this"
    return this
}

fun String?.formatPath(): String {
    if (this == null) return ""
    if (this[0] == '/') return this.substring(1)
    return this
}

fun String.formatHexString(length: Int): String {
    var retentionValue = this
    return if (this.length < length) {
        for (i in 0 until length - this.length) {
            retentionValue = "0$retentionValue"
        }
        retentionValue
    } else
        this
}

fun String?.numberOnly(): String {
    return if (this == null) "0"
    else {
        var stringRet = ""
        for (char in this) {
            if (char in '0'..'9')
                stringRet += char
        }
        stringRet
    }
}

fun String.formatPhone(): String {
    val regexNumeric = Regex("[^0-9]")
    val result = regexNumeric.replace(this, "")
    if (result.take(2) == "62") {
        return this
    }
    return "62".plus(this)
}

fun String.toMD5() =
    MessageDigest
        .getInstance("MD5")
        .digest(toByteArray())
        .toHex()