package com.example.esigram.ui.components.message

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

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
            .fillMaxWidth()
            .background(Color(0xFFF9F9FB)),
        color = Color.Transparent,
        tonalElevation = 0.dp
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 8.dp)
                .background(Color.White, RoundedCornerShape(40.dp))
                .padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onAddMedia,
                modifier = Modifier
                    .size(44.dp)
                    .background(Color(0xff5167f1), CircleShape)
            ) {
                Icon(
                    Icons.Outlined.Add,
                    contentDescription = "Add",
                    tint = Color.White,
                    modifier = Modifier.size(26.dp)
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            ChatInputField(
                onSendClick = onSendClick,
                value = value,
                onValueChanged = onValueChanged,
                onVoiceClick = onMicroPhoneActivate
            )

            Spacer(modifier = Modifier.width(8.dp))


        }
    }
}

@Preview(showBackground = true)
@Composable
fun SendBarPreview() {
    SendBar(
        onAddMedia = {},
        value = "dsqdqsdqddsqdqsdqddsqdqsdqddsqdqsdqddsqdqsdqddsqdqsdqddsqdqsdqddsqdqsdqddsqdqsdqd",
        onValueChanged = {},
        onMicroPhoneActivate = {},
        onSendClick = {}
    )
}
