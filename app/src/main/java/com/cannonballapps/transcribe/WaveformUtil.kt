package com.cannonballapps.transcribe

class WaveformUtil {

    companion object {
        fun normalizeAmplitudes(amplitudes: List<Int>): List<Float> {
            val max = amplitudes.maxOrNull()?.toFloat() ?: return listOf()
            return amplitudes.map { it / max }
        }
    }

}