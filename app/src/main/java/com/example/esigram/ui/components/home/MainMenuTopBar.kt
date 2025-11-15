package com.example.esigram.ui.components.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.esigram.ui.components.ProfileImage
import com.example.esigram.ui.theme.LightGray
import com.example.esigram.viewModels.ProfileViewModel

@Composable
fun MainMenuTopBar(profileViewModel: ProfileViewModel,
                   onNavigateProfile: () -> Unit) {
    val context = LocalContext.current

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
            ProfileImage(
                url = "http://placehold.it/100x100",
                modifier = Modifier.size(48.dp)
                    .clip(CircleShape)
                    .border(BorderStroke(1.dp, LightGray), CircleShape)
                    .clickable { onNavigateProfile() }
            )

            Text(text = "@grocaca",
                modifier = Modifier.padding(start = 6.dp))
        }
    }
}
