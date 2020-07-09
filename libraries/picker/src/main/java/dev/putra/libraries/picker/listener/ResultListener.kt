package dev.putra.libraries.picker.listener

internal interface ResultListener<T> {

    fun onResult(t: T?)
}
