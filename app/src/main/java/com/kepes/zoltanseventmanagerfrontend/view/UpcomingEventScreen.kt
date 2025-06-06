package com.kepes.zoltanseventmanagerfrontend.view

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kepes.zoltanseventmanagerfrontend.data.LoggedUser
import com.kepes.zoltanseventmanagerfrontend.ui.components.EventCard
import com.kepes.zoltanseventmanagerfrontend.ui.components.TopBar
import com.kepes.zoltanseventmanagerfrontend.viewModel.EventViewModel
import com.kepes.zoltanseventmanagerfrontend.viewModel.LoggedUserViewModel


@Composable
fun UpcomingEventScreen(
    eventViewModel: EventViewModel,
    loggedUserViewModel: LoggedUserViewModel,
    context: Context
) {
    val eventList by eventViewModel.eventListFlow.collectAsState()
    val loggedUser by loggedUserViewModel.loggedUserFlow.collectAsState()

    TopBar("Upcoming Events", loggedUser.pictureUrl)

    if (eventList.isNotEmpty())
        Log.i("UpcomingEventScreen", "eventList is not empty, ${eventList.first().title}, size: ${eventList.size}")

    if (eventList.isEmpty())
        eventViewModel.getAllEvents(loggedUser)
    else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(eventList.size) { index ->
                EventCard(
                    eventList[index].title,
                    eventList[index].descShort,
                    eventList[index].date,
                    eventList[index].time,
                    eventList[index].address,
                    actionBtn = {
                        eventViewModel.subscribeToEvent(
                            context = context,
                            userState = loggedUser,
                            eventId = eventList[index].idEvent
                        )
                    })
            }

        }
    }
}
