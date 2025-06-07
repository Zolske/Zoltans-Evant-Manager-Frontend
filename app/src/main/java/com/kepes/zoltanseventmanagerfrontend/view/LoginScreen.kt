package com.kepes.zoltanseventmanagerfrontend.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.credentials.CredentialManager
import androidx.navigation.NavHostController
import com.kepes.zoltanseventmanagerfrontend.data.Screen
import com.kepes.zoltanseventmanagerfrontend.ui.components.TopBar
import com.kepes.zoltanseventmanagerfrontend.viewModel.EventViewModel
import com.kepes.zoltanseventmanagerfrontend.viewModel.LoggedUserViewModel
import com.kepes.zoltanseventmanagerfrontend.viewModel.LoginUiState
import com.kepes.zoltanseventmanagerfrontend.viewModel.LoginUiState.AuthBackend
import com.kepes.zoltanseventmanagerfrontend.viewModel.LoginUiState.AuthGoogle
import com.kepes.zoltanseventmanagerfrontend.viewModel.LoginUiState.Loading
import com.kepes.zoltanseventmanagerfrontend.viewModel.LoginUiState.LoggedIn
import com.kepes.zoltanseventmanagerfrontend.viewModel.LoginUiState.NotLoggedIn
import com.kepes.zoltanseventmanagerfrontend.viewModel.LoginUiState.SignUp
import com.kepes.zoltanseventmanagerfrontend.viewModel.LoginViewModel

@Composable
fun LoginScreen(
    loggedUserViewModel: LoggedUserViewModel,
    eventViewModel: EventViewModel,
    loginViewModel: LoginViewModel,
    loginUiState: LoginUiState,
    navController: NavHostController
) {
    val context = LocalContext.current
    val credentialManager = remember { CredentialManager.create(context) }
    val loggedUser by loggedUserViewModel.loggedUserFlow.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize(),
        //verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (loggedUser.isLoggedIn)
            TopBar(title = "Logout", userUrl = loggedUser.pictureUrl)
        else
            TopBar(title = "Login", userUrl = "")

        Spacer(modifier = Modifier.height(100.dp))

        if (loggedUser.isLoggedIn == false)
            Button(
                onClick = {
                    loginViewModel.loginOrSignupUser(
                        loggedUserViewModel,
                        context,
                        credentialManager,
                        // go to the following screen if logged in successfully
                        //{ navController.navigate(Screen.UpcomingEvents.rout) }
                    )
                },
            ) { Text("Sign IN or UP with your Google account") }
        else
            Button(
                onClick = {
                    loggedUserViewModel.resetLoggedUser()
                    eventViewModel.resetEvents()
                }) { Text("Logout") }

        Spacer(modifier = Modifier.height(100.dp))

        if (loggedUser.isLoggedIn == false)
            when (loginUiState) {
                is NotLoggedIn -> Text("${loginUiState.eMessage}")
                is Loading -> Text("${loginUiState.eMessage}")
                is AuthGoogle -> Text("${loginUiState.eMessage}")
                is AuthBackend -> Text("${loginUiState.eMessage}")
                is SignUp -> Text("${loginUiState.eMessage}")
                is LoggedIn -> Text("${loginUiState.eMessage}")
                is LoginUiState.Error -> Text("${loginUiState.eMessage}")
            }
    }
}