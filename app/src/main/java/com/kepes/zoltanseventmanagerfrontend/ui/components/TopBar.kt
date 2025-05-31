package com.kepes.zoltanseventmanagerfrontend.ui.components

import androidx.compose.foundation.layout.padding
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
import coil.compose.AsyncImage
import com.kepes.zoltanseventmanagerfrontend.data.LoggedUser

/**
 * Composable that displays the topBar and displays back button if back navigation is possible.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    userStateTest: LoggedUser,
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
                    if (userStateTest.pictureUrl.isNotEmpty()) {
                        AsyncImage(
                            model = userStateTest.pictureUrl,
                            contentDescription = null
                        )
                    }
                },
            )
        },
    ) { innerPadding -> Text(
        "",
        modifier = Modifier.padding(innerPadding),
    ) }
}