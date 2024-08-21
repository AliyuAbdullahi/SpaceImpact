package com.lek.spaceimpact.home.ui

import com.lek.spaceimpact.game.domain.ISoundService
import com.lek.spaceimpact.game.domain.LongMusic
import com.lek.spaceimpact.game.domain.SoundClip
import com.lek.spaceimpact.home.ui.model.Destination
import com.lek.spaceimpact.home.ui.model.HomeEvent
import com.lek.spaceimpact.home.ui.model.HomeState
import com.lek.spaceimpact.home.ui.model.ShowAbout
import com.lek.spaceimpact.home.ui.model.ShowHome
import com.lek.spaceimpact.home.ui.model.StartGameClicked
import com.lek.spaceimpact.home.ui.model.SystemCreated
import com.lek.spaceimpact.home.ui.model.SystemDestroyed
import com.lek.spaceimpact.home.ui.model.SystemPaused
import com.lek.spaceimpact.home.ui.model.SystemResumed
import com.lek.spaceimpact.ui.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val soundService: ISoundService
) : BaseViewModel<HomeState, HomeEvent>() {

    override fun handleEvent(event: HomeEvent) {
        when (event) {
            ShowAbout -> {
                soundService.playShortMedia(SoundClip.OPEN_MENU)
                soundService.playLongMedia(LongMusic.ABOUT_GAME)
                updateState { copy(destination = Destination.ABOUT) }
            }

            ShowHome -> updateState {
                soundService.playShortMedia(SoundClip.OPEN_MENU)
                soundService.playLongMedia(LongMusic.HOME)
                copy(destination = Destination.HOME)
            }

            StartGameClicked -> {
                soundService.playShortMedia(SoundClip.OPEN_MENU)
                soundService.stop()
            }

            SystemDestroyed -> {
                soundService.release()
                soundService.stop()
            }

            SystemPaused -> {
                soundService.stop()
            }

            SystemResumed -> {
                soundService.playLongMedia(LongMusic.HOME)
            }

            SystemCreated -> {
                soundService.release()
            }
        }
    }

    override fun initialState(): HomeState = HomeState()
    fun startSound() {
        soundService.init()
    }
}