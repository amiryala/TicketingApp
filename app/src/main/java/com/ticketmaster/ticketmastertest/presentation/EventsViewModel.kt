package com.ticketmaster.ticketmastertest.presentation

import com.ticketmaster.ticketmastertest.core.BaseIntent
import com.ticketmaster.ticketmastertest.core.BaseState
import com.ticketmaster.ticketmastertest.core.BaseViewModel
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class EventsViewModel : BaseViewModel<EventsViewModel.Intent, EventsViewModel.State>(
    initialState = State(test = null)
) {
    sealed class Intent : BaseIntent {
        data object Test : Intent()
    }

    data class State(
        private val test: String?
    ) : BaseState

    override fun reduce(state: State, intent: Intent): State =
        when (intent) {
            Intent.Test -> TODO()
        }
}
