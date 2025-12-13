package com.example.esigram.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.esigram.R
import com.example.esigram.domains.models.ThemeMode
import com.example.esigram.ui.components.ThemeSelector
import com.example.esigram.ui.components.conversations.ProfileImage
import com.example.esigram.ui.components.form.EditTextField
import com.example.esigram.ui.components.navigation.NavigationBar
import com.example.esigram.viewModels.ProfileViewModel

@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel,
    onBackClick: () -> Unit,
    onSignOut: () -> Unit,
    selectedTheme: ThemeMode,
    onThemeSelected: (ThemeMode) -> Unit
) {
    val context = LocalContext.current
    val colorScheme = MaterialTheme.colorScheme

    val username by profileViewModel.username.collectAsState()
    val email by profileViewModel.email.collectAsState()
    val description by profileViewModel.description.collectAsState()
    val profilePictureUrl by profileViewModel.profilePictureUrl.collectAsState()
    val isEditing by profileViewModel.isEditing.collectAsState()
    val fileUri by profileViewModel.fileUri.collectAsState()

    val getContent =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let { newUri ->
                profileViewModel.onFileChangeUtil(newUri, context)
            }
            profileViewModel.onEdit()
        }

    Column(modifier = Modifier.fillMaxHeight()) {

        NavigationBar(
            title = context.getString(R.string.profile),
            showBackButton = true,
            onBackClick = onBackClick
        )

        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            ProfileImage(
                url = if (fileUri != null) fileUri.toString() else profilePictureUrl,
                modifier = Modifier
                    .size(128.dp)
                    .clip(CircleShape)
                    .border(BorderStroke(1.dp, colorScheme.outline), CircleShape)
                    .align(Alignment.CenterHorizontally)
                    .clickable {
                        getContent.launch("image/*")
                    }
            )

            Spacer(Modifier.height(24.dp))

            EditTextField(
                value = email,
                enabled = false,
                label = context.getString(R.string.email)
            )

            Spacer(Modifier.height(12.dp))

            EditTextField(
                value = username,
                onValueChange = {
                    profileViewModel.onUsernameChange(it)
                    profileViewModel.onEdit()
                },
                label = context.getString(R.string.username)
            )

            Spacer(Modifier.height(12.dp))

            EditTextField(
                value = description,
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
                enabled = isEditing,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(context.getString(R.string.save_changes))
            }

            Spacer(Modifier.height(24.dp))

            ThemeSelector(
                selectedTheme = selectedTheme,
                onThemeSelected = onThemeSelected
            )

            Spacer(Modifier.height(12.dp))

            Spacer(Modifier.weight(1f))
        }

        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp)
                .fillMaxWidth()
        ) {

            Button(
                onClick = onSignOut,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorScheme.error,
                    contentColor = colorScheme.onError
                )
            ) {
                Text(context.getString(R.string.sign_out))
            }
        }
    }
}