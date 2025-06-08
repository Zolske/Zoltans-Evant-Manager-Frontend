package com.kepes.zoltanseventmanagerfrontend

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import  androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kepes.zoltanseventmanagerfrontend.view.LoginScreen
import com.kepes.zoltanseventmanagerfrontend.viewModel.LoginViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.createGraph
import com.kepes.zoltanseventmanagerfrontend.data.Screen
//import com.kepes.zoltanseventmanagerfrontend.ui.components.BottomAppBarExample
import com.kepes.zoltanseventmanagerfrontend.ui.components.BottomNavigationBar
import com.kepes.zoltanseventmanagerfrontend.view.CreateEventScreen
//import com.kepes.zoltanseventmanagerfrontend.ui.components.UpcomingEventScreen
import com.kepes.zoltanseventmanagerfrontend.view.SubscribedEventScreen
import com.kepes.zoltanseventmanagerfrontend.view.UpcomingEventScreen
import com.kepes.zoltanseventmanagerfrontend.viewModel.EventViewModel
import com.kepes.zoltanseventmanagerfrontend.viewModel.LoggedUserViewModel

/**
 * enum values that represent the screens in the app
 */
/*enum class AppScreens(@StringRes val title: Int) {
    Login(title = R.string.title_sc_login_signup),
    Home(title = R.string.title_sc_home),
    SubscribedEvent(title = R.string.title_subscribed_events)
}*/

@Preview(showBackground = true)
@Composable
fun ZoltansEventManagerApp(
    navController: NavHostController = rememberNavController()
) {
    val loginViewModel: LoginViewModel = viewModel()
    val eventViewModel: EventViewModel = viewModel()
    val loggedUserViewModel: LoggedUserViewModel = viewModel()
    //var userState by remember { mutableStateOf(LoggedUser()) }

    Scaffold(
        bottomBar = { BottomNavigationBar(navController, loggedUserViewModel) }
    ) { innerPadding ->

        val graph = navController.createGraph(startDestination = Screen.Login.rout) {
            composable(route = Screen.Login.rout) {
                LoginScreen(
                    loggedUserViewModel,
                    eventViewModel,
                    loginViewModel,
                    loginViewModel.loginUiState,
                    navController
                )
            }
            composable(route = Screen.UpcomingEvents.rout) {
                UpcomingEventScreen(eventViewModel, loggedUserViewModel, LocalContext.current)
            }
            composable(route = Screen.SubscribedEvents.rout) {
                SubscribedEventScreen(eventViewModel, loggedUserViewModel, LocalContext.current)
            }
            composable(route = Screen.CreateEvent.rout) {
                CreateEventScreen(eventViewModel, loggedUserViewModel, LocalContext.current)
            }
        }

        NavHost(
            navController = navController,
            graph = graph,
            modifier = Modifier.padding(innerPadding)
        )
    }
}
