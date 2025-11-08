package com.example.esigram.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.esigram.ui.components.home.MainMenuTopBar
import com.example.esigram.ui.components.profile.ProfilePictureClickable
import com.example.esigram.viewModels.AuthViewModel
import com.example.esigram.viewModels.ConversationViewModel
import com.example.esigram.viewModels.ProfileViewModel

@Composable
fun HomeScreen(
    profileViewModel: ProfileViewModel,
    convViewModel: ConversationViewModel,
    onNavigateProfile: () -> Unit) {
     Column {
        MainMenuTopBar(
            profileViewModel = profileViewModel,
            onNavigateProfile = onNavigateProfile
        )
        //ConversationListScreen(conversationViewModel = convViewModel) { }

    }
}

@Composable
@Preview
fun HomeScreenPreview(){

}