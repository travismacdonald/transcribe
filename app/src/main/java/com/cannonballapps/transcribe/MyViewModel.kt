package com.cannonballapps.transcribe

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

// TODO eventually use this as data object
data class SamplesData(
    val samples: List<Int>,
    val samplesPerSecond: Int,
)

@HiltViewModel
class MyViewModel @Inject constructor(
    private val waveformTransformHelper: WaveformTransformHelper,
    private val transcribeMediaPlayer: TranscribeMediaPlayer,
): ViewModel() {

    companion object {
        const val SAMPLES_PER_SECOND = 10
    }

    // TODO clean this up
    private val mp3File: Uri by lazy {
        Uri.parse("android.resource://com.cannonballapps.transcribe/" + R.raw.countdown)
    }

    private var mediaPlayerPoller: Job? = null

    private val _waveformsFlow = MutableStateFlow<WrappedValue<SamplesData>>(WrappedValue.Loading)
    val waveformsFlow = _waveformsFlow.asStateFlow()

    private val _mediaPositionMillisFlow = MutableStateFlow(0)
    val mediaPositionMillisFlow = _mediaPositionMillisFlow.asStateFlow()

    init {
        transcribeMediaPlayer.setMedia(mp3File)
    }

    fun fetchWaveforms(isSilentRefresh: Boolean = false) {
        viewModelScope.launch(Dispatchers.Default) {
            if (!isSilentRefresh) {
                _waveformsFlow.value = WrappedValue.Loading
            }

            val res = waveformTransformHelper.extractWaveforms(
                rawResId = R.raw.countdown,
                framesPerSecond = SAMPLES_PER_SECOND,
            )
            // TODO: error case
            _waveformsFlow.value = WrappedValue.Success(
                SamplesData(
                    samples = res,
                    samplesPerSecond = SAMPLES_PER_SECOND,
                )
            )
        }
    }

    fun playMedia() {
        transcribeMediaPlayer.playMedia()
        startMediaPlayerPoller()
    }

    private fun startMediaPlayerPoller() {
        mediaPlayerPoller?.cancel()
        mediaPlayerPoller = viewModelScope.launch(Dispatchers.Default) {
            while (isActive) {
                _mediaPositionMillisFlow.value = transcribeMediaPlayer.mediaPosition
            }
        }
    }

    private fun stopMediaPlayerPoller() {
        mediaPlayerPoller?.cancel()
    }
}
