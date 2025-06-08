package com.kepes.zoltanseventmanagerfrontend.viewModel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kepes.zoltanseventmanagerfrontend.data.LoggedUser
import com.kepes.zoltanseventmanagerfrontend.model.User
import com.kepes.zoltanseventmanagerfrontend.service.BackApiObject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

class LoggedUserViewModel : ViewModel() {
    private val _loggedUserFlow = MutableStateFlow<LoggedUser>(LoggedUser())

    // StateFlow for the list of logged user, which is observed by the UI
    val loggedUserFlow: StateFlow<LoggedUser> = _loggedUserFlow.asStateFlow()

    var idUser: String
        get() = _loggedUserFlow.value.idUser
        set(value) {
            _loggedUserFlow.value = _loggedUserFlow.value.copy(idUser = value)
        }
    var jsonWebToken: String
        get() = _loggedUserFlow.value.jsonWebToken
        set(value) {
            _loggedUserFlow.value = _loggedUserFlow.value.copy(jsonWebToken = value)
        }
    var pictureUrl: String
        get() = _loggedUserFlow.value.pictureUrl
        set(value) {
            _loggedUserFlow.value = _loggedUserFlow.value.copy(pictureUrl = value)
        }
    var name: String
        get() = _loggedUserFlow.value.name
        set(value) {
            _loggedUserFlow.value = _loggedUserFlow.value.copy(name = value)
        }
    var email: String
        get() = _loggedUserFlow.value.email
        set(value) {
            _loggedUserFlow.value = _loggedUserFlow.value.copy(email = value)
        }
    var hasAccount: Boolean
        get() = _loggedUserFlow.value.hasAccount
        set(value) {
            _loggedUserFlow.value = _loggedUserFlow.value.copy(hasAccount = value)
        }
    var isLoggedIn: Boolean
        get() = _loggedUserFlow.value.isLoggedIn
        set(value) {
            _loggedUserFlow.value = _loggedUserFlow.value.copy(isLoggedIn = value)
        }
    var isAdmin: Boolean
        get() = _loggedUserFlow.value.isAdmin
        set(value) {
            _loggedUserFlow.value = _loggedUserFlow.value.copy(isAdmin = value)
        }
    var isRootAdmin: Boolean
        get() = _loggedUserFlow.value.isRootAdmin
        set(value) {
            _loggedUserFlow.value = _loggedUserFlow.value.copy(isRootAdmin = value)
        }

    fun resetLoggedUser() {
        _loggedUserFlow.value = LoggedUser()
    }

}