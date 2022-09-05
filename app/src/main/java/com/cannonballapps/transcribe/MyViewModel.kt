package com.cannonballapps.transcribe

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(
    private val waveformTransformHelper: WaveformTransformHelper,
): ViewModel() {

    init {
        waveformTransformHelper.myFun()
    }

    fun fubar() {

    }
}