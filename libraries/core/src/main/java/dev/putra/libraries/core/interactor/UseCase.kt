package dev.putra.libraries.core.interactor

interface UseCase<T> {
    operator fun invoke(): T
}

interface UseCaseSuspend<T> {
    suspend operator fun invoke(): T
}

interface UseCaseParam<T, in Params> {
    suspend operator fun invoke(params: Params): T
}