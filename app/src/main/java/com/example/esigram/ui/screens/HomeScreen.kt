package com.example.esigram.ui.screens

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.esigram.datas.local.SessionManager
import com.example.esigram.domains.models.TmpUser
import com.example.esigram.ui.components.home.MainMenuTopBar
import com.example.esigram.ui.components.story.StoryList
import com.example.esigram.ui.components.utils.PullToRefreshBox
import com.example.esigram.viewModels.CameraViewModel
import com.example.esigram.viewModels.ConversationViewModel
import com.example.esigram.viewModels.FriendViewModel
import com.example.esigram.viewModels.ProfileViewModel
import com.example.esigram.viewModels.StoryViewModel
import com.example.esigram.viewModels.utils.uriToFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    profileViewModel: ProfileViewModel,
    convViewModel: ConversationViewModel,
    cameraViewModel: CameraViewModel,
    storyViewModel: StoryViewModel,
    friendViewModel: FriendViewModel,
    sessionManager: SessionManager,
    onNavigateProfile: () -> Unit,
    onClickStory: (userId: String) -> Unit
) {
    val pagerState = rememberPagerState(initialPage = 1, pageCount = { 2 })
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    var isUploading by remember { mutableStateOf(false) }

    val isCameraPageFocused = pagerState.currentPage == 0
    val friends by friendViewModel.friends.collectAsState()
    var me by remember { mutableStateOf<TmpUser?>(null) }

    val isRefreshing = friendViewModel.isRefreshing.collectAsState()
    LaunchedEffect(Unit) {
        me = sessionManager.getAsUser()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        HorizontalPager(
            state = pagerState, modifier = Modifier.fillMaxSize(), userScrollEnabled = !isUploading
        ) { page ->
            when (page) {
                0 -> {
                    CameraScreen(
                        viewModel = cameraViewModel,
                        isFocused = isCameraPageFocused,
                        onSend = { media ->
                            scope.launch {
                                isUploading = true
                                val file = withContext(Dispatchers.IO) {
                                    uriToFile(context, media.uri)
                                }
                                if (file != null) {
                                    val success = storyViewModel.postStory(file)
                                    pagerState.animateScrollToPage(1)
                                    if (!success) {

                                        Toast.makeText(context, "Upload failed", Toast.LENGTH_SHORT)
                                            .show()
                                    }
                                }
                                isUploading = false
                            }
                        })
                }

                1 -> {
                    PullToRefreshBox(
                        isRefreshing = isRefreshing.value,
                        onRefresh = { friendViewModel.refreshAllData() }
                    ) {
                        Column(modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())) {
                            MainMenuTopBar(
                                profileViewModel = profileViewModel,
                                onNavigateProfile = onNavigateProfile,
                                sessionManager = sessionManager,
                            )
                            me?.let {
                                StoryList(
                                    me = it,
                                    friends = friends.data,
                                    onFriendStoryClick = { user ->  onClickStory(user.id) }

                                )
                            }

                        }
                    }
                }
            }
        }

        if (isUploading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f))
                    .clickable(enabled = false) {}, contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color.White)
            }
        }
    }
}