package com.cannonballapps.transcribe

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class TranscribeMediaPlayer @Inject constructor(
    private val mediaPlayer: MediaPlayer,
    @ApplicationContext private val context: Context,
) {

    init {
        mediaPlayer.apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
        }
    }

    val mediaPosition: Int
            get() = mediaPlayer.currentPosition

    fun setMedia(uri: Uri) {
        mediaPlayer.setDataSource(context, uri)
        mediaPlayer.prepareAsync()
    }

    fun playMedia() {
        mediaPlayer.start()
    }
}
