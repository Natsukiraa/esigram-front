package com.example.esigram.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.esigram.ui.components.navigation.NavigationBar
import com.example.esigram.viewModels.ProfileViewModel
import com.example.esigram.R
import com.example.esigram.datas.local.SessionManager
import com.example.esigram.ui.components.ProfileImage

@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel,
    sessionManager: SessionManager,
    onBackClick: () -> Unit,
    onSignOut: () -> Unit
) {
    val context = LocalContext.current

    val username by sessionManager.username.collectAsState(initial = "")

    Column {
    NavigationBar(
        title = context.getString(R.string.profile),
        showBackButton = true,
        onBackClick = onBackClick
    )
        Text("Hello, $username!")
        
        Button(
            onClick = onSignOut
        ) {
            Text(context.getString(R.string.sign_out))
        }
    }



}