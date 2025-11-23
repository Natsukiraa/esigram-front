package com.example.esigram.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import com.example.esigram.ui.components.story.StoryHeader
import com.example.esigram.ui.components.story.StoryMedia
import com.example.esigram.ui.components.story.StoryProgressIndicator
import com.example.esigram.viewModels.StoryPlayerViewModel


@Composable
fun StoryPlayerScreen(
    viewModel: StoryPlayerViewModel,
    userId: String,
    onClose: () -> Unit
) {
    val stories by viewModel.stories.collectAsState()
    val currentIndex by viewModel.currentIndex.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    LaunchedEffect(userId) {
        viewModel.loadStoriesForUser(userId)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = Color.White
            )
        } else if (stories.isNotEmpty()) {
            val currentStory = stories.getOrNull(currentIndex)

            if (currentStory != null) {
                var isUserPaused by remember { mutableStateOf(false) }
                var isBuffering by remember { mutableStateOf(true) }
                var currentDuration by remember { mutableLongStateOf(5000L) }
                LaunchedEffect(currentIndex) {
                    currentDuration = 5000L
                    isBuffering = true
                }
                val effectivePaused = isUserPaused || isBuffering
                StoryMedia(
                    story = currentStory,
                    isPaused = isUserPaused,
                    onDurationCheck = { duration ->
                        currentDuration = duration
                    },
                    onBuffering = { buffering ->
                        isBuffering = buffering
                    },
                    modifier = Modifier.fillMaxSize()
                )

                BoxWithConstraints(
                    modifier = Modifier
                        .fillMaxSize()
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onPress = {
                                    isUserPaused = true
                                    tryAwaitRelease()
                                    isUserPaused = false
                                },
                                onTap = { offset ->
                                    val screenWidth = size.width
                                    if (offset.x < screenWidth * 0.3) {
                                        viewModel.prevStory()
                                    } else if(offset.x > screenWidth * 0.7) {
                                        viewModel.nextStory(onFinished = onClose)
                                    }
                                }
                            )
                        }
                ) {}

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Black.copy(alpha = 0.6f), Color.Transparent),
                                startY = 0f,
                                endY = 300f
                            )
                        )
                ) {
                    StoryProgressIndicator(
                        stepCount = stories.size,
                        currentStep = currentIndex,
                        isPaused = effectivePaused,
                        durationMillis = currentDuration,
                        onStepFinished = { viewModel.nextStory(onFinished = onClose) }
                    )

                    StoryHeader(
                        story = currentStory,
                        onClose = onClose
                    )
                }
            }
        } else {
            Text("No stories found", color = Color.White, modifier = Modifier.align(Alignment.Center))
        }
    }
}