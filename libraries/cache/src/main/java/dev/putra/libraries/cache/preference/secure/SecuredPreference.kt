package dev.putra.libraries.cache.preference.secure

/**
 * Created by Chyrus on 3/30/2020.
 */
interface SecuredPreference {
    fun set(key: String, value: Any)
    fun getInt(key: String): Int
    fun getLong(key: String): Long
    fun getString(key: String): String
    fun getBoolean(key: String): Boolean
    fun getArrayString(key: String): ArrayList<String>
    fun clearPref(key: String)
    fun clearAll()
}