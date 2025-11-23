package com.example.esigram.ui.components.story

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.esigram.domains.models.Story


@Composable
fun StoryHeader(
    story: Story,
    onClose: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp, start = 12.dp, end = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val avatarUrl = story.author?.profilePicture?.signedUrl
        val username = story.author?.username ?: "Unknown"

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(avatarUrl)
                .crossfade(true)
                .error(android.R.drawable.ic_menu_report_image)
                .build(),
            contentDescription = "Author Avatar",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(Color.Gray)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = username,
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp
            )
        }

        IconButton(onClick = onClose) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Close",
                tint = Color.White,
                modifier = Modifier.size(28.dp)
            )
        }
    }
}