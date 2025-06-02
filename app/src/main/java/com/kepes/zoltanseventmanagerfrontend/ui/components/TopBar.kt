package com.kepes.zoltanseventmanagerfrontend.ui.components

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.kepes.zoltanseventmanagerfrontend.R
import com.kepes.zoltanseventmanagerfrontend.data.LoggedUser
import com.kepes.zoltanseventmanagerfrontend.ui.theme.ZoltansEventManagerFrontendTheme

/**
 * Composable that displays the topBar and displays back button if back navigation is possible.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    userState: LoggedUser,
    titleBar: String,
    canNavBack: Boolean,
    navigateBack: () -> Unit,
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(titleBar)
                },
                navigationIcon = {
                    if (canNavBack) {
                        IconButton(onClick = navigateBack) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Localized description"
                            )
                        }
                    }
                },
                actions = {
                    Log.i("TOPBAR", userState.pictureUrl)
                    if (userState.pictureUrl.isNotEmpty()) {
                        AsyncImage(
                            model = userState.pictureUrl,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .padding(5.dp)
                                .size(60.dp) // Adjust size as needed
                                .clip(CircleShape)
                        )
                        IconButton(onClick = { /* do something */ }) {
                            Icon(
                                painter = painterResource(R.drawable.logout_24px),
                                modifier = Modifier.padding(5.dp),
                                contentDescription = "Log user out of the application."
                            )
                        }
                    }
                },
            )
        },
    ) { innerPadding -> Text(
        "",
        modifier = Modifier.padding(innerPadding),
    ) }
}

@Preview(showBackground = true)
@Composable
fun TopBarPreview() {
    ZoltansEventManagerFrontendTheme {
        var loggedUser = LoggedUser()
        loggedUser.pictureUrl = "https://lh3.googleusercontent.com/a/ACg8ocIcj6dPd3Q9kdl_d62qukTHDOOWqchpdb9U0WtBmcu2YzVcc8TY=s96-c"
        TopBar(loggedUser, "Login", false, {})
    }
}