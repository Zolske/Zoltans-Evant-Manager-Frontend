package com.kepes.zoltanseventmanagerfrontend.ui.components

import android.R.attr.start
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.Popup
import com.kepes.zoltanseventmanagerfrontend.R
import com.kepes.zoltanseventmanagerfrontend.data.LoggedUser
import com.kepes.zoltanseventmanagerfrontend.model.Event
import com.kepes.zoltanseventmanagerfrontend.viewModel.EventViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditModal(
    context: Context,
    loggedUser: LoggedUser,
    eventViewModel: EventViewModel,
    idEvent: Long,
    title: String,
    descShort: String,
    desc: String,
    date: String,
    time: String,
    address: String,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
) {
    var titleEdit by remember { mutableStateOf(title) }
    var descShortEdit by remember { mutableStateOf(descShort) }
    var descEdit by remember { mutableStateOf(desc) }
    var timeEdit by remember { mutableStateOf(time.substring(0, 5)) }
    var locationEdit by remember { mutableStateOf(address) }
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    val selectedDate = datePickerState.selectedDateMillis?.let {
        convertMillisToDate(it)
    } ?: date
    var showTimePicker by remember { mutableStateOf(false) }

    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(1.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(1.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                if (showTimePicker) {
                    DialInputTime(
                        time = timeEdit,
                        onConfirm = { hour, minute ->
                            // Handle confirmed time here
                            timeEdit = "$hour:$minute"
                            showTimePicker = false
                        },
                        onDismiss = {
                            // Just hide the picker
                            showTimePicker = false
                        }
                    )
                }
                Spacer(Modifier.size(10.dp))
                Text(
                    text = "Edit Event:",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                )
                Spacer(Modifier.size(10.dp))
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
                if (showDatePicker) {
                    Popup(
                        onDismissRequest = { showDatePicker = false },
                        alignment = Alignment.TopStart
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .offset(y = 64.dp)
                                .shadow(elevation = 4.dp)
                                .background(MaterialTheme.colorScheme.surface)
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
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
                        onClick = { onDismissRequest() }
                    ) { Text("Dismiss") }

                    Button(
                        onClick = {
                            eventViewModel.updateEvent(
                                context,
                                loggedUser,
                                idEvent,
                                Event(
                                    idEvent,
                                    titleEdit,
                                    descShortEdit,
                                    descEdit,
                                    selectedDate,
                                    timeEdit,
                                    locationEdit,
                                    ""
                                )
                            )
                            onConfirmation()
                        }
                    ) { Text("update Event") }
                }
            }
        }
    }
}

fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return formatter.format(Date(millis))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialInputTime(
    time: String,
    onConfirm: (hour: Int, minute: Int) -> Unit,
    onDismiss: () -> Unit,
) {
    val timePickerState = rememberTimePickerState(
        initialHour = time.substring(0, 2).toInt(),
        initialMinute = time.substring(3, 5).toInt(),
        is24Hour = true
    )

    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TimePicker(
            state = timePickerState,
            modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 2.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = {
                    onDismiss()
                }
            ) {
                Text("Dismiss")
            }

            Button(
                onClick = {
                    onConfirm(timePickerState.hour, timePickerState.minute)
                }
            ) {
                Text("Confirm")
            }
        }
    }
}
