package dev.putra.domain.utangku.di

import dev.putra.domain.utangku.data.repo.UtangkuRepositoryImpl
import dev.putra.domain.utangku.data.source.remote.UtangkuService
import dev.putra.domain.utangku.domain.repo.UtangkuRepository
import dev.putra.domain.utangku.domain.usecase.GetListUtangUseCase
import dev.putra.libraries.network.NETWORK_CLIENT
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

/**
 * Created by Chyrus on 3/28/2020.
 */

private val domainModules = module {
    single<UtangkuRepository> { UtangkuRepositoryImpl(utangkuService = get()) }
}
private val networkModules = module {
    single { get<Retrofit>(named(NETWORK_CLIENT)).create(UtangkuService::class.java) }
}

private val useCaseModules = module {
    factory { GetListUtangUseCase(utangkuRepository = get()) }
}

fun injectUtangkuDomain() = loadKoinModules(listOf(networkModules, useCaseModules, domainModules))