package com.cannonballapps.transcribe

// TODO: make generic
sealed class WrappedValue {
    class Success(val waveforms: List<Int>) : WrappedValue()
    class Error(val e: Throwable) : WrappedValue()
    object Loading : WrappedValue()
}
