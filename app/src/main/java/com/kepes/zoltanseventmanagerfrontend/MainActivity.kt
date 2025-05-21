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
import androidx.compose.ui.unit.dp
import androidx.credentials.*
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import kotlinx.coroutines.launch

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
                        val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                        val idToken = googleIdTokenCredential.idToken
                        val displayName = googleIdTokenCredential.displayName ?: "Unknown"

                        signInStatus = "Signed in as $displayName\nID Token:\n$idToken"

                        // TODO: Send `idToken` to your backend for verification
                    } else {
                        signInStatus = "Unexpected credential type"
                    }
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
}
