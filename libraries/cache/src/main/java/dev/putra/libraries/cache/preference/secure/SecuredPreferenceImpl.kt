package dev.putra.libraries.cache.preference.secure

import android.content.SharedPreferences
import dev.putra.libraries.security.SecurityCrypto
import org.json.JSONArray
import org.json.JSONException

/**
 * Created by Chyrus on 3/30/2020.
 */
class SecuredPreferenceImpl(val preference: SharedPreferences, private val keyToken: String) :
    SecuredPreference {

    private val security by lazy {
        SecurityCrypto
    }

    private fun chipper(key: String): String =
        preference.getString(key, null) ?: ""

    override fun set(key: String, value: Any) {
        preference.edit().apply {
            when (value) {
                is String -> putString(key, security.aesEncrypt(value, keyToken))
                is Boolean -> putString(key, security.aesEncrypt(value.toString(), keyToken))
                is Int -> putString(key, security.aesEncrypt(value.toString(), keyToken))
                is Long -> putString(key, security.aesEncrypt(value.toString(), keyToken))
                is ArrayList<*> -> putString(
                    key,
                    security.aesEncrypt(JSONArray(value).toString(), keyToken)
                )
            }
            apply()
        }
    }

    override fun getInt(key: String): Int {
        val chipper = chipper(key)
        return if (chipper.isNotEmpty()) {
            security.aesDecrypt(chipper, keyToken).toInt()
        } else {
            -1
        }
    }

    override fun getLong(key: String): Long {
        val chipper = chipper(key)
        return if (chipper.isNotEmpty()) {
            security.aesDecrypt(chipper, keyToken).toLong()
        } else {
            -1
        }
    }

    override fun getString(key: String): String {
        val chipper = chipper(key)
        return if (chipper.isNotEmpty()) {
            security.aesDecrypt(chipper, keyToken)
        } else {
            ""
        }
    }

    override fun getBoolean(key: String): Boolean {
        val chipper = chipper(key)
        return if (chipper.isNotEmpty()) {
            security.aesDecrypt(chipper, keyToken).toBoolean()
        } else {
            false
        }
    }

    override fun getArrayString(key: String): ArrayList<String> {
        val array = ArrayList<String>()
        val chipper = chipper(key)
        val jsonArray = if (chipper.isNotEmpty()) {
            security.aesDecrypt(chipper, keyToken)
        } else {
            ""
        }
        if (jsonArray.matches(key.toRegex())) {
            return array
        } else {
            try {
                val jArray = JSONArray(jsonArray)
                (0 until jArray.length()).mapTo(array) { jArray.getString(it) }
                return array
            } catch (e: JSONException) {
            }
        }
        return array
    }

    override fun clearPref(key: String) {
        preference.edit()?.apply {
            remove(key)
            apply()
        }
    }
}