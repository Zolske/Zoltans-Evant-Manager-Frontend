package com.kepes.zoltanseventmanagerfrontend

import  androidx. compose. runtime. Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kepes.zoltanseventmanagerfrontend.ui.components.GoogleSignInScreen
import com.kepes.zoltanseventmanagerfrontend.viewModel.UserViewModel

@Composable
fun ZoltansEventManagerApp( userViewModel: UserViewModel = viewModel() ){

    GoogleSignInScreen( userViewModel )
}