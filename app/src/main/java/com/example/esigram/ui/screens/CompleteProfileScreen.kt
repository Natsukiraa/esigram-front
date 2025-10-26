package com.example.esigram.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.esigram.R
import com.example.esigram.ui.components.InputTextField
import com.example.esigram.viewModels.CompleteProfileViewModel

@Composable
fun CompleteProfileScreen(
    completeProfileViewModel: CompleteProfileViewModel
) {
    // mutable state data from VM
    val description = completeProfileViewModel.description.collectAsState()
    val file = completeProfileViewModel.file.collectAsState()
    val username = completeProfileViewModel.username.collectAsState()

    val context = LocalContext.current

    val getContent = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let { completeProfileViewModel.onFileChange(it) }
    }

    Column(modifier = Modifier.fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 8.dp)) {
        Text(
            context.getString(R.string.sign_up),
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.textPrimary),
        )

        InputTextField(
            value = username.value,
            onValueChanged = { completeProfileViewModel.onUsernameChange(it) },
            label = "Username"
        )

        InputTextField(
            value = description.value,
            onValueChanged = { completeProfileViewModel.onDescriptionChange(it) },
            label = "Description"
        )

        file.value?.let { file ->
            Image(
                painter = rememberAsyncImagePainter(file),
                contentDescription = "Selected Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(128.dp)
                    .clip(CircleShape)
            )
        }

        Button(
            onClick = { getContent.launch("image/*") },
            content = { Text("Select Profile Picture") }
        )

        Button(
            onClick = { completeProfileViewModel.completeSignUp() },
            content = { Text("Submit") }
        )
    }
}
