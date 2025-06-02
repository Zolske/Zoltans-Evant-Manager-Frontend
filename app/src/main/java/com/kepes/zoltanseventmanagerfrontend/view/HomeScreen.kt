package com.kepes.zoltanseventmanagerfrontend.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kepes.zoltanseventmanagerfrontend.data.LoggedUser
import com.kepes.zoltanseventmanagerfrontend.ui.theme.ZoltansEventManagerFrontendTheme
import com.kepes.zoltanseventmanagerfrontend.viewModel.EventViewModel

@Composable
fun HomeScreen(
    userState: LoggedUser,
    eventViewModel: EventViewModel,
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
            Text(eventList[index].title)
        }

        }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    ZoltansEventManagerFrontendTheme {
        /*HomeScreen(LoggedUser())*/
    }
}