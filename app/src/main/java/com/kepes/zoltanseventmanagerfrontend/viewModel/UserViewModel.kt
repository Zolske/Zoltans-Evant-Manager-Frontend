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
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {
    private val _userListFlow = MutableStateFlow<List<User>>(emptyList())

    // StateFlow for the list of users, which is observed by the UI
    val userListFlow: StateFlow<List<User>> = _userListFlow

    fun addUser(user: User) {
        val currentList = _userListFlow.value.toMutableList()
        currentList.add(user)
        _userListFlow.value = currentList.toList()
    }

    fun resetUserList() {
        _userListFlow.value = emptyList()
    }

    fun addUserList(userList: List<User>) {
        val currentList = _userListFlow.value.toMutableList()
        currentList.addAll(userList)
        _userListFlow.value = currentList.toList()
    }

    fun updateUserList(userState: LoggedUser): Boolean {
        var response: MutableList<User>?
        viewModelScope.launch {
            try {
                response = BackApiObject.retrofitService.getAllUsers(
                    bearerToken = "Bearer ${userState.jsonWebToken}",
                ).body()
                if (response != null) {
                    resetUserList()
                    addUserList(response!!)
                }
                Log.i("CREATE EVENT", "Event created $")
            } catch (e: Exception) {
                Log.e("CREATE EVENT", "Error: ${e.message}")
            }
        }
        return true
    }

    fun toggleAdminValue(userState: LoggedUser, idUser: String, context: Context) {
        viewModelScope.launch {
            try {
                var response = BackApiObject.retrofitService.toggleAdmin(
                    bearerToken = "Bearer ${userState.jsonWebToken}",
                    userId = idUser,
                ).body()
                updateUserList(userState)
                Toast.makeText(context, "Admin status changed", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Log.e("CREATE EVENT", "Error: ${e.message}")
            }
        }
    }
}