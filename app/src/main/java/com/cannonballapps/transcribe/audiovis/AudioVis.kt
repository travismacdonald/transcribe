package com.cannonballapps.transcribe.audiovis

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.cannonballapps.transcribe.WrappedValue
import com.cannonballapps.transcribe.WaveformUtil
import kotlinx.coroutines.flow.StateFlow

/**
 * TODO
 * change some of these parameters to use Modifier (e.g. `height: Dp`)
 */
@Composable
fun WaveformSeekBar(
    samplesFlow: StateFlow<WrappedValue>,
    height: Dp,
    waveformBarWidth: Dp = 20.dp,
    spaceBetweenWaveformBars: Dp = 4.dp,
) {
    val s = samplesFlow.collectAsState()

    (s.value as? WrappedValue.Success)?.let {
        Box(modifier = Modifier.horizontalScroll(rememberScrollState())) {
            Waveform(
                samples = it.waveforms,
                height = height,
                waveformBarWidth = waveformBarWidth,
                spaceBetweenWaveformBars = spaceBetweenWaveformBars,
            )
        }
    }
    (s.value as? WrappedValue.Loading)?.let {
        Box(modifier = Modifier.horizontalScroll(rememberScrollState())) {
            Text(text = "Loading")
        }
    }
}

/**
 * TODO magic number defaults
 * Maybe add some sensible defaults
 */
@Composable
fun Waveform(
    samples: List<Int>,
    height: Dp,
    waveformBarWidth: Dp = 20.dp,
    spaceBetweenWaveformBars: Dp = 4.dp,
    // TODO on seek events
) {
    val normalizedSamples: List<Float> = WaveformUtil.normalizeAmplitudes2(
        samples,
        normalMin = 0.1f,
        normalMax = 1.0f,
    )

    Row(verticalAlignment = Alignment.CenterVertically) {
        if (samples.isNotEmpty()) {
            WaveformBar(
                height = height.times(normalizedSamples[0]),
                width = waveformBarWidth,
            )
            if (samples.size > 1) {
                for (sample in normalizedSamples.subList(fromIndex = 1, toIndex = samples.size)) {
                    Spacer(modifier = Modifier.size(spaceBetweenWaveformBars))
                    WaveformBar(
                        height = height.times(sample),
                        width = waveformBarWidth,
                    )
                }
            }
        }
    }
}

@Composable
fun WaveformBar(
    height: Dp,
    width: Dp,
) {
    /**
     * TODO these corner radius calculations might not be correct
     */
    Canvas(
        modifier = Modifier.size(
            width = width,
            height = height,
        ),
    ) {
        val roundedCornerRadius = size.width / 2
        val cornerRadius = CornerRadius(
            x = roundedCornerRadius,
            y = roundedCornerRadius,
        )

        val path = Path().apply {
            addRoundRect(
                RoundRect(
                    rect = Rect(
                        offset = Offset.Zero,
                        size = Size(
                            width = size.width,
                            height = size.height
                        ),
                    ),
                    cornerRadius = cornerRadius
                )
            )
        }
        drawPath(
            path = path,
            color = Color.Blue,
        )
    }
}


/**
 * Composable previews
 */

@Preview
@Composable
fun WaveformBarPreview() {
    WaveformBar(
        height = 20.dp,
        width = 10.dp,
    )
}

@Preview
@Composable
fun WaveformPreview() {
    Waveform(
        samples = listOf(1, 2, 0, 3, 4, 1),
        height = 200.dp,
        waveformBarWidth = 20.dp,
    )
}