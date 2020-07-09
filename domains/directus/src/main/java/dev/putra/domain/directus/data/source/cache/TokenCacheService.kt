package dev.putra.domain.directus.data.source.cache

import dev.putra.domain.directus.domain.model.TokenCache

/**
 * Created by Chyrus on 3/29/2020.
 */
internal interface TokenCacheService {
    fun get(): TokenCache
    suspend fun set(item: TokenCache): TokenCache
}