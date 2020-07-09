package dev.putra.libraries.network.usecase

import dev.putra.libraries.network.data.ResultState
import dev.putra.libraries.network.extension.getError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

/**
 * Created by Chyrus on 3/28/2020.
 */
abstract class UseCase<Type : Any> {

    abstract suspend fun build(): Type

    operator fun invoke() = flow {
        emit(ResultState.Loading)
        build().also {
            emit(ResultState.Success(it))
        }
    }.flowOn(Dispatchers.IO).catch {
        it.printStackTrace()
        emit(ResultState.Failure(it.getError(), it))
    }

}