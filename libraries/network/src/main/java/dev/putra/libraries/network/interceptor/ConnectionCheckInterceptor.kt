package dev.putra.libraries.network.interceptor

/**
 * Created by Chyrus on 3/29/2020.
 */
import android.content.Context
import dev.putra.libraries.network.checkOnlineStatus
import dev.putra.libraries.network.exception.IoException
import okhttp3.Interceptor
import okhttp3.Response

class ConnectionCheckInterceptor(
    private val context: Context,
    private val noConnectionMessage: String,
    private val genericError: String
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return if (!checkOnlineStatus(context)) {
            throw IoException(noConnectionMessage, 408)
        } else {
            with(chain.proceed(chain.request())) {
                when (code) {
                    408 -> throw IoException(genericError, code)
                    else -> this
                }
            }
        }
    }
}