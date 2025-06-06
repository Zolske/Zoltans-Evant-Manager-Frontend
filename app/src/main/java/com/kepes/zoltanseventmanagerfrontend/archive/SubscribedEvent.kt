/*
package com.kepes.zoltanseventmanagerfrontend.view

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kepes.zoltanseventmanagerfrontend.data.LoggedUser
import com.kepes.zoltanseventmanagerfrontend.ui.components.EventCard
//import com.kepes.zoltanseventmanagerfrontend.ui.components.EventNavigationTab
import com.kepes.zoltanseventmanagerfrontend.ui.theme.ZoltansEventManagerFrontendTheme
import com.kepes.zoltanseventmanagerfrontend.viewModel.EventViewModel

@Composable
fun SubscribedEvent(
    userState: LoggedUser,
    eventViewModel: EventViewModel,
    context: Context,
    modifier: Modifier
) {
    val eventList by eventViewModel.eventListFlow.collectAsState()
    eventViewModel.getAllEvents(userState)

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
                actionBtn = {eventViewModel.subscribeToEvent(
                    context = context,
                    userState = userState,
                    eventId = eventList[index].idEvent
                ) })
        }

    }

}
*/
