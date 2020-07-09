package dev.putra.libraries.network.interceptor

import dev.putra.commons.utilities.extension.toStringJson
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * Created by Chyrus on 3/29/2020.
 */
class HeaderInterceptor(private val headerMaps: () -> Map<String, String>) :
    Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(rebuildRequest(chain.request()))
    }

    private fun rebuildRequest(request: Request) = request.newBuilder().apply {
        headerMaps.invoke().forEach { map ->
            println(map.toStringJson())
            addHeader(map.key, map.value)
        }
    }.build()

}