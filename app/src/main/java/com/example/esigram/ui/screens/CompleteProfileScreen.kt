package com.example.esigram.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import coil.compose.rememberAsyncImagePainter
import com.example.esigram.ui.components.InputTextField
import com.example.esigram.viewModels.CompleteProfileViewModel

@Composable
fun CompleteProfileScreen(
    completeProfileViewModel: CompleteProfileViewModel
) {
    val description = completeProfileViewModel.description.collectAsState()
    val file = completeProfileViewModel.file.collectAsState()

    val getContent = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let { completeProfileViewModel.onFileChange(it) }
    }

    Column {
        InputTextField(
            value = description.value,
            onValueChanged = { completeProfileViewModel.onDescriptionChange(it) },
            label = "Description"
        )

        Button(
            onClick = { getContent.launch("image/*") },
            content = { Text("Select Profile Picture") }
        )

        file.value?.let {
            Image(
                painter = rememberAsyncImagePainter(it),
                contentDescription = "Selected Image",
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
