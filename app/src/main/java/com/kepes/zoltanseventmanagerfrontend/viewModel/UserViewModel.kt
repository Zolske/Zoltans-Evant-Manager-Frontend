package com.kepes.zoltanseventmanagerfrontend.viewModel

import androidx.lifecycle.ViewModel
import com.kepes.zoltanseventmanagerfrontend.data.LoggedUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * Handles the logic between the backend and the UI for the logged in User data
 */
class UserViewModel : ViewModel() {

    /**
     * logged in user state
     */
    // can only be seen and changed in this class
    private val _userState = MutableStateFlow(LoggedUser())
    // read only outside the class, but can be changed in this class
    val userState: StateFlow<LoggedUser> = _userState.asStateFlow()

    fun setJsonWebToken(jsonWebToken: String) {
        _userState.update { currentState -> currentState.copy(jsonWebToken = jsonWebToken) }
    }

    fun setIdUser(idUser: String) {
        _userState.update { currentState -> currentState.copy(idUser = idUser) }
    }
}