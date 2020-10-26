package dev.putra.libraries.network.interceptor

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import dev.putra.libraries.network.exception.IoException
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

class ErrorResponseInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.proceed(chain.request())
        val errorCode = originalRequest.code
        val errorMessage = originalRequest.message
        val originBody = originalRequest.body?.string()
        val response = try {
            Gson().fromJson(originBody, NetworkResponse::class.java)
        } catch (e: Exception) {
            NetworkResponse(
                status = errorCode.toString(),
                message = errorMessage,
                code = errorCode.toString()
            )
        }
        originBody?.takeIf { response.message.isNotBlank() }?.let {
            val interceptBody = it.toResponseBody(originalRequest.body?.contentType())
            return originalRequest.newBuilder().body(interceptBody).build()
        }
        throw IoException(response?.message ?: errorMessage, response?.status?.toInt() ?: errorCode)
    }

    data class NetworkResponse(
        @SerializedName("status") val status: String,
        @SerializedName("code") val code: String,
        @SerializedName("message") val message: String
    )
}