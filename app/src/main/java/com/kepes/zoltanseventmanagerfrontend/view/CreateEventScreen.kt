package com.kepes.zoltanseventmanagerfrontend.view

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.kepes.zoltanseventmanagerfrontend.R
import com.kepes.zoltanseventmanagerfrontend.model.Event
import com.kepes.zoltanseventmanagerfrontend.ui.components.DialInputTime
import com.kepes.zoltanseventmanagerfrontend.ui.components.TopBar
import com.kepes.zoltanseventmanagerfrontend.ui.components.convertMillisToDate
import com.kepes.zoltanseventmanagerfrontend.viewModel.EventViewModel
import com.kepes.zoltanseventmanagerfrontend.viewModel.LoggedUserViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateEventScreen(
    eventViewModel: EventViewModel,
    loggedUserViewModel: LoggedUserViewModel,
    context: Context,
) {
    val subscribedEventList by eventViewModel.subscribedEventListFlow.collectAsState()
    val loggedUser by loggedUserViewModel.loggedUserFlow.collectAsState()
    var titleEdit by remember { mutableStateOf("") }
    var descShortEdit by remember { mutableStateOf("") }
    var descEdit by remember { mutableStateOf("") }
    var timeEdit by remember { mutableStateOf("00:00") }
    var locationEdit by remember { mutableStateOf("") }
    var showTimePicker by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    val selectedDate = datePickerState.selectedDateMillis?.let {
        convertMillisToDate(it)
    } ?: ""

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBar("Create Event", loggedUser.pictureUrl)

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(1.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(5.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(Modifier.size(10.dp))
                if (showTimePicker) {
                    DialInputTime(
                        time = timeEdit,
                        onConfirm = { hour, minute ->
                            // Handle confirmed time here
                            /* timeEdit = "$hour:$minute"*/
                            timeEdit = String.format("%02d:%02d", hour, minute)
                            showTimePicker = false
                        },
                        onDismiss = {
                            // Just hide the picker
                            showTimePicker = false
                        }
                    )
                }

                if (showDatePicker) {
                    Popup(
                        onDismissRequest = { showDatePicker = false },
                        alignment = Alignment.TopCenter
                    ) {
                        Column(
                            verticalArrangement = Arrangement.Top,
                            modifier = Modifier
                                .fillMaxWidth()
                                .offset(y = 64.dp)
                                .shadow(elevation = 4.dp)
                                .background(MaterialTheme.colorScheme.surface)
                                .padding(16.dp)
                        ) {
                            DatePicker(
                                state = datePickerState,
                                showModeToggle = false
                            )
                            Spacer(Modifier.size(10.dp))
                            Button(
                                onClick = { showDatePicker = !showDatePicker },
                            ) { Text("Confirm") }
                        }
                    }
                }

                OutlinedTextField(
                    value = titleEdit,
                    onValueChange = { titleEdit = it },
                    label = { Text("Title") },
                )
                Spacer(Modifier.size(10.dp))
                OutlinedTextField(
                    value = descShortEdit,
                    onValueChange = { descShortEdit = it },
                    label = { Text("Short description") },
                )
                Spacer(Modifier.size(10.dp))
                OutlinedTextField(
                    value = descEdit,
                    onValueChange = { descEdit = it },
                    label = { Text("Long description") },
                )
                Spacer(Modifier.size(10.dp))
                // Date Picker
                OutlinedTextField(
                    value = selectedDate,
                    onValueChange = { },
                    label = { Text("Event date") },
                    readOnly = true,
                    trailingIcon = {
                        IconButton(onClick = { showDatePicker = !showDatePicker }) {
                            Icon(
                                imageVector = Icons.Default.DateRange,
                                contentDescription = "Select date"
                            )
                        }
                    },
                )

                Spacer(Modifier.size(10.dp))
                // Time Picker
                OutlinedTextField(
                    value = timeEdit,
                    onValueChange = { timeEdit = it },
                    label = { Text("Time") },
                    trailingIcon = {
                        IconButton(onClick = { showTimePicker = !showTimePicker }) {
                            Icon(
                                painter = painterResource(R.drawable.time_24px),
                                contentDescription = "Select time"
                            )
                        }
                    },
                )

                Spacer(Modifier.size(10.dp))
                OutlinedTextField(
                    value = locationEdit,
                    onValueChange = { locationEdit = it },
                    label = { Text("Address") },
                )
                Spacer(Modifier.size(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = {
                            titleEdit = ""
                            descShortEdit = ""
                            descEdit = ""
                            timeEdit = ""
                            locationEdit = ""
                        }
                    ) {
                        Text("reset fields")
                    }
                    Button(
                        onClick = {
                            eventViewModel.createEvent(
                                context,
                                loggedUser,
                                Event(
                                    idEvent = 0,
                                    title = titleEdit,
                                    descShort = descShortEdit,
                                    desc = descEdit,
                                    date = selectedDate,
                                    time = timeEdit,
                                    address = locationEdit,
                                    pictureUrl = ""
                                )
                            )
                            // reset fields
                            titleEdit = ""
                            descShortEdit = ""
                            descEdit = ""
                            timeEdit = ""
                            locationEdit = ""
                        }
                    ) {
                        Text("create event")
                    }

                }
            }
        }
    }
}
