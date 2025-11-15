package com.example.esigram.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.esigram.ui.components.navigation.NavigationBar
import com.example.esigram.viewModels.ProfileViewModel
import com.example.esigram.R
import com.example.esigram.datas.local.SessionManager
import com.example.esigram.ui.components.ProfileImage
import com.example.esigram.ui.theme.LightGray

@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel,
    sessionManager: SessionManager,
    onBackClick: () -> Unit,
    onSignOut: () -> Unit
) {
    val context = LocalContext.current

    val username by sessionManager.username.collectAsState(initial = "")
    val profilePictureUrl by sessionManager.profilePictureUrl.collectAsState(initial = "")
    val description by sessionManager.description.collectAsState(initial = "")

    Column {
        NavigationBar(
            title = context.getString(R.string.profile),
            showBackButton = true,
            onBackClick = onBackClick
        )


        Column (
            modifier = Modifier
                .padding(horizontal = 16.dp),
        ) {
            Row {
                ProfileImage(
                    url = profilePictureUrl,
                    modifier = Modifier.size(96.dp)
                        .clip(CircleShape)
                        .border(BorderStroke(1.dp, LightGray), CircleShape)
                )

                Column(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                ) {
                    Text(
                        text = "$username!",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    description?.let {
                        Text(
                            text = it,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }


                }

            }

            Button(
                onClick = onSignOut,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(context.getString(R.string.sign_out))
            }
        }
    }



}