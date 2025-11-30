package com.example.esigram.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.esigram.datas.local.SessionManager
import com.example.esigram.ui.components.home.MainMenuTopBar
import com.example.esigram.viewModels.ConversationViewModel

@Composable
fun HomeScreen(
    convViewModel: ConversationViewModel,
    sessionManager: SessionManager,
    onNavigateProfile: () -> Unit,
    onNavigateFriendsList: () -> Unit,
) {
    Column {
        MainMenuTopBar(
            onNavigateProfile = onNavigateProfile,
            sessionManager = sessionManager,
            onNavigateFriendsList = onNavigateFriendsList,
        )
        //ConversationListScreen(conversationViewModel = convViewModel) { }

    }
}

@Composable
@Preview
fun HomeScreenPreview() {

}