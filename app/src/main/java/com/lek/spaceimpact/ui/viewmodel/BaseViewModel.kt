package com.lek.spaceimpact.ui.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

abstract class BaseViewModel<STATE, EVENT> : ViewModel() {

    abstract fun initialState(): STATE

    private val sharedFlow = MutableSharedFlow<STATE>(replay = 1)
    val stateFlow: Flow<STATE> = sharedFlow
    val stateValue: STATE
        get() = sharedFlow.replayCache.last()

    init {
       clearState()
    }

    open fun shouldUpdate(): Boolean = true

    protected fun updateState(reducer: STATE.() -> STATE) {
        if (shouldUpdate()) {
            val newState = reducer(stateValue)
            sharedFlow.tryEmit(newState)
        }
    }

    private fun clearState() {
        sharedFlow.tryEmit(initialState())
    }

    open fun handleEvent(event: EVENT) {}
}