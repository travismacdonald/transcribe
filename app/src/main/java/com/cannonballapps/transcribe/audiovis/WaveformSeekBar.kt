package com.cannonballapps.transcribe.audiovis

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.cannonballapps.transcribe.SamplesData

/**
 * TODO
 * change some of these parameters to use Modifier (e.g. `height: Dp`)
 */
@Composable
fun WaveformSeekBar(
    samplesData: SamplesData,
    seekBarPosition: Int,
    height: Dp,
    waveformBarWidth: Dp = 20.dp,
    spaceBetweenWaveformBars: Dp = 4.dp,
) {
    Box(modifier = Modifier.horizontalScroll(rememberScrollState())) {
        Waveform(
            samples = samplesData.samples,
            height = height,
            waveformBarWidth = waveformBarWidth,
            spaceBetweenWaveformBars = spaceBetweenWaveformBars,
        )
        SeekBar(
            modifier = Modifier.height(height).width(4.dp) // TODO figure out how to position this
        )
    }
}


@Preview
@Composable
fun WaveformSeekBarPreview() {
    WaveformSeekBar(
        samplesData = SamplesData(
            samples = listOf(0, 3, 1, 2, 4, 7, 8, 2),
            samplesPerSecond = 10,
        ),
        seekBarPosition = 4,
        height = 164.dp,
        waveformBarWidth = 6.dp,
        spaceBetweenWaveformBars = 2.dp,
    )
}
