package com.cannonballapps.transcribe.audiovis

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

    Log.d("fubar", "waveform seekbar")

    Box(
        modifier = Modifier
            .horizontalScroll(rememberScrollState())
            .border(width = 2.dp, color = Color.Black)
        ,
    ) {
        Waveform(
            samples = samplesData,
            height = height,
            waveformBarWidth = waveformBarWidth,
            spaceBetweenWaveformBars = spaceBetweenWaveformBars,
        )
        SeekBar(
            modifier = Modifier
                .height(height)
                .width(2.dp)
                .offset(x = seekBarPosition.dp)
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
