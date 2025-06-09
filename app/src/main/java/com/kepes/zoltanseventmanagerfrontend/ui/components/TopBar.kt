package com.kepes.zoltanseventmanagerfrontend.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.kepes.zoltanseventmanagerfrontend.R
import com.kepes.zoltanseventmanagerfrontend.ui.theme.ZoltansEventManagerFrontendTheme

@Composable
fun TopBar(
    title: String,
    userUrl: String
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .background(MaterialTheme.colorScheme.inverseOnSurface),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_no_name_500),
            modifier = Modifier
                .size(80.dp)
                .padding(10.dp),
            contentDescription = "App logo."
        )
        Text(
            text = title,
            modifier = Modifier.align(Alignment.CenterVertically),)
        if (userUrl.isNotEmpty()){
            AsyncImage(
                model = userUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(end = 10.dp)
                    .size(45.dp)
                    .clip(CircleShape)
                    .align(Alignment.CenterVertically)
            )
        }
        else {
            Icon(
                painter = painterResource(R.drawable.account_circle_24px),
                modifier = Modifier
                    .padding(end = 10.dp)
                    .align(Alignment.CenterVertically),
                contentDescription = "Log user out of the application."
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun showBar() {
    ZoltansEventManagerFrontendTheme {
        TopBar(title = "title", userUrl = "")
    }
}
