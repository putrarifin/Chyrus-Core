package dev.putra.domain.directus.di

import dev.putra.domain.directus.data.repo.DirectusRepositoryImpl
import dev.putra.domain.directus.data.repo.TokenCacheImpl
import dev.putra.domain.directus.data.source.cache.TokenCacheService
import dev.putra.domain.directus.data.source.remote.DirectusService
import dev.putra.domain.directus.domain.model.TokenCache
import dev.putra.domain.directus.domain.repo.DirectusRepository
import dev.putra.domain.directus.domain.usecase.*
import dev.putra.libraries.cache.paper.Cache
import dev.putra.libraries.network.NETWORK_CLIENT
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

/**
 * Created by Chyrus on 3/29/2020.
 */

val dataSourceModule: Module = module {
    single<TokenCacheService> { TokenCacheImpl(cache = get(named(TOKEN_CACHE))) }
    single<DirectusRepository> {
        DirectusRepositoryImpl(
            directusService = get(),
            securedPreference = get()
        )
    }
}

private const val TOKEN_CACHE = "USER_CACHE"
val cacheModule: Module = module {
    single(named(TOKEN_CACHE)) { Cache<TokenCache>() }
}

private val useCaseModules = module {
    factory { GetTokenUseCase(directusRepository = get()) }
    factory { RefreshTokenUseCase(directusRepository = get()) }
    factory { LoginUseCase(directusRepository = get()) }
    factory { UploadFileUseCase(directusRepository = get()) }
    factory { FetchFileUseCase(directusRepository = get()) }
    factory { FetchMyUserUseCase(directusRepository = get()) }
    factory { UpdateUserUseCase(directusRepository = get()) }
    factory { CreateUserUseCase(directusRepository = get()) }
}

private val networkModules = module {
    single { get<Retrofit>(named(NETWORK_CLIENT)).create(DirectusService::class.java) }
}

fun injectDirectusDomain() = loadKoinModules(
    listOf(
        networkModules,
        useCaseModules,
        dataSourceModule,
        cacheModule
    )
)