package com.cannonballapps.transcribe

sealed class WrappedValue<out T> {
    class Success<T>(val value: T) : WrappedValue<T>()
    class Error(val e: Throwable) : WrappedValue<Nothing>()
    object Loading : WrappedValue<Nothing>()
}
