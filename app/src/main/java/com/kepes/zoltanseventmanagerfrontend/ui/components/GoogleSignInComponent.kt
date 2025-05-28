package com.kepes.zoltanseventmanagerfrontend.ui.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.kepes.zoltanseventmanagerfrontend.BuildConfig
import com.kepes.zoltanseventmanagerfrontend.viewModel.BackendViewModel
import com.kepes.zoltanseventmanagerfrontend.viewModel.UserViewModel
import kotlinx.coroutines.launch

@Composable
fun GoogleSignInScreen( userViewModel: UserViewModel ) {
    val context = LocalContext.current
    val credentialManager = remember { CredentialManager.create(context) }
    val coroutineScope = rememberCoroutineScope()
    var signInStatus by remember { mutableStateOf("Not signed in") }
    val WEB_CLIENT_ID = BuildConfig.WEB_CLIENT_ID

    val userState by userViewModel.userState.collectAsState()

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
                .setServerClientId(WEB_CLIENT_ID)
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
                        val googleIdToken = googleIdTokenCredential.idToken
                        val displayName = googleIdTokenCredential.displayName ?: "Unknown"

                        signInStatus = "Signed in as $displayName\nID Token:\n$googleIdToken"


                        Log.d("GOOGLE ID TOKEN ", "${googleIdToken}" )
                        try {
                            val viewModel = BackendViewModel()
                            Log.d("USER AUTH", "call backend to authenticate user")
                            viewModel.getJWTandID(userViewModel, googleIdToken)
                        } catch (e: Exception) {
                            Log.e("USER AUTH", e.message.toString())
                        }
                    }
                } catch (e: GoogleIdTokenParsingException) {
                    Log.e("USER AUTH", e.message.toString())
                }
            }
        }) {
            Text("Sign in with Google")
        }
        Text(userState.idUser)
    }
}