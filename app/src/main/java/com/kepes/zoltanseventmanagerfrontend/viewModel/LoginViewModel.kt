package com.kepes.zoltanseventmanagerfrontend.viewModel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.kepes.zoltanseventmanagerfrontend.BuildConfig
import com.kepes.zoltanseventmanagerfrontend.model.User
import com.kepes.zoltanseventmanagerfrontend.service.BackApiObject
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

sealed interface LoginUiState {
    data class Error(val eMessage: String) : LoginUiState
    data class NotLoggedIn(val eMessage: String) : LoginUiState
    data class Loading(val eMessage: String) : LoginUiState
    data class AuthGoogle(val eMessage: String) : LoginUiState
    data class AuthBackend(val eMessage: String) : LoginUiState
    data class SignUp(val eMessage: String) : LoginUiState
    data class LoggedIn(val eMessage: String) : LoginUiState
}

/**
 * Handles the logic between the backend and the UI
 */
class LoginViewModel : ViewModel() {
    var loginUiState: LoginUiState by mutableStateOf(LoginUiState.NotLoggedIn("You are not logged in."))
        private set

    /**
     * Gets the Google ID token from the Google Identity Services.
     */
    private suspend fun authGoogle(context: Context, credentialManager: CredentialManager): String {
        var googleIdToken = ""
        val WEB_CLIENT_ID = BuildConfig.WEB_CLIENT_ID
        val googleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false) // true for returning users only
            .setServerClientId(WEB_CLIENT_ID)
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()



        loginUiState = LoginUiState.Loading("Authenticating with Google...")
        loginUiState = try {
            val result = credentialManager.getCredential(context, request)
            val credential = result.credential

            if (credential is CustomCredential &&
                credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
            ) {
                val googleIdTokenCredential =
                    GoogleIdTokenCredential.createFrom(credential.data)
                googleIdToken = googleIdTokenCredential.idToken
                //Log.d("GOOGLE AUTH", "Google ID token: $googleIdToken") // for debugging
                LoginUiState.AuthGoogle("Successfully authenticated with Google.")
            } else {
                Log.e("GOOGLE AUTH", "Google credentials are not valid.")
                LoginUiState.Error("Error: Google credentials are not valid, please try again.")
            }
        } catch (e: GoogleIdTokenParsingException) {
            Log.e("GOOGLE AUTH", e.message.toString())
            LoginUiState.Error("Error: Google credentials are not valid, please try again.")
            throw GoogleIdTokenParsingException(e)
        }
        return googleIdToken
    }

    /**
     * Gets the JWT and the User ID from the backend, and updates the userViewModel.
     * Both are needed to authenticate the user.
     * The Backend needs the googleIdToken to authenticate the user.
     * @param userViewModel the userViewModel to update
     * @param googleIdToken the googleIdToken to send to the backend
     */
    private suspend fun authBackend(loggedUser: LoggedUserViewModel, googleIdToken: String): User? {
        loginUiState = LoginUiState.Loading("Authenticating with Server...")
        // resp. has the google user data not the server DB data, server JWT, ID is same for both
        var response: Response<User>
        loginUiState = try {
            // Call the backend API, google ID token is passed in the header
            response =
                BackApiObject.retrofitService.getAuth(headerGoogleIdToken = googleIdToken)
            if (response.isSuccessful) {
                // The JWT and the User ID are in the headers
                val headers = response.headers()
                if (headers["json-web-token"] != null && headers["user-id"] != null) {
                    loggedUser.idUser = headers["user-id"].toString()
                    loggedUser.jsonWebToken = headers["json-web-token"].toString()
                    LoginUiState.AuthBackend("Successfully authenticated with Server.")
                } else {
                    var responseStatus = ""
                    if (headers["response-status-code"] != null)
                        responseStatus = headers["response-status-code"].toString()
                    Log.e("USER AUTH", "${responseStatus}: Server did not return JWT or User ID.")
                    LoginUiState.Error("Error ${responseStatus}: Server could not authenticate your.")
                }
            } else {
                Log.d("USER AUTH", "The Server did not returned the expected response.")
                LoginUiState.Error("Error: There is a problem with the Server.")
            }
        } catch (e: IOException) {
            Log.e("USER AUTH", e.message.toString())
            LoginUiState.Error("Error (${e.message.toString()}): Something went wrong.")
            throw IOException(e)
        } catch (e: HttpException) {
            Log.e("USER AUTH", e.message.toString())
            LoginUiState.Error("Error (${e.message.toString()}): Something went wrong.")
            throw IOException(e)
        }
        return response.body()
    }

    private suspend fun getUserData(loggedUser: LoggedUserViewModel): Boolean {
        loginUiState = LoginUiState.Loading("Get user data from Server...")
        val response = BackApiObject.retrofitService.getUserData(
            bearerToken = "Bearer ${loggedUser.jsonWebToken}",
            userId = loggedUser.idUser
        )

        if (response.isSuccessful && response.body() != null) {
            loggedUser.pictureUrl = response.body()!!.pictureUrl
            loggedUser.name = response.body()!!.name
            loggedUser.email = response.body()!!.email
            loggedUser.hasAccount = true
            loggedUser.isLoggedIn = true
            loggedUser.isAdmin = response.body()!!.isAdmin
            loggedUser.isRootAdmin = response.body()!!.isRootAdmin
            Log.i("USER AUTH", "logged user is admin: ${loggedUser.isAdmin}")
            loginUiState = LoginUiState.LoggedIn("Received user data from the Server.")
            return true
        } else {
            loginUiState = LoginUiState.SignUp("You have no account at the Server.")
            return false
        }
    }

    fun createUser(loggedUser: LoggedUserViewModel, user: User?) {
        loginUiState = LoginUiState.Loading("Creating user ...")
        if (user == null) return
        var response: Response<Unit>
        viewModelScope.launch {
            try {
                response = BackApiObject.retrofitService.createUser(
                    bearerToken = "Bearer ${loggedUser.jsonWebToken}",
                    request = user
                )
                if (response.isSuccessful) {
                    loggedUser.idUser = user.idUser
                    loggedUser.pictureUrl = user.pictureUrl
                    loggedUser.name = user.name
                    loggedUser.email = user.email
                    loggedUser.hasAccount = true
                    loggedUser.isLoggedIn = true
                    loginUiState = LoginUiState.LoggedIn("User created and logged in.")
                } else {
                    loginUiState =
                        LoginUiState.Error("Sorry, something went wrong, please try again.")
                }
            } catch (e: Exception) {
                loginUiState = LoginUiState.Error("Sorry, something went wrong, please try again.")
                Log.e("UPDATE EVENT", "Error: ${e.message}")
            }
        }
    }

    fun loginOrSignupUser(
        loggedUser: LoggedUserViewModel,
        context: Context,
        credentialManager: CredentialManager,
    ) {
        viewModelScope.launch {
            try {
                var googleIdToken = authGoogle(context, credentialManager)
                var googleUserData = authBackend(loggedUser, googleIdToken)
                val hasAccount = getUserData(loggedUser)
                // Log.i("JWT", loggedUser.jsonWebToken)                             // for debugging
                // Log.i("LOGIN USER", "user has account: ${loggedUser.hasAccount}") // for debugging
                if (hasAccount == false)
                    createUser(loggedUser, googleUserData)
            } catch (e: Exception) {
                Log.e("LOGIN SIGNUP", "Error: ${e.message}")
            }
        }
    }

    fun resetLoginUiState() {
        loginUiState = LoginUiState.NotLoggedIn("You are not logged in.")
    }

}