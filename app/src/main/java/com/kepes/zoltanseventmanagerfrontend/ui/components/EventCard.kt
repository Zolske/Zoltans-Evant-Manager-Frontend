package com.kepes.zoltanseventmanagerfrontend.ui.components

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kepes.zoltanseventmanagerfrontend.ui.theme.ZoltansEventManagerFrontendTheme
import com.kepes.zoltanseventmanagerfrontend.R
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.kepes.zoltanseventmanagerfrontend.data.LoggedUser
import com.kepes.zoltanseventmanagerfrontend.ui.components.EditModal
import com.kepes.zoltanseventmanagerfrontend.viewModel.EventViewModel


@Composable
fun EventCard(
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
    actionBtnName: String,
    actionBtnIcon: Int,
    actionBtnDes: String,
    actionBtn: () -> Unit) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier.padding(6.dp).height(155.dp).fillMaxWidth(),
        colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceDim)
    ) {
        val openEditDialog = remember { mutableStateOf(false) }

        Column(
            modifier = Modifier.height(100.dp)
        ) {
            Text(
                text = "$title",
                modifier = Modifier.padding(top = 4.dp, start = 20.dp, end = 20.dp),
                textAlign = TextAlign.Start,
            )
            Column(
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(
                            topStart = 10.dp,
                            topEnd = 10.dp
                        )
                    )
                    .background(Color.LightGray).fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = "$descShort",
                    lineHeight = 1.2.em,
                    fontStyle = FontStyle.Italic,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 4.dp, start = 10.dp, end = 10.dp).height(65.dp),
                    textAlign = TextAlign.Center,
                )
            }
        }
        HorizontalDivider(thickness = 2.dp)
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            )
            {
                Row {
                    Modifier.weight(1f);
                    Icon(
                        painter = painterResource(R.drawable.date_24px),
                        modifier = Modifier.padding(1.dp).size(10.dp),
                        contentDescription = "date"
                    )
                    Text(
                        text = "$date",
                        fontSize = 12.sp,
                        modifier = Modifier.padding(0.dp),
                        textAlign = TextAlign.Center,
                    )
                }
                Row {
                    Modifier.weight(1f);
                    Icon(
                        painter = painterResource(R.drawable.time_24px),
                        modifier = Modifier.padding(1.dp).size(10.dp),
                        contentDescription = "time"
                    )
                    Text(
                        text = "${time.subSequence(0,5)}",
                        fontSize = 12.sp,
                        modifier = Modifier.padding(0.dp),
                        textAlign = TextAlign.Center,
                    )
                }
                Row {
                    Modifier.weight(1f);
                    Icon(
                        painter = painterResource(R.drawable.location_on_24px),
                        modifier = Modifier.padding(1.dp).size(10.dp),
                        contentDescription = "location"
                    )
                    Text(
                        text = "$address",
                        fontSize = 12.sp,
                        modifier = Modifier.padding(0.dp),
                        textAlign = TextAlign.Center,
                    )
                }


            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                ExtendedFloatingActionButton(
                    modifier = Modifier.padding(2.dp).height(20.dp),
                    onClick = { openEditDialog.value = true },
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.edit_note_24px),
                            modifier = Modifier.padding(0.dp),
                            contentDescription = "Edit event"
                        )
                    },
                    text = { Text(text = "Edit", modifier = Modifier.padding(0.dp)) },
                )
                ExtendedFloatingActionButton(
                    modifier = Modifier.padding(2.dp).height(20.dp),
                    onClick = { actionBtn() },
                    icon = {
                        Icon(
                            painter = painterResource(actionBtnIcon),
                            modifier = Modifier.padding(0.dp),
                            contentDescription = actionBtnDes
                        )
                    },
                    text = { Text(text = actionBtnName, modifier = Modifier.padding(0.dp)) },
                )
            }
        }
        when {
            openEditDialog.value -> {
                    EditModal(
                        context,
                        loggedUser,
                        eventViewModel,
                        idEvent = idEvent,
                        title = title,
                        descShort = descShort,
                        desc = desc,
                        date = date,
                        time = time,
                        address = address,
                        onDismissRequest = { openEditDialog.value = false },
                        onConfirmation = {
                            openEditDialog.value = false
                            println("Confirmation registered") // Add logic here to handle confirmation.
                        }
                    )
                }
            }
        }
    }
/*        {
            Icon(
                painter = painterResource(R.drawable.subscribe_24px),
                modifier = Modifier.padding(5.dp),
                contentDescription = "Log user out of the application."
            )
        }*/


/*
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    ZoltansEventManagerFrontendTheme {
        EventCard(1234, "title", "desc_short", "date", "time", "location", "subscribe", R.drawable.subscribe_24px, "subscribe to event", actionBtn = { })
    }
}*/
