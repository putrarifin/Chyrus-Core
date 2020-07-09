package dev.putra.commons.utilities.extension

/**
 * Created by Chyrus on 3/28/2020.
 */

import android.os.Parcelable
import com.google.gson.Gson
import com.google.gson.JsonParseException
import java.lang.reflect.Type

inline fun <reified T : Any?> String.fromJson(newInstance : () -> T): T {
    return try {
        Gson().fromJson(this, T::class.java)
    } catch (exception: JsonParseException) {
        newInstance()
    }
}

inline fun <reified T : Any?> String.fromJson(defaultValue : T): T {
    return try {
        Gson().fromJson(this, T::class.java)
    } catch (exception: JsonParseException) {
        defaultValue
    }
}

fun <A> String.fromJson(type: Type): List<A> {
    return try {
        Gson().fromJson(this, type)
    } catch (e: Exception) {
        listOf()
    }
}

fun Parcelable.toStringJson(): String {
    return Gson().toJson(this)
}

fun <T> T.toStringJson(): String {
    return Gson().toJson(this)
}