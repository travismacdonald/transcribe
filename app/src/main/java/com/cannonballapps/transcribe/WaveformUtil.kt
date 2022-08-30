package com.cannonballapps.transcribe

class WaveformUtil {

    companion object {
        fun normalizeAmplitudes(
            amplitudes: List<Int>,
            max: Float = 1f,
            min: Float = 0f,
        ): List<Float> {
            val maxValue = amplitudes.maxOrNull()?.toFloat() ?: return listOf()
            return amplitudes.map { it / maxValue }
        }


        /**
         * Normalize amplitudes within arbitrary values
         * TODO: optimize this function
         */
        fun normalizeAmplitudes2(
            samples: List<Int>,
            normalMin: Float = 0f,
            normalMax: Float = 1f,
        ): List<Float> {
            val minSample = samples.minOrNull()?.toFloat() ?: return listOf()
            val maxSample = samples.maxOrNull()?.toFloat() ?: return listOf()

            return samples.map { sample ->
                ((normalMax - normalMin) * ((sample - minSample) / (maxSample - minSample))) + normalMin
            }
        }
    }

}