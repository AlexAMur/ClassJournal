package com.catshome.classJournal.screens.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<State: Any, Action, Event> ( installState :State):ViewModel() {
    private val _viewStates = MutableStateFlow(installState)
    private val _viewActions = MutableSharedFlow<Action?>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    fun viewState(): StateFlow <State> =_viewStates.asStateFlow()
    fun viewActions(): SharedFlow <Action?> =_viewActions.asSharedFlow()
    protected var viewState: State
        get() = _viewStates.value
        set(value) {
            _viewStates.value = value
        }

    protected var viewAction: Action?
        get() = _viewActions.replayCache.last()
        set(value) {
            _viewActions.tryEmit(value)
        }
    public abstract fun obtainEvent(viewEvent: Event)

    fun clearAction() {
        viewAction = null
    }

    /**
     * Convenient method to perform work in [viewModelScope] scope.
     */
    protected fun withViewModelScope(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch(block = block)
    }
}
