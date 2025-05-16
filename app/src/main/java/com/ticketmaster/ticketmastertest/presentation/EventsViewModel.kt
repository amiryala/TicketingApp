package com.ticketmaster.ticketmastertest.presentation

import com.ticketmaster.ticketmastertest.R
import com.ticketmaster.ticketmastertest.core.BaseIntent
import com.ticketmaster.ticketmastertest.core.BaseState
import com.ticketmaster.ticketmastertest.core.BaseViewModel
import com.ticketmaster.ticketmastertest.data.EventsRepository
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class EventsViewModel(val eventsRepository: EventsRepository) : BaseViewModel<EventsViewModel.Intent, EventsViewModel.State>(
    initialState = State(listOf())
) {
    init {
        invoke(intent = Intent.loadEventList)
    }

    sealed class Intent : BaseIntent {
        data object loadEventList: Intent()
        data class filterEventList(val filter: String): Intent()
    }

    data class State(
        val eventList: List<EventData>
    ) : BaseState

    data class EventData(
        val image: Int,
        val title: String,
        val body: String
    )

    override fun reduce(state: State, intent: Intent): State {
        return when (intent) {
            Intent.loadEventList -> return state.copy(eventsRepository.getEventsDataList())
            is Intent.filterEventList -> return state.copy(eventsRepository.getFilteredList(intent.filter))
        }
    }
}
