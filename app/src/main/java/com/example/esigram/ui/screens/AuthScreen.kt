package com.example.esigram.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.esigram.R
import com.example.esigram.ui.components.form.EditTextField
import com.example.esigram.ui.components.form.PasswordTextField
import com.example.esigram.viewModels.AuthViewModel

@Composable
fun AuthScreen(
    authViewModel: AuthViewModel,
    onSuccessSignIn: () -> Unit = {},
    onSignUp: () -> Unit = {}
) {
    val context = LocalContext.current

    val finishedAuth = authViewModel.finishedAuth.collectAsState()
    val email = authViewModel.email.collectAsState()
    val password = authViewModel.password.collectAsState()
    val pageState = authViewModel.pageState.collectAsState()

    LaunchedEffect(finishedAuth.value) {
        if(finishedAuth.value) {
            if (authViewModel.isNewUser()) {
                onSignUp()
            } else {
                onSuccessSignIn()
            }
            authViewModel.changeFinishedAuth(false)
        }
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        Box(contentAlignment = Alignment.Center) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = context.getString(R.string.app_logo_desc),
                    modifier = Modifier
                        .size(120.dp)
                        .padding(bottom = 8.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop,
                )

                Column(modifier = Modifier.padding(vertical = 16.dp)) {
                    Text(
                        text = if (pageState.value == "Login") context.getString(R.string.welcome) else context.getString(R.string.register),
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Text(
                        text = context.getString(R.string.welcome_desc),
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        color = colorResource(id = R.color.textSecondary),
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                EditTextField(
                    value = email.value,
                    onValueChange = {
                        authViewModel.onEmailChange(it)
                    },
                    label = context.getString(R.string.email)
                )

                Spacer(modifier = Modifier.height(8.dp))

                PasswordTextField(
                    value = password.value,
                    onValueChange = {
                        authViewModel.onPasswordChange(it)
                    },
                    label = context.getString(R.string.password)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        if (pageState.value == "Login") {
                            authViewModel.login(email.value, password.value)
                        } else {
                            authViewModel.register(email.value, password.value)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text(
                        text = if (pageState.value =="Login") context.getString(R.string.login) else context.getString(R.string.sign_up)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = if (pageState.value == "Login") context.getString(R.string.not_registered_yet) else context.getString(R.string.already_registered),
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .clickable {
                            authViewModel.onPageStateChange(
                                if (pageState.value == "Login") "Register" else "Login"
                            )
                        }
                        .padding(8.dp)
                )
            }
        }
    }
}
