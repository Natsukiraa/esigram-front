package com.example.esigram.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.esigram.datas.local.SessionManager
import com.example.esigram.ui.components.home.MainMenuTopBar
import com.example.esigram.viewModels.ConversationViewModel
import com.example.esigram.viewModels.ProfileViewModel

@Composable
fun HomeScreen(
    profileViewModel: ProfileViewModel,
    convViewModel: ConversationViewModel,
    sessionManager: SessionManager,
    onNavigateProfile: () -> Unit) {
     Column {
        MainMenuTopBar(
            profileViewModel = profileViewModel,
            onNavigateProfile = onNavigateProfile,
            sessionManager = sessionManager
        )
        //ConversationListScreen(conversationViewModel = convViewModel) { }

    }
}

@Composable
@Preview
fun HomeScreenPreview(){

}