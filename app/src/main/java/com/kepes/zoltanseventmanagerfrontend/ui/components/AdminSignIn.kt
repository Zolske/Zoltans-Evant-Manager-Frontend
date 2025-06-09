package com.kepes.zoltanseventmanagerfrontend.ui.components

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.kepes.zoltanseventmanagerfrontend.BuildConfig
import com.kepes.zoltanseventmanagerfrontend.viewModel.LoggedUserViewModel
import com.kepes.zoltanseventmanagerfrontend.viewModel.UserViewModel


@Composable
fun adminSignIn(
    loggedUserViewModel: LoggedUserViewModel,
    context: Context,
    userViewModel: UserViewModel,
    showAdmin: MutableState<Boolean>
) {
    var password by remember { mutableStateOf("") }
    val ROOT_ADMIN_PASSWORD = BuildConfig.ROOT_ADMIN_PASSWORD
    val loggedUser by loggedUserViewModel.loggedUserFlow.collectAsState()
    val isRootAdmin = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        if (isRootAdmin.value)
            Text("You are logged in as Root Admin")
        else {
            Text("Root Admin Login")
            PasswordField(
                password = password,
                onPasswordChange = { password = it }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            if (isRootAdmin.value) {
                loggedUser.isRootAdmin = false
                isRootAdmin.value = false
                showAdmin.value = false
            } else {
                if (password == ROOT_ADMIN_PASSWORD) {
                    loggedUser.isRootAdmin = true
                    isRootAdmin.value = true
                    showAdmin.value = true
                    password = ""
                } else
                    Toast.makeText(context, "Wrong password.", Toast.LENGTH_SHORT).show()
            }
        }
        ) {
            if (isRootAdmin.value)
                Text("Root Admin Logout")
            else
                Text("Root Admin Login")
        }
    }
}

@Composable
fun PasswordField(
    password: String,
    onPasswordChange: (String) -> Unit
) {
    var passwordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = password,
        onValueChange = onPasswordChange,
        label = { Text("Root Admin Password") },
        placeholder = { Text("Enter your admin password") },
        singleLine = true,
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            val image = if (passwordVisible)
                Icons.Default.Visibility
            else
                Icons.Default.VisibilityOff

            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(
                    imageVector = image,
                    contentDescription = if (passwordVisible) "Hide password" else "Show password"
                )
            }
        }
    )
}