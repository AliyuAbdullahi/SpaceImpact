package com.lek.spaceimpact.game.domain

interface ISoundService {
    fun init()
    fun playLongMedia(longMusic: LongMusic)
    fun playShortMedia(soundClip: SoundClip)
    fun pause()
    fun resume()
    fun release()
    fun stop()
}