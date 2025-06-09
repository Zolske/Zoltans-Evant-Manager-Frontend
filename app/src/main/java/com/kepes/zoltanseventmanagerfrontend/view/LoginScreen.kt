package com.kepes.zoltanseventmanagerfrontend.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.credentials.CredentialManager
import androidx.navigation.NavHostController
import com.kepes.zoltanseventmanagerfrontend.ui.components.TopBar
import com.kepes.zoltanseventmanagerfrontend.ui.components.adminSignIn
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
import com.kepes.zoltanseventmanagerfrontend.viewModel.UserViewModel

@Composable
fun LoginScreen(
    loggedUserViewModel: LoggedUserViewModel,
    eventViewModel: EventViewModel,
    loginViewModel: LoginViewModel,
    loginUiState: LoginUiState,
    userViewModel: UserViewModel,
    navController: NavHostController,
    showAdmin: MutableState<Boolean>
) {
    val context = LocalContext.current
    val credentialManager = remember { CredentialManager.create(context) }
    val loggedUser by loggedUserViewModel.loggedUserFlow.collectAsState()
    val focusManager = LocalFocusManager.current

    // the box makes it easier to leave the password field when clicking outside
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
            ) {
                focusManager.clearFocus()
            }
            .padding(16.dp)
    ) {

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

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                "   Welcome to\n      Zoltan's\nEvent Manager",
                fontFamily = FontFamily.Serif,
                fontSize = 30.sp,
                lineHeight = 40.sp
            )

            Spacer(modifier = Modifier.height(60.dp))

            if (loggedUser.isLoggedIn == false) {
                Text("Sign in or up with your Google account")
                Spacer(modifier = Modifier.height(10.dp))
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
                ) { Text("Sign IN or UP") }
            } else {
                Text("Welcome ${loggedUser.name}, nice to have you here.")
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    onClick = {
                        loggedUserViewModel.resetLoggedUser()
                        eventViewModel.resetEvents()
                        eventViewModel.resetSubscribedEvents()
                        loginViewModel.resetLoginUiState()
                    }) { Text("Logout") }
            }
            Spacer(modifier = Modifier.height(30.dp))
            if (loggedUser.isLoggedIn == false)
                when (loginUiState) {
                    is NotLoggedIn -> Text("${loginUiState.eMessage}", fontStyle = FontStyle.Italic)
                    is Loading -> Text("${loginUiState.eMessage}", fontStyle = FontStyle.Italic)
                    is AuthGoogle -> Text("${loginUiState.eMessage}", fontStyle = FontStyle.Italic)
                    is AuthBackend -> Text("${loginUiState.eMessage}", fontStyle = FontStyle.Italic)
                    is SignUp -> Text("${loginUiState.eMessage}", fontStyle = FontStyle.Italic)
                    is LoggedIn -> Text("${loginUiState.eMessage}", fontStyle = FontStyle.Italic)
                    is LoginUiState.Error -> Text(
                        "${loginUiState.eMessage}",
                        fontStyle = FontStyle.Italic
                    )
                }

            Spacer(modifier = Modifier.height(100.dp))
            if (loggedUser.isLoggedIn)
                adminSignIn(loggedUserViewModel, context, userViewModel, showAdmin)
        }
    }
}