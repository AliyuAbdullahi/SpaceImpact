package com.lek.spaceimpact.home.ui.model

sealed interface HomeEvent

object ShowAbout: HomeEvent

object ShowHome: HomeEvent

object StartGameClicked: HomeEvent

object SystemPaused: HomeEvent

object SystemResumed: HomeEvent

object SystemDestroyed: HomeEvent

object SystemCreated: HomeEvent