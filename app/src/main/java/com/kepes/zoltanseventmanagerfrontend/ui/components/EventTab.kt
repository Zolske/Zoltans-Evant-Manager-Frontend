package com.kepes.zoltanseventmanagerfrontend.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kepes.zoltanseventmanagerfrontend.R

@Composable
fun UpcomingEventScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
            Text("Upcoming Event Screen")
    }
}


@Composable
fun SubscribedEventScreen(modifier: Modifier = Modifier) {
Box(
modifier = Modifier.fillMaxSize(),
contentAlignment = Alignment.Center
) {
Text("Subscribed Event Screen")
}
}

enum class Destination(
val route: String,
val label: String,
val icon: Int,
val contentDescription: String
) {
UpcomingEvents("upcoming", "Upcoming Events", R.drawable.event_upcoming_24px, "Tab for upcoming Events."),
SubscribedEvents("subscribed", "Subscribed Events", R.drawable.subscribe_24px, "Tab for subscribed Events."),
}

@Composable
fun AppNavHost(
navController: NavHostController,
startDestination: Destination
) {
NavHost(
navController,
startDestination = startDestination.route
) {
Destination.entries.forEach { destination ->
    composable(destination.route) {
        when (destination) {
            Destination.UpcomingEvents -> UpcomingEventScreen()
            Destination.SubscribedEvents -> SubscribedEventScreen()
        }
    }
}
}
}


//@Preview(showBackground = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventNavigationTab(
    modifier: Modifier = Modifier
) {
val navController = rememberNavController()
val startDestination = Destination.UpcomingEvents
var selectedDestination by rememberSaveable { mutableIntStateOf(startDestination.ordinal)}

Scaffold(modifier = modifier) { contentPadding ->
PrimaryTabRow(selectedTabIndex = selectedDestination, modifier = Modifier.padding(contentPadding)) {
    Destination.entries.forEachIndexed { index, destination ->
        Tab(
            selected = selectedDestination == index,
            onClick = {
                navController.navigate(route = destination.route)
                selectedDestination = index
            },
            icon = { Icon(
                        painter = painterResource(destination.icon),
                        contentDescription = destination.contentDescription)
                   },
            text = {
                Text(
                    text = destination.label,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        )
    }
}
AppNavHost(
    navController = navController,
    startDestination = startDestination)
}
}
