package dev.putra.libraries.core.interactor

interface UseCase<T> {
    operator fun invoke(): T
}
interface UseCaseSuspen<T> {
    suspend operator fun invoke(): T
}