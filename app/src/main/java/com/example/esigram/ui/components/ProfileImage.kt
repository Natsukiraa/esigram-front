package com.example.esigram.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import com.example.esigram.R

@Composable
fun ProfileImage(
    url: String,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        model = url,
        contentDescription = "Profile picture",
        contentScale = ContentScale.Crop,
        placeholder = painterResource(R.drawable.round_person_24),
        error = painterResource(R.drawable.round_person_24),
        modifier = modifier
    )
}