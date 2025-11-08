package com.example.esigram.ui.components.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.esigram.R

@Composable
fun ProfilePictureClickable(
    uri: String,
    onClick: () -> Unit,
    size: Int = 128
){
    val context = LocalContext.current

    Image(
        painter = rememberAsyncImagePainter(uri),
        contentDescription = context.getString(R.string.profile_picture),
        contentScale = ContentScale.Crop,
        modifier = Modifier.size(size.dp)
            .clip(CircleShape)
            .clickable { onClick() }
    )
}