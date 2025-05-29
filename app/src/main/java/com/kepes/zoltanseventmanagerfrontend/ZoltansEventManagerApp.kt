package com.kepes.zoltanseventmanagerfrontend

import  androidx. compose. runtime. Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kepes.zoltanseventmanagerfrontend.view.LoginScreen
import com.kepes.zoltanseventmanagerfrontend.viewModel.UserViewModel
import com.kepes.zoltanseventmanagerfrontend.viewModel.LoginViewModel

@Composable
fun ZoltansEventManagerApp( userViewModel: UserViewModel = viewModel() ){
    val loginViewModel: LoginViewModel = viewModel()

    //GoogleSignInScreen( userViewModel )
    LoginScreen(userViewModel, loginViewModel, loginViewModel.loginUiState)
}