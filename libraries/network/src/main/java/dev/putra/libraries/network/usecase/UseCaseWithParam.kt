package dev.putra.libraries.network.usecase

import dev.putra.libraries.network.data.ResultState
import dev.putra.libraries.network.extension.getError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * Created by Chyrus on 3/28/2020.
 */
abstract class UseCaseWithParam<Type : Any, in Params> {

    abstract suspend fun build(params: Params): Type

    operator fun invoke(params: Params) = flow {
        emit(ResultState.Loading)
        build(params).also {
            emit(ResultState.Success(it))
        }
    }.flowOn(Dispatchers.IO).catch {
        it.printStackTrace()
        emit(ResultState.Failure(it.getError(), it))
    }

}