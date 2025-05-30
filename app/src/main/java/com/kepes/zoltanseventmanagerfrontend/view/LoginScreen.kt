package com.kepes.zoltanseventmanagerfrontend.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.credentials.CredentialManager
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
    userViewModel: UserViewModel,
    loginViewModel: LoginViewModel,
    loginUiState: LoginUiState,
    changeToScreen: () -> Unit = {}
){
    val context = LocalContext.current
    val credentialManager = remember { CredentialManager.create(context) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            loginViewModel.loginOrSignupUser(
                userViewModel,
                context,
                credentialManager,
                changeToScreen
            )
        })
        {
            Text("'Sign in' or 'sign up' with Google")
        }
        when (loginUiState) {
            is NotLoggedIn -> Text("${loginUiState.eMessage}")
            is Loading -> Text("${loginUiState.eMessage}")
            is AuthGoogle -> Text("${loginUiState.eMessage}")
            is AuthBackend -> Text("${loginUiState.eMessage}")
            is SignUp -> Text("${loginUiState.eMessage}")
            is LoggedIn -> Text("${loginUiState.eMessage}")
            is LoginUiState.Error -> Text("${loginUiState.eMessage}")
        }
        Button(onClick = changeToScreen) { Text("Go to Home Screen")}
    }
}