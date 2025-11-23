package com.example.esigram.ui.components.story

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun StoryProgressIndicator(
    stepCount: Int,
    currentStep: Int,
    isPaused: Boolean,
    durationMillis: Long,
    onStepFinished: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp, start = 8.dp, end = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        for (i in 0 until stepCount) {
            val progress = remember(i) { Animatable(0f) }

            LaunchedEffect(currentStep, isPaused, durationMillis) {
                when {
                    i < currentStep -> {
                        progress.snapTo(1f)
                    }
                    i > currentStep -> {
                        progress.snapTo(0f)
                    }
                    else -> {
                        if (progress.value == 1f || progress.value == 0f) {
                            progress.snapTo(0f)
                        }

                        if (!isPaused) {
                            progress.animateTo(
                                targetValue = 1f,
                                animationSpec = tween(
                                    durationMillis = durationMillis.toInt(),
                                    easing = LinearEasing
                                )
                            )
                            onStepFinished()
                        }
                    }
                }
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(3.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color.White.copy(alpha = 0.4f))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(progress.value)
                        .background(Color.White)
                )
            }
        }
    }
}