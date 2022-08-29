package com.cannonballapps.transcribe

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import linc.com.amplituda.Amplituda
import linc.com.amplituda.AmplitudaResult
import linc.com.amplituda.exceptions.AmplitudaException
import linc.com.amplituda.exceptions.io.AmplitudaIOException


class MainActivity : ComponentActivity() {

    private lateinit var m: MediaPlayer

    private  val mp3File: Uri by lazy {
        Uri.parse("android.resource://com.cannonballapps.transcribe/" + R.raw.countdown)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        audioExample2()
        pcmExample2()
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


    private fun pcmExample2() {
        val amplituda = Amplituda(applicationContext)
        Log.d("fubar", "calling processAudio")
        amplituda.processAudio(R.raw.countdown)
        Log.d("fubar", "made it here")
    }

    /**
     * TODO only support mp3 atm, maybe wav files in the future
     */
    private fun pcmExample() {
        val amplituda = Amplituda(applicationContext)
        amplituda.processAudio(R.raw.countdown)[{ result: AmplitudaResult<Int?> ->
            val amplitudesData = result.amplitudesAsList()
            val amplitudesForFirstSecond =
                result.amplitudesForSecond(1)
            val duration = result.getAudioDuration(AmplitudaResult.DurationUnit.SECONDS)
            val source = result.audioSource
            val sourceType = result.inputAudioType
        }, { exception: AmplitudaException? ->
            if (exception is AmplitudaIOException) {
                Log.d("fubar", "IO Exception!")
            }
        }]
    }
}