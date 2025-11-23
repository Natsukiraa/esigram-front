package com.example.esigram.ui.components.story

import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.OptIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView

@OptIn(UnstableApi::class)
@Composable
fun StoryVideoPlayer(
    uri: String,
    isPaused: Boolean,
    onDurationChanged: (Long) -> Unit,
    onVideoBuffering: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            repeatMode = Player.REPEAT_MODE_OFF
            playWhenReady = true
        }
    }

    DisposableEffect(exoPlayer) {
        val listener = object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                when (playbackState) {
                    Player.STATE_BUFFERING -> {
                        onVideoBuffering(true)
                    }
                    Player.STATE_READY -> {
                        onVideoBuffering(false)
                        if (exoPlayer.duration > 0) {
                            onDurationChanged(exoPlayer.duration)
                        }
                    }
                    Player.STATE_ENDED -> {
                        exoPlayer.seekTo(0)
                    }
                }
            }
        }
        exoPlayer.addListener(listener)
        onDispose {
            exoPlayer.removeListener(listener)
            exoPlayer.release()
        }
    }

    LaunchedEffect(uri) {
        onVideoBuffering(true)

        val mediaItem = MediaItem.fromUri(uri)
        exoPlayer.setMediaItem(mediaItem)

        exoPlayer.seekTo(0L)

        exoPlayer.prepare()
    }

    LaunchedEffect(isPaused) {
        if (isPaused) {
            exoPlayer.pause()
        } else {
            exoPlayer.play()
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }

    AndroidView(
        factory = { ctx ->
            PlayerView(ctx).apply {
                player = exoPlayer
                useController = false
                resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
                layoutParams = FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            }
        },
        modifier = modifier
    )
}