package com.example.esigram.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.esigram.ui.components.MainMenuTopBar
import com.example.esigram.viewModels.AuthViewModel

@Composable
fun HomeScreen(viewModel: AuthViewModel, onSignOut: () -> Unit) {
    Column {
        MainMenuTopBar(viewModel = viewModel, onSignOut = onSignOut)
    }
}

@Composable
@Preview
fun HomeScreenPreview(){

}