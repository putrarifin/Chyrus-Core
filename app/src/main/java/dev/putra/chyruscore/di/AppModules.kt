package dev.putra.chyruscore.di

import android.content.Context
import dev.putra.chyruscore.BuildConfig
import dev.putra.chyruscore.R
import dev.putra.chyruscore.helper.NavManager
import dev.putra.chyruscore.ui.navigator.MainViewModel
import dev.putra.commons.ui.extension.stringAt
import dev.putra.libraries.cache.preference.secure.SecuredPreference
import dev.putra.libraries.cache.preference.secure.SecuredPreferenceImpl
import dev.putra.libraries.network.NETWORK_CLIENT
import dev.putra.libraries.network.createNetworkClient
import dev.putra.libraries.network.interceptor.ConnectionCheckInterceptor
import dev.putra.libraries.network.interceptor.ErrorResponseInterceptor
import dev.putra.libraries.network.interceptor.HeaderInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModules = module {
    viewModel { MainViewModel() }
    single { NavManager() }
}

val networkModules = module {
    single(named(NETWORK_CLIENT)) {
        createNetworkClient(
            baseUrl = "BASE_URL_HERE",
            interceptors = listOf(
                HeaderInterceptor {
                    mapOf(
                        "Content-Type" to "application/json",
                        "Authorization" to "Bearer TOKEN_HERE"
                    )
                },
                ConnectionCheckInterceptor(
                    androidContext(),
                    androidContext().stringAt(R.string.no_internet_connection_title),
                    androidContext().stringAt(R.string.no_internet_connection_desc)
                ), ErrorResponseInterceptor()
            ),
            debug = BuildConfig.DEBUG
        )
    }
}

val cacheModules = module {
    single<SecuredPreference> {
        SecuredPreferenceImpl(
            preference = get(),
            keyToken = "Native.secretKey()" //your secretKeyPref
        )
    }
    single {
        androidContext().getSharedPreferences(
            "Native.secretKeyNamePref()", //your secretNamePref
            Context.MODE_PRIVATE
        )
    }
}

fun injectApp() = loadKoinModules(listOf(appModules, networkModules, cacheModules))
