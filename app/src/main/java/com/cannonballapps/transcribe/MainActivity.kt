package com.cannonballapps.transcribe

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.SoundPool
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {

    private lateinit var m: MediaPlayer

    private  val mp3File: Uri by lazy {
        Uri.parse("android.resource://com.cannonballapps.transcribe/" + R.raw.countdown)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        audioExample2()
        pcmExample()
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
     * TODO only support mp3 atm, maybe wav files in the future
     */
    private fun pcmExample() {
        val stream = this.contentResolver.openInputStream(mp3File)
        val byteArray = stream?.readBytes()
        Log.d("fubar", "bytes : $byteArray")
    }
}