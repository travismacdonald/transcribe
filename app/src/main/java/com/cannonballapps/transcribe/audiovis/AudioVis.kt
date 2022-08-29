package com.cannonballapps.transcribe.audiovis

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
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
import com.cannonballapps.transcribe.WaveformUtil

@Composable
fun Waveform(
    samples: List<Int>,
    sampleRate: Int,
) {
    val normalizedSamples = WaveformUtil.normalizeAmplitudes(samples)

    WaveformBar(height = (normalizedSamples[0] * 10).dp, width = 20.dp, isTop = true)
}

@Composable
fun WaveformBar(
    height: Dp,
    width: Dp,
    isTop: Boolean,
) {
    /**
     * TODO these corner radius calculations are definitely not correct
     */
    Canvas(
        modifier = Modifier
            .width(width)
            .height(height),
    ) {
        val bottomCornerRadiusFloat: Float
        val topCornerRadiusFloat: Float
        val roundedCornerRadius = size.width / 2

        if (isTop) {
            bottomCornerRadiusFloat = 0f
            topCornerRadiusFloat = roundedCornerRadius
        } else {
            bottomCornerRadiusFloat = roundedCornerRadius
            topCornerRadiusFloat = 0f
        }

        val topCornerRadius = CornerRadius(
            x = topCornerRadiusFloat,
            y = topCornerRadiusFloat,
        )
        val bottomCornerRadius = CornerRadius(
            x = bottomCornerRadiusFloat,
            y = bottomCornerRadiusFloat,
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
//                    topLeft = topCornerRadius,
//                    topRight = topCornerRadius,
//                    bottomLeft = bottomCornerRadius,
//                    bottomRight = bottomCornerRadius,
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
        isTop = true,
    )
}

@Preview
@Composable
fun WaveformPreview() {
    Waveform(
        listOf(1),
        1,
    )
}