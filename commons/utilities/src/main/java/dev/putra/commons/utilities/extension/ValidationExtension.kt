package dev.putra.commons.utilities.extension

/**
 * Created by Chyrus on 3/28/2020.
 */

import android.text.Editable
import android.text.TextUtils

fun String?.splitPhoneNumber(): String {
    val array = this?.chunked(3)
    return array?.joinToString(separator = "-") ?: ""
}

fun String?.splitTokenCode(): String {
    val array = this?.chunked(3)
    return array?.joinToString(separator = "  ") ?: ""
}

fun String.isNumber(): Boolean {
    try {
        this.toDouble()
    } catch (nfe: NumberFormatException) {
        return false
    } catch (nfe: NullPointerException) {
        return false
    }
    return true
}

fun String.isValidPhoneNumber(): Boolean {
    return android.util.Patterns.PHONE.matcher(this).matches()
}

fun String.isValidEmail(): Boolean {
    return !TextUtils.isEmpty(this) && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun Editable.isValidAlphaNumericForm(): Boolean {
    return this.isNotBlank() && this.matches("^[a-zA-Z0-9 ().,-]*\$".toRegex())
}

fun CharSequence.isValidAlphaNumericForm(): Boolean {
    return this.isNotBlank() && this.matches("^[a-zA-Z0-9 ().,-]*\$".toRegex())
}

fun String?.isValidUserNameEmail(): Boolean {
    this?.forEach { char ->
        if (!(char in 'A'..'Z'
                    || char in 'a'..'z'
                    || char in '0'..'9'
                    || char == '+'
                    || char == '.'
                    || char == '_'
                    || char == '%'
                    || char == '-')
        ) {
            return false
        }
    }
    return true
}

fun String?.isValidProviderEmail(): Boolean {
    this?.forEach { char ->
        if (!(char in 'A'..'Z'
                    || char in 'a'..'z'
                    || char in '0'..'9')
        ) {
            return false
        }
    }
    return true
}

fun String?.checkValidEmail(): Boolean {
    val emailsPart = this?.split("@")
    if (emailsPart?.size == 2) {
        val isValidUserName = emailsPart[0].isValidUserNameEmail()
        val isValidProviderEmail: Boolean
        var isValidDomainEmail = false
        val providersPart = emailsPart[1].split(".")
        if (providersPart.size >= 2) {
            with(providersPart[0]) {
                isValidProviderEmail = this.isValidProviderEmail() && (this.length in 1..10)
            }
            for (i in 1 until providersPart.size) {
                with(providersPart[i]) {
                    isValidDomainEmail = this.isValidProviderEmail() && this.isNotEmpty() && (this.length in 1..5)
                }
            }
            return isValidUserName && isValidProviderEmail && isValidDomainEmail
        } else if (providersPart.size == 1) {
            with(providersPart[0]) {
                isValidProviderEmail = this.isValidProviderEmail() && (this.length in 1..10) && this.endsWith(".")
            }
            return isValidUserName && isValidProviderEmail
        }
    } else if (emailsPart?.size == 1) {
        with(emailsPart[0]) {
            return this.isValidUserNameEmail() && this.endsWith("@")
        }
    }
    return false
}

fun String?.checkIsValidPin(): Boolean {
    val textArray = this?.toCharArray()
    return !textArray.isAscending() && !textArray.isDescending() && !textArray.isSameValue()
}

fun CharArray?.isAscending(): Boolean {
    var isArrayAscending = true
    if (this != null) {
        for (i in this.indices) {
            if (i == this.size - 1) {
                break
            }
            if (this[i] != this[i + 1] - 1) {
                isArrayAscending = false
                break
            }
        }
    }
    return isArrayAscending
}

fun CharArray?.isDescending(): Boolean {
    var isArrayDescending = true
    if (this != null) {
        for (i in this.indices) {
            if (i == this.size - 1) {
                break
            }
            if (this[i] != this[i + 1] + 1) {
                isArrayDescending = false
                break
            }
        }
    }
    return isArrayDescending
}

fun CharArray?.isSameValue(): Boolean {
    var isSameValue = true
    if (this != null) {
        for (i in this.indices) {
            if (i == this.size - 1) {
                break
            }
            if (this[i] != this[i + 1]) {
                isSameValue = false
                break
            }
        }
    }
    return isSameValue
}