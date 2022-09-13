package com.cannonballapps.transcribe

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.cannonballapps.transcribe.audiovis.WaveformSeekBarPresenter
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
                WaveformSeekBarPresenter()
            }
        }
    }
}