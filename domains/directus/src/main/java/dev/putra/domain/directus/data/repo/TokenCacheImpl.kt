package dev.putra.domain.directus.data.repo

import dev.putra.domain.directus.data.source.cache.TokenCacheService
import dev.putra.domain.directus.domain.model.TokenCache
import dev.putra.libraries.cache.paper.Cache

/**
 * Created by Chyrus on 3/29/2020.
 */
class TokenCacheImpl(private val cache: Cache<TokenCache>) : TokenCacheService {

    private val key = "TokenKey"

    override fun get(): TokenCache = cache.load(key)

    override suspend fun set(item: TokenCache): TokenCache = cache.save(key, item)

}