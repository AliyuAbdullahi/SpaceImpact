package com.lek.spaceimpact.data

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.SoundPool
import android.util.Log
import com.lek.spaceimpact.domain.ISoundService
import com.lek.spaceimpact.domain.LongMusic
import com.lek.spaceimpact.domain.SoundClip

class SoundService(private val context: Context) : ISoundService {
    private lateinit var audioAttributes: AudioAttributes
    private lateinit var soundPool: SoundPool

    private var mediaPlayer: MediaPlayer = MediaPlayer()

    private val soundIds = mutableMapOf<SoundClip, Int>()

    override fun init() {
        soundPool = SoundPool.Builder()
            .setMaxStreams(3).build()

        SoundClip.values().forEach {
            val id = soundPool.load(context, it.resource, 1)
            soundIds[it] = id
        }
    }

    override fun playLongMedia(longMusic: LongMusic) {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
            mediaPlayer.release()
        }

        mediaPlayer = MediaPlayer.create(context, longMusic.resource)
        mediaPlayer.isLooping = true
        mediaPlayer.start()
    }

    override fun playShortMedia(soundClip: SoundClip) {
        soundIds[soundClip]?.let {
            try {
                soundPool.play(it, 1F, 1F, 0, 0, 1F)
            } catch (error: Exception) {
                Log.e("ERROR", error.toString())
            }
        }
    }
}