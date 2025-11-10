package com.example.esigram.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.esigram.R
import com.example.esigram.ui.components.InputTextField
import com.example.esigram.ui.components.ProfileImage
import com.example.esigram.ui.theme.LightGray
import com.example.esigram.viewModels.CompleteProfileViewModel

@Composable
fun CompleteProfileScreen(
    completeProfileViewModel: CompleteProfileViewModel,
    onSuccessSignUp: () -> Unit = {}
) {
    // mutable state data from VM
    val description = completeProfileViewModel.description.collectAsState()
    val fileUri = completeProfileViewModel.fileUri.collectAsState()
    val username = completeProfileViewModel.username.collectAsState()
    val submitResult by completeProfileViewModel.submitResult.collectAsState()

    val context = LocalContext.current

    val getContent = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let { completeProfileViewModel.onFileChange(it, context) }
    }

    LaunchedEffect(submitResult) {
        submitResult.let {
            if(it == true) onSuccessSignUp()
        }
    }

    LaunchedEffect(Unit) {
        completeProfileViewModel.setDefaultProfilePicture(context)
    }

    Surface (modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)) {
            Text(
                context.getString(R.string.sign_up),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.textPrimary),
            )

            Column(modifier = Modifier.padding(top = 36.dp, bottom = 12.dp)
                .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    context.getString(R.string.complete_profile),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = colorResource(id = R.color.textPrimary)
                )

                Text(
                    context.getString(R.string.profile_desc),
                    fontSize = 16.sp,
                    color = colorResource(id = R.color.textSecondary),
                    modifier = Modifier.width(250.dp),
                    textAlign = TextAlign.Center
                )
            }

            Column(modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally) {
                ProfileImage(
                    url = fileUri.value.toString(),
                    modifier = Modifier.size(48.dp)
                        .clip(CircleShape)
                        .border(BorderStroke(1.dp, LightGray), CircleShape)
                        .clickable { getContent.launch("image/*") }
                )

                Column(modifier = Modifier
                    .padding(vertical = 16.dp)
                    .fillMaxWidth()) {
                    InputTextField(
                        value = username.value,
                        onValueChanged = { completeProfileViewModel.onUsernameChange(it) },
                        label = context.getString(R.string.username)
                    )

                    InputTextField(
                        value = description.value,
                        onValueChanged = { completeProfileViewModel.onDescriptionChange(it) },
                        label = context.getString(R.string.description)
                    )
                }

                Button(
                    onClick = { completeProfileViewModel.completeSignUp() },
                    modifier = Modifier.fillMaxWidth(),
                    content = { Text(context.getString(R.string.sign_up)) }
                )
            }


        }
    }
}

