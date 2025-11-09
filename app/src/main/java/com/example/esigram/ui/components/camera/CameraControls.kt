package com.example.esigram.ui.components.camera

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalViewConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.esigram.R
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun CameraControls(
    modifier: Modifier = Modifier,
    onCapture: () -> Unit,
    toggleRecord: () -> Unit,
    onSelectFromGallery: () -> Unit,
    activeRecording: Boolean,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onSelectFromGallery,
            modifier = Modifier.size(48.dp),
            colors = IconButtonDefaults.iconButtonColors(contentColor = Color.White)
        ) {
            Icon(
                painter = painterResource(R.drawable.gallery_thumbnail_24px),
                contentDescription = "Select from Gallery",
                modifier = Modifier.size(30.dp),
                tint = Color.White
            )
        }

        ShutterButton(
            activeRecording = activeRecording,
            onCapture = onCapture,
            toggleRecord = toggleRecord
        )

        Box(modifier = Modifier.size(48.dp))
    }
}

@Composable
private fun ShutterButton(
    activeRecording: Boolean,
    onCapture: () -> Unit,
    toggleRecord: () -> Unit
) {
    val color by animateColorAsState(
        targetValue = if (activeRecording) Color.Red else Color.White,
        label = "Shutter Color"
    )
    val innerSize by animateDpAsState(
        targetValue = if (activeRecording) 40.dp else 64.dp,
        label = "Shutter Inner Size"
    )
    val innerCornerRadius by animateDpAsState(
        targetValue = if (activeRecording) 8.dp else 32.dp,
        animationSpec = spring(),
        label = "Shutter Corner Radius"
    )

    val longPressTimeout = LocalViewConfiguration.current.longPressTimeoutMillis

    Box(
        modifier = Modifier
            .size(80.dp)
            .pointerInput(Unit) {
                coroutineScope {
                    awaitPointerEventScope {
                        while (true) {
                            val down = awaitFirstDown(requireUnconsumed = false)
                            var longPressJob: Job? = null
                            var longPressed = false

                            longPressJob = launch {
                                delay(longPressTimeout)
                                longPressed = true
                                toggleRecord()
                            }

                            val up = waitForUpOrCancellation()

                            longPressJob.cancel()

                            if (longPressed) {
                                toggleRecord()
                            } else {
                                onCapture()
                            }
                        }
                    }
                }
            }
            .border(4.dp, color, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(innerSize)
                .clip(RoundedCornerShape(innerCornerRadius))
                .background(color)
        )
    }
}
