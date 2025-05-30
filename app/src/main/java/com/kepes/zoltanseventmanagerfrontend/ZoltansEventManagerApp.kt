package com.kepes.zoltanseventmanagerfrontend

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import  androidx. compose. runtime. Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kepes.zoltanseventmanagerfrontend.view.LoginScreen
import com.kepes.zoltanseventmanagerfrontend.viewModel.UserViewModel
import com.kepes.zoltanseventmanagerfrontend.viewModel.LoginViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kepes.zoltanseventmanagerfrontend.view.HomeScreen

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
    userViewModel: UserViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
){
    val loginViewModel: LoginViewModel = viewModel()

    Scaffold() { innerPadding ->
        //val uiState by viewModel.uiState.colletAsState()

        NavHost(
            navController = navController,
            startDestination = AppScreens.Login.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = AppScreens.Login.name) {
                LoginScreen(
                    userViewModel,
                    loginViewModel,
                    loginViewModel.loginUiState,
                    changeToScreen = {navController.navigate(AppScreens.Home.name)})
            }
            composable(route = AppScreens.Home.name) {
                HomeScreen()
            }
        }
    }

    //GoogleSignInScreen( userViewModel )
    //LoginScreen(userViewModel, loginViewModel, loginViewModel.loginUiState)
}