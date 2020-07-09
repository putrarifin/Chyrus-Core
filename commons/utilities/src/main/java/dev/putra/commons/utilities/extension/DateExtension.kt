package dev.putra.commons.utilities.extension

/**
 * Created by Chyrus on 3/28/2020.
 */

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

fun Long.formatLongToStringDate(): String {
    val date = Date(this).time
    val dateFormat = SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.getDefault())
    dateFormat.timeZone = TimeZone.getDefault()
    return dateFormat.format(date)
}

fun Long.formatExpirationDate(): String {
    val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    dateFormat.timeZone = TimeZone.getDefault()
    return dateFormat.format(Date(this))
}

fun Long.millisToMinutesSeconds(): String {
    val formatter = SimpleDateFormat("mm:ss", Locale.UK)
    val date = Date(this)
    return formatter.format(date)
}

fun Long.getTransactionDate(): String {
    val dateFormat = SimpleDateFormat("dd MMM yyyy, HH:mm aa", Locale.getDefault())
    dateFormat.timeZone = TimeZone.getDefault()
    return dateFormat.format(Date(this))
}

fun String.getLongDate(): Long? {
    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val date = sdf.parse(this)
    return date?.time
}
