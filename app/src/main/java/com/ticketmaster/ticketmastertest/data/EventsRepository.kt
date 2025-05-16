package com.ticketmaster.ticketmastertest.data

import com.ticketmaster.ticketmastertest.R
import com.ticketmaster.ticketmastertest.presentation.EventsViewModel.EventData
import org.koin.core.annotation.Factory

@Factory
class EventsRepository {
    fun getEventsDataList(): List<EventData> {
        val list = listOf(
            EventData(R.drawable.test_event_image_1, "Test Event 1", "Test Event 1"),
            EventData(R.drawable.test_event_image_2, "Test Event 2", "Test Event 2"),
            EventData(R.drawable.test_event_image_3, "Test Event 3", "Test Event 3")
        )
        return list
    }

    fun getFilteredList(filter: String): List<EventData> {
        val list = listOf(
            EventData(R.drawable.test_event_image_1, "Test Event 1", "Test Event 1"),
            EventData(R.drawable.test_event_image_2, "Test Event 2", "Test Event 2"),
            EventData(R.drawable.test_event_image_3, "Test Event 3", "Test Event 3")
        )
        val filteredList: List<EventData> = list.filter {
            it.title.contains(filter)
        }
        return  filteredList
    }
}
