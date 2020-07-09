package dev.putra.libraries.cache.preference.type

import android.content.SharedPreferences
import dev.putra.libraries.cache.preference.Preference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class BooleanPreference(
    keyFlow: Flow<String>,
    private val sharedPreferences: SharedPreferences,
    private val key: String,
    private val defaultValue: Boolean
) : Preference<Boolean>(keyFlow, sharedPreferences, key) {

    override fun get() = sharedPreferences.getBoolean(key, defaultValue)

    override fun set(value: Boolean) = sharedPreferences.edit().putBoolean(key, value).apply()

    override suspend fun setAndCommit(value: Boolean) =
            withContext(Dispatchers.IO) { sharedPreferences.edit().putBoolean(key, value).commit() }
}