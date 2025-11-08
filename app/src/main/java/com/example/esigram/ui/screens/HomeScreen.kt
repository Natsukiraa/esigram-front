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

    val context = LocalContext.current
    val me = profileViewModel.me.collectAsState()
    val profilePicture = me.value?.profilePictureUrl?.signedUrl?.replace("localhost", "192.168.3.54") ?: "android.resource://${context.packageName}/drawable/default_picture"

     Column {
        MainMenuTopBar(
            profileViewModel = profileViewModel,
            onNavigateProfile = onNavigateProfile
        )
        //ConversationListScreen(conversationViewModel = convViewModel) { }

        ProfilePictureClickable(
            uri = profilePicture,
            onNavigateProfile
        )
    }
}

@Composable
@Preview
fun HomeScreenPreview(){

}