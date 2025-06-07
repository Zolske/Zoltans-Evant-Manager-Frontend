package com.kepes.zoltanseventmanagerfrontend.view

import android.R.attr.top
import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kepes.zoltanseventmanagerfrontend.R
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 100.dp, start = 24.dp, end = 24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (eventList.isEmpty())
                eventViewModel.getNotSubscribedEvents(loggedUser, context)
                //eventViewModel.getAllEvents(loggedUser)
            else {
            items(eventList.size) { index ->
                EventCard(
                    eventList[index].title,
                    eventList[index].descShort,
                    eventList[index].date,
                    eventList[index].time,
                    eventList[index].address,
                    "subscribe",
                    R.drawable.subscribe_24px,
                    "subscribe to event",
                    actionBtn = {
                        eventViewModel.subscribeToEvent(
                            context = context,
                            userState = loggedUser,
                            eventId = eventList[index].idEvent
                        )
/*                        eventViewModel.resetEvents()
                        eventViewModel.getNotSubscribedEvents(loggedUser, context)*/
                    })
            }

        }
    }
}
