package com.cannonballapps.transcribe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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
): ViewModel() {

    private val _waveformsFlow = MutableStateFlow<WrappedValue>(WrappedValue.Loading)
    val waveformsFlow = _waveformsFlow.asStateFlow()

    fun fetchWaveforms() {
        viewModelScope.launch(Dispatchers.IO) {
            val res = waveformTransformHelper.extractWaveforms(
                rawResId = R.raw.countdown,
                framesPerSecond = 10,
            )
            // TODO: error case
            _waveformsFlow.value = WrappedValue.Success(res)
        }
    }
}
