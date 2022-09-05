package com.cannonballapps.transcribe

import android.util.Log
import androidx.annotation.RawRes
import linc.com.amplituda.Amplituda
import linc.com.amplituda.AmplitudaResult
import linc.com.amplituda.Compress
import linc.com.amplituda.exceptions.AmplitudaException
import linc.com.amplituda.exceptions.io.AmplitudaIOException
import javax.inject.Inject

class WaveformTransformHelper @Inject constructor(
    private val amplituda: Amplituda,
) {

    suspend fun extractWaveforms(
        @RawRes rawResId: Int,
        framesPerSecond: Int,
    ): List<Int> {
        var amplitudeData: List<Int>? = null
        amplituda.processAudio(
            R.raw.countdown, Compress.withParams(Compress.AVERAGE, framesPerSecond)
        )[{ result: AmplitudaResult<Int?> ->
            amplitudeData = result.amplitudesAsList()
        }, { exception: AmplitudaException? ->
            if (exception is AmplitudaIOException) {
                Log.d("fubar", "IO Exception!")
            }
        }]
    }

    fun myFun() {
        Log.d("fubar", "aoesuthaoestnuh")
    }
}