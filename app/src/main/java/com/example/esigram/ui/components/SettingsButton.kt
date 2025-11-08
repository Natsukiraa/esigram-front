package com.example.esigram.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SettingsButton(onCLick: () -> Unit){
    Button(
        onClick = { onCLick() },
        shape = CircleShape,
        modifier = Modifier.size(32.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFF6F6F6),
            contentColor = Color.Black
        ),
        contentPadding = PaddingValues(0.dp)
    ) {
        Icon(
            imageVector = Icons.Default.MoreVert,
            contentDescription = "Settings",
            modifier = Modifier.size(16.dp)
        )
    }
}

@Preview
@Composable
fun SettingsButtonPreview(){
    SettingsButton(onCLick = {})
}

