package dev.putra.libraries.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import java.lang.Exception
import java.lang.RuntimeException
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import org.apache.http.conn.ssl.SSLSocketFactory.SSL
import java.security.SecureRandom
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory

/**
 * Created by Chyrus on 3/28/2020.
 */
val NETWORK_CLIENT = "NetworkProperty"

fun createNetworkClient(
    baseUrl: String,
    interceptors: List<Interceptor>? = emptyList(),
    debug: Boolean = false,
    isUnsafeOkHttpClient: Boolean = false
) =
    retrofitClient(baseUrl, httpClient(debug, interceptors, isUnsafeOkHttpClient))

private fun httpClient(
    debug: Boolean,
    interceptors: List<Interceptor>? = emptyList(),
    isUnsafeOkHttpClient: Boolean = false
): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)
    val clientBuilder = if (isUnsafeOkHttpClient) getUnsafeOkHttpClient() else OkHttpClient.Builder()
    clientBuilder.apply {
        if (debug) {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            addInterceptor(httpLoggingInterceptor)
        }
        interceptors?.let {
            it.forEach { interceptor ->
                addInterceptor(interceptor)
            }
        }
        readTimeout(60, TimeUnit.SECONDS)
        writeTimeout(60, TimeUnit.SECONDS)
        connectTimeout(60, TimeUnit.SECONDS)
    }
    return clientBuilder.build()
}

private fun retrofitClient(
    baseUrl: String,
    httpClient: OkHttpClient
): Retrofit =
    Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

internal fun checkOnlineStatus(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val nw = connectivityManager.activeNetwork ?: return false
        val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
        return when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            //for other device how are able to connect with Ethernet
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            //for check internet over Bluetooth
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
            else -> false
        }
    } else {
        return connectivityManager.activeNetworkInfo?.isConnected ?: false
    }
}

private fun getUnsafeOkHttpClient(): OkHttpClient.Builder {
    return try {
        val trustAllCerts: Array<TrustManager> = arrayOf(
            object : X509TrustManager {
                @Throws(CertificateException::class)
                override fun checkClientTrusted(
                    chain: Array<out X509Certificate>?,
                    authType: String?
                ) {
                }

                @Throws(CertificateException::class)
                override fun checkServerTrusted(
                    chain: Array<out X509Certificate>?,
                    authType: String?
                ) {
                    chain?.forEach { c ->
                        println("Issuer: " + c.issuerDN)
                    }
                }

                override fun getAcceptedIssuers(): Array<X509Certificate> {
                    return arrayOf()
                }
            }
        )

        val sslContext: SSLContext = SSLContext.getInstance(SSL)
        sslContext.init(null, trustAllCerts, SecureRandom())

        val sslSocketFactory: SSLSocketFactory = sslContext.socketFactory
        OkHttpClient.Builder().apply {
            sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            hostnameVerifier { _, _ ->
                true
            }
        }
    } catch (e: Exception) {
        throw RuntimeException(e)
    }
}