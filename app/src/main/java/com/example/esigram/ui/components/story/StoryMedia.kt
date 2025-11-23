package com.example.esigram.ui.components.story

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.esigram.domains.models.Story

@Composable
fun StoryMedia(
    story: Story,
    isPaused: Boolean,
    onDurationCheck: (Long) -> Unit,
    onBuffering: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val isVideo = story.media.signedUrl.lowercase().let {
        it.contains(".mp4") || it.contains(".mov")
    }
    if (isVideo) {
        StoryVideoPlayer(
            uri = story.media.signedUrl,
            isPaused = isPaused,
            modifier = modifier,
            onDurationChanged = onDurationCheck,
            onVideoBuffering = onBuffering,
        )
    } else {
        LaunchedEffect(Unit) {
            onDurationCheck(5000L)
            onBuffering(false)
        }
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(story.media.signedUrl)
                .crossfade(true)
                .build(),
            contentDescription = "Story Image",
            contentScale = ContentScale.Crop,
            modifier = modifier
        )
    }
}