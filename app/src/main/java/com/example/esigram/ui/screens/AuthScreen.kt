package com.example.esigram.ui.screens

import android.app.Activity.RESULT_OK
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.esigram.R
import com.example.esigram.viewModels.AuthViewModel
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract

@Composable
fun AuthScreen(
    authViewModel: AuthViewModel,
    onSuccessSignIn: () -> Unit = {},
    onSignUp: () -> Unit = {}
) {
    val signInLauncher = rememberLauncherForActivityResult(
        contract = FirebaseAuthUIActivityResultContract()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            authViewModel.refreshUser()
            val isNewUser = authViewModel.isNewUser()

            if (isNewUser) {
                onSignUp()
            } else {
                onSuccessSignIn()
                authViewModel.saveUserSession()
            }
        }
    }

    AuthScreenContent {
        signInLauncher.launch(authViewModel.signIn())
    }
}

@Composable
fun AuthScreenContent(onSignInClick: () -> Unit = {}) {
    val context = LocalContext.current

    Surface(modifier = Modifier.fillMaxSize()) {
        Box(contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "App Logo",
                    modifier = Modifier
                        .width(150.dp)
                        .height(150.dp)
                        .padding(bottom = 8.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop,
                )

                Column(
                    modifier = Modifier.padding(vertical = 16.dp)
                ) {
                    Text(
                        context.getString(R.string.welcome),
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        context.getString(R.string.welcome_desc),
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.width(250.dp),
                        textAlign = TextAlign.Center,
                        color = colorResource(id = R.color.textSecondary)
                    )
                }

                Button(
                    onClick = onSignInClick,
                    modifier = Modifier.width(250.dp)
                ) {
                    Text(
                        context.getString(R.string.login) + "/" + context.getString(R.string.sign_up)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun AuthScreenPreview() {
    AuthScreenContent()
}
