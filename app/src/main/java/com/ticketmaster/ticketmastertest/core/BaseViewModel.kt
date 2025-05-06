package com.ticketmaster.ticketmastertest.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class BaseViewModel<Intent : BaseIntent, State : BaseState>(
    initialState: State,
    initialIntent: Intent? = null,
) : ViewModel() {
    private val intentChannel = Channel<Intent>(Channel.UNLIMITED)

    private val _uiState = MutableStateFlow(initialState)
    val uiState: StateFlow<State> =
        _uiState
            .onStart {
                initialIntent?.let { invoke(it) }
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(stopTimeoutMillis = MAX_ANR_MILLIS),
                initialValue = initialState,
            )

    init {
        viewModelScope.launch {
            handleIntents()
        }
    }

    operator fun invoke(intent: Intent) {
        intentChannel.trySend(intent)
    }

    protected abstract fun reduce(
        state: State,
        intent: Intent,
    ): State

    protected open suspend fun process(
        state: State,
        intent: Intent,
    ): Intent? = null

    private suspend fun handleIntents() {
        intentChannel.consumeAsFlow().collect { intent ->
            val previousState = uiState.value
            val newState = reduce(previousState, intent)
            _uiState.update { newState }
            val sideEffect =
                withContext(Dispatchers.IO) {
                    process(previousState, intent)
                }
            if (sideEffect != null) {
                invoke(sideEffect)
            }
        }
    }

    private companion object {
        const val MAX_ANR_MILLIS = 5_000L
    }
}
