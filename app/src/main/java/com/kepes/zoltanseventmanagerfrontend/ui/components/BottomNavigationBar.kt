package com.kepes.zoltanseventmanagerfrontend.ui.components


import android.R.attr.id
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import com.kepes.zoltanseventmanagerfrontend.data.Screen
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.kepes.zoltanseventmanagerfrontend.R
import com.kepes.zoltanseventmanagerfrontend.data.LoggedUser
import com.kepes.zoltanseventmanagerfrontend.viewModel.LoggedUserViewModel
import androidx.compose.runtime.MutableState


@Composable
fun BottomNavigationBar(navController: NavController, loggedUserViewModel: LoggedUserViewModel, showAdmin: MutableState<Boolean>) {
    val selectedNavigationIndex = rememberSaveable { mutableIntStateOf(0) }
    val loggedUser by loggedUserViewModel.loggedUserFlow.collectAsState()
    val navigationItems = listOf(
        NavigationItem(
            title = "Login",
            icon = ImageVector.vectorResource(R.drawable.login_24px),
            route = Screen.Login.rout,
            isEnabled = !loggedUser.isLoggedIn,
            isVisible = !loggedUser.isLoggedIn
        ),
        NavigationItem(
            title = "Logout",
            icon = ImageVector.vectorResource(R.drawable.logout_24px),
            route = Screen.Login.rout,
            isEnabled = loggedUser.isLoggedIn,
            isVisible = loggedUser.isLoggedIn
        ),
        NavigationItem(
            title = "Upcoming\n   Events",
            icon = ImageVector.vectorResource(R.drawable.event_upcoming_24px),
            route = Screen.UpcomingEvents.rout,
            isEnabled = loggedUser.isLoggedIn
        ),
        NavigationItem(
            title = "Subscribed\n    Events",
            icon = ImageVector.vectorResource(R.drawable.subscribe_24px),
            route = Screen.SubscribedEvents.rout,
            isEnabled = loggedUser.isLoggedIn
        ),
        NavigationItem(
            title = "Create\n Event",
            icon = ImageVector.vectorResource(R.drawable.draw_24px),
            route = Screen.CreateEvent.rout,
            isEnabled = loggedUser.isLoggedIn && loggedUser.isAdmin,
            isVisible = loggedUser.isLoggedIn && loggedUser.isAdmin,
/*            isVisible = true*/
        ),
        NavigationItem(
            title = "Admin",
            icon = ImageVector.vectorResource(R.drawable.manage_accounts_24px),
            route = Screen.SubscribedEvents.rout,
            isEnabled = true,
            isVisible = loggedUser.isLoggedIn && loggedUser.isRootAdmin
        ),
    )

    NavigationBar(
        containerColor = Color.White
    ) {
        Log.i("BottomNavigationBar", "is user logged in: ${loggedUser.isLoggedIn}")
        navigationItems.forEachIndexed { index, item ->
            // only that are set to visible (e.g. not admin, create, only login or logout)
            if (item.isVisible || (showAdmin.value && item.title == "Admin")) {
                NavigationBarItem(
                    selected = selectedNavigationIndex.intValue == index,
                    onClick = {
                        selectedNavigationIndex.intValue = index
                        navController.navigate(item.route)
                    },
                    // show buttons but disable as long the user is not logged in
                    enabled = item.isEnabled,
                    icon = {
                        Icon(imageVector = item.icon, contentDescription = item.title)
                    },
                    label = {
                        Text(
                            item.title,
                            color = if (index == selectedNavigationIndex.intValue)
                                Color.Black
                            else if (item.isEnabled == false)
                                Color.Gray.copy(alpha = 0.4f)
                            else Color.Gray
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.surface,
                        indicatorColor = MaterialTheme.colorScheme.primary
                    )
                )
            }
        }

    }
}

data class NavigationItem(
    val title: String,
    val icon: ImageVector,
    val route: String,
    val isEnabled: Boolean = false,
    val isVisible: Boolean = true,
)