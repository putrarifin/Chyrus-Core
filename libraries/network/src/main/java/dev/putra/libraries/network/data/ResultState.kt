package dev.putra.libraries.network.data

import dev.putra.commons.utilities.data.ErrorNetwork

/**
 * Created by Chyrus on 3/28/2020.
 */
sealed class ResultState<out T : Any> {
    object Loading : ResultState<Nothing>()
    data class Success<T : Any>(val result: T) : ResultState<T>()
    data class Failure(
        val error: ErrorNetwork,
        val exception: Throwable? = null
    ) : ResultState<Nothing>()
}