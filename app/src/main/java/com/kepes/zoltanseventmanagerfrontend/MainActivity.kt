package com.kepes.zoltanseventmanagerfrontend

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.credentials.*
import androidx.lifecycle.viewModelScope
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.kepes.zoltanseventmanagerfrontend.data.NetworkUserRepository
import com.kepes.zoltanseventmanagerfrontend.service.UserApiService
import com.kepes.zoltanseventmanagerfrontend.viewModel.UserViewModel
import kotlinx.coroutines.launch
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kepes.zoltanseventmanagerfrontend.data.RetrofitInstance

class MainActivity : ComponentActivity() {

    private val WEB_CLIENT_ID = BuildConfig.WEB_CLIENT_ID

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                GoogleSignInScreen(WEB_CLIENT_ID)
            }
        }
    }
}

@Composable
fun GoogleSignInScreen(webClientId: String) {
    val context = LocalContext.current
    val credentialManager = remember { CredentialManager.create(context) }
    val coroutineScope = rememberCoroutineScope()
    var signInStatus by remember { mutableStateOf("Not signed in") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(signInStatus)
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = {
            val googleIdOption = GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(false) // true for returning users only
                .setServerClientId(webClientId)
                .build()

            val request = GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build()

            coroutineScope.launch {
                try {
                    val result = credentialManager.getCredential(context, request)
                    val credential = result.credential

                    if (credential is CustomCredential &&
                        credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
                    ) {
                        val googleIdTokenCredential =
                            GoogleIdTokenCredential.createFrom(credential.data)
                        val idToken = googleIdTokenCredential.idToken
                        val displayName = googleIdTokenCredential.displayName ?: "Unknown"

                        signInStatus = "Signed in as $displayName\nID Token:\n$idToken"

                        // TODO: Send `idToken` to your backend for verification
                        Log.i("READY ", "send request" )
                        try {
                            //val response = RetrofitInstance.api.getUser("something")
                            //val response = RetrofitInstance.api.getUser(idToken)
                            Log.i("RESPONSE ", "before" )
                            val responseGreeting = RetrofitInstance.api.getGreeting()
                            Log.i("RESPONSE ", responseGreeting )
                            //And Remove the advice with _advice
                            //userDataState.value = "user name is: ${response.name}"
                        } catch (e: Exception) {
                            //userDataState.value = "Error: ${e.message}"
                            Log.i("RESPONSE ", e.message.toString())
                        }
                    }
                    // TODO: Send `idToken` to your backend for verification

                //} catch (e: GetCredentialException) {
                } catch (e: GoogleIdTokenParsingException) {
                    signInStatus = "Sign-in failed: ${e.message}"
                    Log.e("GoogleSignIn", "Error: ", e)
                }
            }
        }) {
            Text("Sign in with Google")
        }
    }

    /*
    @Composable
    fun UserApp(idToken: String) {
        val viewModel: UserViewModel = viewModel()
        val advice = viewModel.userDataState.value

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = advice,
                //style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(16.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { viewModel.fetchUser(idToken) }) {
                Text("Refresh Advice")
            }
        }

    }
    */
}
