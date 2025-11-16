package com.example.esigram.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.esigram.ui.components.navigation.NavigationBar
import com.example.esigram.viewModels.ProfileViewModel
import com.example.esigram.R
import com.example.esigram.ui.components.ProfileImage
import com.example.esigram.ui.components.form.EditTextField
import com.example.esigram.ui.theme.LightGray

@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel,
    onBackClick: () -> Unit,
    onSignOut: () -> Unit
) {
    val context = LocalContext.current

    val username = profileViewModel.username.collectAsState()
    val email = profileViewModel.email.collectAsState()
    val description = profileViewModel.description.collectAsState()
    val profilePictureUrl = profileViewModel.profilePictureUrl.collectAsState()
    val isEditing = profileViewModel.isEditing.collectAsState()
    
    Column {
        NavigationBar(
            title = context.getString(R.string.profile),
            showBackButton = true,
            onBackClick = onBackClick
        )

        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            ProfileImage(
                url = profilePictureUrl.value,
                modifier = Modifier
                    .size(128.dp)
                    .clip(CircleShape)
                    .border(BorderStroke(1.dp, LightGray), CircleShape)
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(Modifier.height(24.dp))

            EditTextField(
                value = email.value,
                enabled = false,
                label = context.getString(R.string.email)
            )

            Spacer(Modifier.height(12.dp))

            EditTextField(
                value = username.value,
                onValueChange = {
                    profileViewModel.onUsernameChange(it)
                    profileViewModel.onEdit()
                },
                label = context.getString(R.string.username)
            )

            Spacer(Modifier.height(12.dp))

            EditTextField(
                value = description.value,
                onValueChange = {
                    profileViewModel.onDescriptionChange(it)
                    profileViewModel.onEdit()
                },
                label = context.getString(R.string.description),
                maxLines = 3
            )

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = {
                    profileViewModel.onSave()
                },
                enabled = isEditing.value,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save Changes")
            }

            Spacer(Modifier.height(12.dp))

            Button(
                onClick = onSignOut,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.dangerColor)
                )
            ) {
                Text(context.getString(R.string.sign_out))
            }
        }
    }

}

