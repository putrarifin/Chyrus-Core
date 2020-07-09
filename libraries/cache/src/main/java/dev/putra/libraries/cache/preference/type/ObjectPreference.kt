package dev.putra.libraries.cache.preference.type

import android.content.SharedPreferences
import dev.putra.libraries.cache.preference.Preference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class ObjectPreference<T>(
    keyFlow: Flow<String>,
    private val sharedPreferences: SharedPreferences,
    private val key: String,
    private val serializer: Serializer<T>,
    private val defaultValue: T
) : Preference<T>(keyFlow, sharedPreferences, key) {

    interface Serializer<T> {

        fun deserialize(serialized: String): T

        fun serialize(value: T): String
    }

    override fun get() =
            sharedPreferences.getString(key, null)?.let { serializer.deserialize(it) } ?: defaultValue

    override fun set(value: T) =
            sharedPreferences.edit().putString(key, serializer.serialize(value)).apply()

    override suspend fun setAndCommit(value: T) =
            withContext(Dispatchers.IO) { sharedPreferences.edit().putString(key, serializer.serialize(value)).commit() }
}