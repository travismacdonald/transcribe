package com.cannonballapps.transcribe

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cannonballapps.transcribe.audiovis.Waveform
import com.cannonballapps.transcribe.audiovis.WaveformSeekBar
import com.cannonballapps.transcribe.ui.theme.TranscribeTheme
import linc.com.amplituda.Amplituda
import linc.com.amplituda.AmplitudaResult
import linc.com.amplituda.Compress
import linc.com.amplituda.exceptions.AmplitudaException
import linc.com.amplituda.exceptions.io.AmplitudaIOException


class MainActivity : ComponentActivity() {

    private lateinit var m: MediaPlayer

    private var amplitudeData: List<Int>? = null

    private  val mp3File: Uri by lazy {
        Uri.parse("android.resource://com.cannonballapps.transcribe/" + R.raw.countdown)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        audioExample2()
        pcmExample()

        setContent {
            TranscribeTheme {
                Box(
                    Modifier.wrapContentSize().padding(horizontal = 8.dp)
                ) {
                    WaveformSeekBar(
                        samples = amplitudeData!!,
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

    /**
     * TODO Test what file types are supported
     */
    private fun pcmExample() {
        val amplituda = Amplituda(applicationContext)
        amplituda.processAudio(R.raw.countdown, Compress.withParams(Compress.AVERAGE, 10))[{ result: AmplitudaResult<Int?> ->
            amplitudeData = result.amplitudesAsList()
        }, { exception: AmplitudaException? ->
            if (exception is AmplitudaIOException) {
                Log.d("fubar", "IO Exception!")
            }
        }]
    }
}