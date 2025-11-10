package com.example.esigram.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.esigram.ui.components.navigation.NavigationBar
import com.example.esigram.viewModels.ProfileViewModel
import com.example.esigram.R
import com.example.esigram.ui.components.ProfileImage

@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel,
    onBackClick: () -> Unit,
    onSignOut: () -> Unit
) {
    val context = LocalContext.current

    val me = profileViewModel.me.collectAsState()
    val profilePicture = me.value?.profilePictureUrl?.signedUrl?.replace("localhost", "192.168.3.54") ?: "android.resource://${context.packageName}/drawable/default_picture"

    Column {
    NavigationBar(
        title = context.getString(R.string.profile),
        showBackButton = true,
        onBackClick = onBackClick
    )

        ProfileImage(
            url = profilePicture,
            modifier = Modifier
                .size(64.dp)
            
        )

        Button(
            onClick = onSignOut
        ) {
            Text(context.getString(R.string.sign_out))
        }
    }



}