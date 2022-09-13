package com.cannonballapps.transcribe

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.cannonballapps.transcribe.audiovis.WaveformSeekBar
import com.cannonballapps.transcribe.ui.theme.TranscribeTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.mediaPositionMillisFlow
            .onEach { Log.d("fubar", "mediaPosition: $it") }
            .launchIn(lifecycleScope)

        viewModel.fetchWaveforms()
        viewModel.playMedia()

        setContent {
            TranscribeTheme {
                Box(
                    Modifier.wrapContentSize().padding(horizontal = 8.dp)
                ) {
                    WaveformSeekBar(
                        samplesFlow = viewModel.waveformsFlow,
                        height = 200.dp,
                        waveformBarWidth = 6.dp,
                        spaceBetweenWaveformBars = 2.dp,
                    )

                }
            }
        }
    }
}