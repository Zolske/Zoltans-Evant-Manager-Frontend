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
import androidx.compose.ui.unit.dp
import com.kepes.zoltanseventmanagerfrontend.R
import com.kepes.zoltanseventmanagerfrontend.ui.components.EventCard
import com.kepes.zoltanseventmanagerfrontend.ui.components.TopBar
import com.kepes.zoltanseventmanagerfrontend.viewModel.EventViewModel
import com.kepes.zoltanseventmanagerfrontend.viewModel.LoggedUserViewModel


@Composable
fun SubscribedEventScreen(
    eventViewModel: EventViewModel,
    loggedUserViewModel: LoggedUserViewModel,
    context: Context
) {
    val subscribedEventList by eventViewModel.subscribedEventListFlow.collectAsState()
    val loggedUser by loggedUserViewModel.loggedUserFlow.collectAsState()

    TopBar("Subscribed Events", loggedUser.pictureUrl)

    if (subscribedEventList.isEmpty())
        eventViewModel.updateSubscribedEventListFlow(loggedUser, context)
    else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 100.dp, start = 24.dp, end = 24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(subscribedEventList.size) { index ->
                EventCard(
                    context = context,
                    loggedUser = loggedUser,
                    eventViewModel = eventViewModel,
                    idEvent = subscribedEventList[index].idEvent,
                    title = subscribedEventList[index].title,
                    descShort = subscribedEventList[index].descShort,
                    desc = subscribedEventList[index].desc,
                    date = subscribedEventList[index].date,
                    time = subscribedEventList[index].time,
                    address = subscribedEventList[index].address,
                    "unsubscribe",
                    R.drawable.unsubscribe_24px,
                    "unsubscribe from event",
                    actionBtn = {
                        eventViewModel.unsubscribeFromEvent(
                            context = context,
                            userState = loggedUser,
                            eventId = subscribedEventList[index].idEvent
                        )
                    })
            }

        }
    }

}