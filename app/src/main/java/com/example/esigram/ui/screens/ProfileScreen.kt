package com.example.esigram.ui.screens

import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.example.esigram.viewModels.ProfileViewModel

@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel
) {
    val me = profileViewModel.me.collectAsState()

    Surface {
        Text("Profile Screen" + (me.value?.username ?: "Loading..."))
    }


}