package com.lek.spaceimpact.game.data

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.SoundPool
import android.util.Log
import com.lek.spaceimpact.game.domain.ISoundService
import com.lek.spaceimpact.game.domain.LongMusic
import com.lek.spaceimpact.game.domain.SoundClip

class SoundService(private val context: Context) : ISoundService {

    private lateinit var soundPool: SoundPool

    private var mediaPlayer: MediaPlayer? = MediaPlayer()

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
        try {
            mediaPlayer?.stop()
            mediaPlayer = MediaPlayer.create(context, longMusic.resource)
            mediaPlayer?.start()
        } catch (error: Exception) {
            mediaPlayer = MediaPlayer.create(context, longMusic.resource)
            mediaPlayer?.start()
            Log.e("ERROR", error.stackTraceToString())
        }
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

    override fun stop() {
        mediaPlayer?.stop()
    }

    override fun pause() {
        try {
            mediaPlayer?.pause()
        } catch (error: Exception) {
            Log.e("ERROR", error.stackTraceToString())
        }
    }

    override fun resume() {
        try {
            mediaPlayer?.start()
        } catch (error: Exception) {
            Log.e("ERROR", error.stackTraceToString())
        }
    }

    override fun release() {
        try {
            mediaPlayer?.stop()
            mediaPlayer?.release()
            mediaPlayer = null
        }catch (error: Exception) {
            Log.e("ERROR", error.stackTraceToString())
        }
    }
}