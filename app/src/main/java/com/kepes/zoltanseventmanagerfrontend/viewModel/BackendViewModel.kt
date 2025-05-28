package com.kepes.zoltanseventmanagerfrontend.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kepes.zoltanseventmanagerfrontend.service.BackApiObject
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

/**
 * Handles the logic between the backend and the UI
 */
class BackendViewModel() : ViewModel() {

    /**
     * Gets the JWT and the User ID from the backend, and updates the userViewModel.
     * Both are needed to authenticate the user.
     * The Backend needs the googleIdToken to authenticate the user.
     * @param userViewModel the userViewModel to update
     * @param googleIdToken the googleIdToken to send to the backend
     */
    fun getJWTandID(userViewModel: UserViewModel, googleIdToken: String) {
        viewModelScope.launch {
            try {
                // Call the backend API, google ID token is passed in the header
                val response = BackApiObject.retrofitService.getAuth(headerGoogleIdToken = googleIdToken)
                // The JWT and the User ID are in the headers
                val headers = response.headers()
                if (response.isSuccessful) {
                    if (headers["json-web-token"] != null)
                        userViewModel.setJsonWebToken(headers["json-web-token"].toString())
                    if (headers["user-id"] != null)
                        userViewModel.setIdUser(headers["user-id"].toString())
                    } else {
                    Log.d("USER AUTH", "SUCCESS: json-web-token: ${headers["json-web-token"]}, " +
                            "user-id : ${headers["user-id"]}")
                }
            } catch (e: IOException) {
                Log.e("USER AUTH", e.message.toString())
            } catch (e: HttpException) {
                Log.e("USER AUTH", e.message.toString())
            }
        }
    }
}