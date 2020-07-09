package dev.putra.libraries.network.extension

/**
 * Created by Chyrus on 3/29/2020.
 */
import com.google.gson.Gson
import dev.putra.commons.utilities.data.ErrorNetwork
import dev.putra.libraries.network.data.ErrorResponse
import dev.putra.libraries.network.exception.IoException
import retrofit2.HttpException

fun Throwable.getError(): ErrorNetwork =
    ErrorNetwork(
        message = when (this) {
            is IoException -> fetchErrorMessage()
            is HttpException -> fetchErrorMessage()
            else -> "something went wrong!"
        },
        code = when (this) {
            is IoException -> code()
            is HttpException -> code()
            else -> 500
        }
    )


fun HttpException.fetchErrorMessage(): String {
    val errorModel: ErrorNetwork? = try {
        val responseString =
            response()?.errorBody()?.bytes()?.let { String(it) }.orEmpty()
        Gson().fromJson(responseString, ErrorResponse::class.java).error
    } catch (e: Exception) {
        null
    }
    return errorModel?.message ?: "$message"
}