package com.ticketmaster.ticketmastertest.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ticketmaster.ticketmastertest.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun EventsScreen(
    viewModel: EventsViewModel = koinViewModel(),
    modifier: Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    var filterText by remember { mutableStateOf("") }
    Column {
        TextField(
            modifier = Modifier.padding(top = 48.dp),
            value = filterText,
            onValueChange = {
                filterText = it
                viewModel.invoke(
                    EventsViewModel.Intent.filterEventList(it)
                )
            }
        )
        Event(uiState.eventList)
    }
}

@Composable
fun Event(list: List<EventsViewModel.EventData>) {
    LazyColumn {
        items(list) { item ->
            Image(
                painterResource(item.image),
                contentDescription = null
            )
            Text(
                text = item.title
            )
            Text(
                text = item.body
            )
        }
    }
}