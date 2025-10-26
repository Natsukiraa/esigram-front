package com.example.esigram.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.esigram.R

@Composable
fun SendBar(
    onAddMedia: () -> Unit,
    value: String,
    onValueChanged: (String) -> Unit,
    onMicroPhoneActivate: () -> Unit,
    onSendClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        color = Color.White,
        shape = RoundedCornerShape(24.dp, 24.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(Color(0xff5167f1)),
                contentAlignment = Alignment.Center
            ) {
                IconButton(onClick = onAddMedia) {
                    Icon(
                        Icons.Outlined.Add,
                        modifier = Modifier.size(30.dp),
                        contentDescription = "Add Button",
                        tint = Color.White
                    )
                }
            }

            Box(
                modifier = Modifier
                    .padding(start = 8.dp),
                contentAlignment = Alignment.Center
            ){
                OutlinedTextField(
                    value = value,
                    onValueChange = onValueChanged,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp),
                    shape = RoundedCornerShape(42.dp),
                    placeholder = { Text("Type Here...") },
                    colors = TextFieldDefaults.colors(
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )
                IconButton(
                    modifier = Modifier
                        .padding(start = 210.dp),
                   onClick = onMicroPhoneActivate
                ) {
                    Icon(
                        modifier = Modifier
                            .size(30.dp),
                        painter = painterResource(R.drawable.outline_mic_24),
                        contentDescription = "Microphone"
                    )
                }
                IconButton(onClick = onSendClick) {
                    Icon(
                        painter = painterResource(R.drawable.outline_send_24),
                        contentDescription = "Send",
                        tint = Color(0xff5167f1)
                    )
                }


            }


        }

    }
}

@Composable
@Preview
fun SendBarPreview() {
    SendBar(
        onAddMedia = {},
        value = "",
        onValueChanged = {},
        onMicroPhoneActivate = {},
        onSendClick = {}
    )
}