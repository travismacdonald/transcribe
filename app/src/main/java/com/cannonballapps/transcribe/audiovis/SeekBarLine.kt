package com.cannonballapps.transcribe.audiovis

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SeekBar(
    modifier: Modifier = Modifier,
) {
    Box(modifier = Modifier.wrapContentSize()) {
        Canvas(
            modifier = modifier,
        ) {
            drawRect(
                color = Color.Red
            )
        }
    }
}


@Preview(
    name = "SeekBar",
    widthDp = 100,
    heightDp = 100,
    showBackground = true,
)
@Composable
fun SeekBarLinePreview() {
    SeekBar(
        modifier = Modifier.width(6.dp).height(40.dp)
    )
}
