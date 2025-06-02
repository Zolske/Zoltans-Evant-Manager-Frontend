package com.kepes.zoltanseventmanagerfrontend

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import  androidx. compose. runtime. Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kepes.zoltanseventmanagerfrontend.view.LoginScreen
import com.kepes.zoltanseventmanagerfrontend.viewModel.LoginViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kepes.zoltanseventmanagerfrontend.data.LoggedUser
import com.kepes.zoltanseventmanagerfrontend.ui.components.TopBar
import com.kepes.zoltanseventmanagerfrontend.view.HomeScreen
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.kepes.zoltanseventmanagerfrontend.model.Event
import com.kepes.zoltanseventmanagerfrontend.viewModel.EventViewModel

/**
 * enum values that represent the screens in the app
 */
enum class AppScreens(@StringRes val title: Int) {
    Login(title = R.string.title_sc_login_signup),
    Home(title = R.string.title_sc_home),
    Event(title = R.string.title_sc_event)
}

@Composable
fun ZoltansEventManagerApp(
    navController: NavHostController = rememberNavController()
){
    val loginViewModel: LoginViewModel = viewModel()
    val eventViewModel: EventViewModel = viewModel()
    var userState by remember { mutableStateOf(LoggedUser()) }
    /*var allEventsState by remember { mutableStateOf(mutableListOf<Event>()) }*/
    //var allEventsState by remember mutableStateListOf<Event>()

    Scaffold() { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = AppScreens.Login.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = AppScreens.Login.name) {
                TopBar(userState, AppScreens.Login.name, false) { }
                LoginScreen(
                    userState,
                    loginViewModel,
                    loginViewModel.loginUiState,
                    changeToScreen = {
                        // pass changing to 'next screen' in but do not add Login to 'back stack'
                        navController.navigate(AppScreens.Home.name){
                            popUpTo(AppScreens.Login.name){
                                inclusive = true
                            }
                        }
                    }
                )
            }
            composable(route = AppScreens.Home.name) {
                TopBar(userState, AppScreens.Home.name, true) { navController.popBackStack() }
                HomeScreen(userState, eventViewModel)
            }
        }
    }
}