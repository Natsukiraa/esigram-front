package com.example.esigram.ui.components.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.esigram.ui.components.profile.ProfilePictureClickable
import com.example.esigram.viewModels.ProfileViewModel

@Composable
fun MainMenuTopBar(profileViewModel: ProfileViewModel,
                   onNavigateProfile: () -> Unit) {
    val context = LocalContext.current
    val me = profileViewModel.me.collectAsState()
    val profilePicture = me.value?.profilePictureUrl?.signedUrl?.replace("localhost", "192.168.3.54") ?: "android.resource://${context.packageName}/drawable/default_picture"

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ProfilePictureClickable(
                uri = profilePicture,
                onClick = onNavigateProfile,
                size = 48
            )

            Text(text = "@${me.value?.username}",
                modifier = Modifier.padding(start = 6.dp))
        }
    }
}
