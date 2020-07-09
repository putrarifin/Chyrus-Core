package dev.putra.libraries.core.interactor

interface ParameterizedUseCase<T, TParams> {
    suspend fun execute(params: TParams): T
}