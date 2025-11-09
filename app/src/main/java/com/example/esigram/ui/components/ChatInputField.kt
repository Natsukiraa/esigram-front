package com.example.esigram.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.esigram.R

@Composable
fun ChatInputField(
    value: String,
    onValueChanged: (String) -> Unit,
    onSendClick: () -> Unit,
    onVoiceClick: () -> Unit
) {

    val dynamicShape = if (value.length < 60) {
        RoundedCornerShape(50.dp)
    } else {
        RoundedCornerShape(16.dp)
    }

    Row (
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChanged,
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp),
            placeholder = {
                Text(
                    text = "Type your message here...",
                    color = Color.Gray,
                    fontSize = 15.sp
                )
            },
            shape = dynamicShape,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFF4F4F8),
                unfocusedContainerColor = Color(0xFFF4F4F8),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = Color(0xff5167f1)
            ),
            textStyle = LocalTextStyle.current.copy(fontSize = 16.sp),
            maxLines = 5
        )

        if (value.trim().isNotEmpty()) {
            IconButton (
                onClick = onSendClick,
                modifier = Modifier
                    .size(42.dp)
                    .background(Color(0xff5167f1), CircleShape)
            ) {
                Icon(
                    painter = painterResource(R.drawable.outline_send_24),
                    contentDescription = "Envoyer",
                    tint = Color.White,
                    modifier = Modifier.size(22.dp)
                )
            }
        } else {
            IconButton(
                onClick = onVoiceClick,
                modifier = Modifier
                    .size(42.dp)
                    .background(Color(0xFFE0E0E0), CircleShape)
            ) {
                Icon(
                    painter = painterResource(R.drawable.outline_mic_24),
                    contentDescription = "Enregistrer un message vocal",
                    tint = Color.DarkGray,
                    modifier = Modifier.size(22.dp)
                )
            }
        }
    }
}

@Composable
@Preview
fun ChatInputFieldPreview(){
    ChatInputField(
        value = "1",
        onValueChanged = {},
        onSendClick = {},
        onVoiceClick = {}
    )
}
