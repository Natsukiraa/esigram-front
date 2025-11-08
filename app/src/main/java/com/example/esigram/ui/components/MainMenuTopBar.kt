package com.example.esigram.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.esigram.viewModels.AuthViewModel

@Composable
fun MainMenuTopBar(viewModel: AuthViewModel, onSignOut: () -> Unit = {}) {
    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .height(64.dp)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {

        Text(
            "Esigram",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black)

        Box() {
            SettingsButton(onCLick = { expanded = !expanded })
            MainMenuDropdown(expandedState = expanded,
                onExpandChange = { expanded = it },
                viewModel = viewModel,
                onSignOut = onSignOut
            )
        }
    }
}

@Preview
@Composable
fun MainMenuTopBarPreview() {
    MainMenuTopBar(viewModel = AuthViewModel(), onSignOut = {})
}