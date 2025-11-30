package com.example.esigram.ui.components.message

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.esigram.domains.models.Message

@Composable
fun MessageBubble(
    message: Message,
    contentColor: Color,
    apiUrl: String = "http://localhost:8080"
) {
    Box(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
    ) {
        Column {
            Text(
                text = message.content,
                fontSize = 16.sp,
                color = contentColor
            )

            if (message.attachments?.isNotEmpty() == true) {
                Spacer(modifier = Modifier.height(8.dp))

                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    message.attachments?.forEach { mediaId ->

                        AttachmentItem(mediaId = mediaId, apiUrl = apiUrl)
                    }
                }
            }
        }
    }
}