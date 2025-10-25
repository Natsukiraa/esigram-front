package com.example.esigram.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.esigram.ui.components.MainMenuTopBar
import com.example.esigram.viewModels.AuthViewModel
import com.example.esigram.viewModels.CompleteProfileViewModel
import com.example.esigram.viewModels.ConversationViewModel
import com.example.esigram.viewModels.StoryViewModel

@Composable
fun HomeScreen(
    authViewModel: AuthViewModel,
    convViewModel: ConversationViewModel,
    completeProfileViewModel: CompleteProfileViewModel,
    onSignOut: () -> Unit) {
    Column {
        MainMenuTopBar(viewModel = authViewModel, onSignOut = onSignOut)
        CompleteProfileScreen(completeProfileViewModel = completeProfileViewModel)
    }

    //ConversationListScreen(conversationViewModel = convViewModel) { }
}

@Composable
@Preview
fun HomeScreenPreview(){

}