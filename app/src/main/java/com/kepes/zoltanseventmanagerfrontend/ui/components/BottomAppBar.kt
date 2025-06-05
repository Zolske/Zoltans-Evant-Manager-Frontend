package com.kepes.zoltanseventmanagerfrontend.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kepes.zoltanseventmanagerfrontend.R

@Composable
@Preview
fun BottomAppBarExample() {
    Scaffold(
        bottomBar = {
            BottomAppBar(
                modifier = Modifier.height(80.dp),
                actions = {

                },


                floatingActionButton = {
                    Row (
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        FloatingActionButton(
                            onClick = { /* do something */ },
                            containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                            elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.event_upcoming_24px),
                                modifier = Modifier.padding(5.dp),
                                contentDescription = "Page containing all upcoming events."
                            )
                        }
                        FloatingActionButton(
                            onClick = { /* do something */ },
                            containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                            elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.subscribe_24px),
                                modifier = Modifier.padding(5.dp),
                                contentDescription = "Page containing all subscribed events."
                            )
                        }
                        FloatingActionButton(
                            onClick = { /* do something */ },
                            containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                            elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.logout_24px),
                                modifier = Modifier.padding(5.dp),
                                contentDescription = "Log user out of the application."
                            )
                        }
                    }
                }
            )

        },
    ) { innerPadding ->
        Text(
            modifier = Modifier.padding(innerPadding),
            text = ""
        )
    }
}