package dev.putra.domain.directus.domain.model

/**
 * Created by Chyrus on 3/29/2020.
 */
data class TokenCache(
    val token: String
) {
    companion object {
        const val KEY = "key-pref-token"
    }
}