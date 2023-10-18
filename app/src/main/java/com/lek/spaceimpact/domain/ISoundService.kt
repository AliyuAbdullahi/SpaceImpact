package com.lek.spaceimpact.domain

interface ISoundService {
    fun init()
    fun playLongMedia(longMusic: LongMusic)
    fun playShortMedia(soundClip: SoundClip)
    fun pause()
    fun resume()
    fun release()
}