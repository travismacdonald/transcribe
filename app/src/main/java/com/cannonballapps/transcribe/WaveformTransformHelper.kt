package com.cannonballapps.transcribe

import androidx.annotation.RawRes
import linc.com.amplituda.Amplituda
import linc.com.amplituda.Compress
import javax.inject.Inject

class WaveformTransformHelper @Inject constructor(
    private val amplituda: Amplituda,
) {

    fun extractWaveforms(
        @RawRes rawResId: Int,
        framesPerSecond: Int,
    ): List<Int> {
        return amplituda.processAudio(
            rawResId,
            Compress.withParams(
                Compress.AVERAGE,
                framesPerSecond
            ),
        ).get().amplitudesAsList()
    }
}
