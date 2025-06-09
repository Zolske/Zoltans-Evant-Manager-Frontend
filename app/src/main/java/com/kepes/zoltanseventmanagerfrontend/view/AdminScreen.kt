package com.kepes.zoltanseventmanagerfrontend.view

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.kepes.zoltanseventmanagerfrontend.ui.components.TopBar
import com.kepes.zoltanseventmanagerfrontend.viewModel.LoggedUserViewModel
import com.kepes.zoltanseventmanagerfrontend.viewModel.UserViewModel


@Composable
fun AdminScreen(
    userViewModel: UserViewModel,
    loggedUserViewModel: LoggedUserViewModel,
    context: Context
) {
    val userListFlow by userViewModel.userListFlow.collectAsState()
    val loggedUser by loggedUserViewModel.loggedUserFlow.collectAsState()
    val showAdmin = remember { mutableStateOf(false) }
    val showUsers = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBar("Root Admin", loggedUser.pictureUrl)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 20.dp, start = 10.dp, end = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    showUsers.value = userViewModel.updateUserList(loggedUser)
                })
            { Text("fetch all users") }

            if (userListFlow.isEmpty())
                showUsers.value = userViewModel.updateUserList(loggedUser)
            if (showUsers.value) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 10.dp, start = 2.dp, end = 2.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    items(userListFlow.size) { index ->
                        HorizontalDivider(
                            thickness = 2.dp,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            if (userListFlow[index].pictureUrl.isNotEmpty())
                                AsyncImage(
                                    model = userListFlow[index].pictureUrl,
                                    contentDescription = "user image $showUsers",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .padding(end = 10.dp)
                                        .size(45.dp)
                                        .clip(CircleShape)
                                        .align(Alignment.CenterVertically)
                                )
                            Text(userListFlow[index].name)
                            Spacer(modifier = Modifier.padding(10.dp))
                            if (userListFlow[index].isAdmin)
                                Button(
                                    onClick = {
                                        userViewModel.toggleAdminValue(
                                            loggedUser,
                                            userListFlow[index].idUser,
                                            context
                                        )
                                    })
                                { Text("demote admin") }
                            else
                                Button(
                                    onClick = {
                                        userViewModel.toggleAdminValue(
                                            loggedUser,
                                            userListFlow[index].idUser,
                                            context
                                        )
                                    })
                                { Text("promote admin") }
                        }
                    }
                }
            }
        }
    }
}