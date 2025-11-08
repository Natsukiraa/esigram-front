package com.example.esigram.ui.components.home

import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.esigram.viewModels.AuthViewModel

@Composable
fun MainMenuDropdown(expandedState: Boolean,
                     onExpandChange: (Boolean) -> Unit = {},
                     viewModel: AuthViewModel,
                     onSignOut: () -> Unit,
                     onNavigateProfile: () -> Unit) {
    val context = LocalActivity.current as ComponentActivity
    DropdownMenu(
        expanded = expandedState,
        onDismissRequest = { onExpandChange(false) }
    ) {
        DropdownMenuItem(
            text = { Text("Sign Out") },
            onClick = {
                viewModel.signOut(context)
                onExpandChange(false)
                onSignOut()
            }
        )
    }
}