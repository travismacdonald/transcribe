package com.cannonballapps.transcribe

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
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
import com.cannonballapps.transcribe.audiovis.WaveformSeekBar
import com.cannonballapps.transcribe.ui.theme.TranscribeTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MyViewModel by viewModels()

    private lateinit var m: MediaPlayer

    private val mp3File: Uri by lazy {
        Uri.parse("android.resource://com.cannonballapps.transcribe/" + R.raw.countdown)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.fetchWaveforms()
        audioExample2()

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

    override fun onDestroy() {
        super.onDestroy()
        m.release()
    }

    private fun audioExample2() {
        m = MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
            setDataSource(applicationContext, mp3File)
            setOnPreparedListener { it.start() }
            prepareAsync()
        }
    }
}