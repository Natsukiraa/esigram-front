package com.example.esigram.ui.components.message

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import coil.request.ImageRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import androidx.core.net.toUri

enum class MediaType { LOADING, IMAGE, VIDEO, AUDIO, DOCUMENT, UNKNOWN }

fun getMediaType(filename: String): MediaType {
    val lower = filename.lowercase()
    return when {
        lower.endsWith(".jpg") || lower.endsWith(".png") || lower.endsWith(".jpeg") || lower.endsWith(
            ".webp"
        ) -> MediaType.IMAGE

        lower.endsWith(".mp4") || lower.endsWith(".mov") || lower.endsWith(".mkv") -> MediaType.VIDEO
        lower.endsWith(".mp3") || lower.endsWith(".wav") || lower.endsWith(".ogg") || lower.endsWith(
            ".m4a"
        ) -> MediaType.AUDIO // AJOUT
        lower.endsWith(".pdf") || lower.endsWith(".doc") || lower.endsWith(".docx") || lower.endsWith(
            ".xls"
        ) -> MediaType.DOCUMENT

        else -> MediaType.UNKNOWN
    }
}


suspend fun fetchMediaType(url: String): MediaType {
    return withContext(Dispatchers.IO) {
        try {
            val client =
                OkHttpClient.Builder().followRedirects(false).followSslRedirects(false).build()

            val request = Request.Builder().url(url).head().build()

            client.newCall(request).execute().use { response ->
                val contentType =
                    response.header("X-Media-Type") ?: response.header("Content-Type") ?: ""

                Log.d("MediaTypeCheck", "Quick Check: $contentType")

                getMediaType(contentType)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            MediaType.UNKNOWN
        }
    }
}

@Composable
fun AttachmentItem(
    mediaId: String, apiUrl: String
) {
    val context = LocalContext.current
    val fullUrl = "${apiUrl}media/redirect/$mediaId"

    var mediaType by remember { mutableStateOf(MediaType.LOADING) }
    var showFullscreen by remember { mutableStateOf(false) }

    LaunchedEffect(mediaId) {
        Log.d("MediaTypeCheck", "Fetching media type for $fullUrl")
        mediaType = fetchMediaType(fullUrl)
    }

    val baseModifier = Modifier
        .clip(RoundedCornerShape(12.dp))
        .background(MaterialTheme.colorScheme.surfaceVariant)

    Box(
        modifier = Modifier.animateContentSize()
    ) {
        when (mediaType) {
            MediaType.LOADING -> {
                Box(
                    modifier = baseModifier
                        .height(160.dp)
                        .width(200.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp), strokeWidth = 2.dp)
                }
            }

            MediaType.AUDIO -> {
                Box(modifier = baseModifier.fillMaxWidth()) {
                    AudioPlayerBlock(url = fullUrl)
                }
            }

            MediaType.IMAGE -> {
                Box(
                    modifier = baseModifier
                        .height(160.dp)
                        .widthIn(min = 100.dp, max = 240.dp)
                        .clickable { showFullscreen = true }
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(context).data(fullUrl).crossfade(true).build(),
                        contentDescription = "Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.matchParentSize()
                    )
                }
            }

            MediaType.VIDEO -> {
                Box(
                    modifier = baseModifier
                        .height(160.dp)
                        .widthIn(min = 100.dp, max = 240.dp)
                        .clickable {
                            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(fullUrl)))
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .background(MaterialTheme.colorScheme.surface),
                    )
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Play",
                        tint = Color.White,
                        modifier = Modifier.size(48.dp)
                    )
                }
            }

            MediaType.DOCUMENT, MediaType.UNKNOWN -> {
                Column(
                    modifier = baseModifier
                        .height(160.dp)
                        .widthIn(min = 100.dp, max = 240.dp)
                        .clickable {
                            try {
                                context.startActivity(Intent(Intent.ACTION_VIEW, fullUrl.toUri()))
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                        .padding(8.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.List,
                        contentDescription = "File",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(40.dp)
                    )
                    Text(
                        text = "Document",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }
    }

    if (showFullscreen) {
        Dialog(
            onDismissRequest = { showFullscreen = false },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface)
                    .clickable { showFullscreen = false }) {
                AsyncImage(
                    model = ImageRequest.Builder(context).data(fullUrl).crossfade(true).build(),
                    contentDescription = "Fullscreen",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}