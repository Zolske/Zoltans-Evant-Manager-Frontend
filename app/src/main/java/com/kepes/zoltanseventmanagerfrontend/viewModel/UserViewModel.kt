package com.kepes.zoltanseventmanagerfrontend.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kepes.zoltanseventmanagerfrontend.data.RetrofitInstance
import kotlinx.coroutines.launch

class UserViewModel(idToken: String) : ViewModel() {
    val userDataState = mutableStateOf("Fetching user data...")

    init {
        fetchUser(idToken)
    }

    fun fetchUser(idToken: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getUser(idToken)

                //And Remove the advice with _advice
                userDataState.value = "user name is: ${response.name}"
            } catch (e: Exception) {
                userDataState.value = "Error: ${e.message}"
            }
        }
    }
}