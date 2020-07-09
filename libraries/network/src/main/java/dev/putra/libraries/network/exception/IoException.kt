package dev.putra.libraries.network.exception

/**
 * Created by Chyrus on 3/29/2020.
 */

import com.google.gson.Gson
import dev.putra.commons.utilities.data.ErrorNetwork
import dev.putra.libraries.network.data.ErrorResponse
import retrofit2.HttpException
import java.io.IOException

internal class IoException(
    private val errorMessage: String?,
    private val code: Int
) : IOException(errorMessage) {
    fun fetchErrorMessage(): String = errorMessage ?: localizedMessage ?: "400"

    fun code() = code

    companion object {
        fun fetchErrorMessage(e: HttpException): String {
            val errorModel: ErrorNetwork? = try {
                val responseString =
                    e.response()?.errorBody()?.bytes()?.let { String(it) }.orEmpty()
                Gson().fromJson(responseString, ErrorResponse::class.java).error
            } catch (e: Exception) {
                null
            }
            return errorModel?.message ?: "${e.message}"
        }
    }
}