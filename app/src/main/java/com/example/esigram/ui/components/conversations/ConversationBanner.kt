package com.example.esigram.ui.components.conversations

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.esigram.domains.models.Message
import com.example.esigram.domains.models.OldUser
import java.time.Instant
import java.util.UUID

@Composable
fun ConversationBanner(
    onClickOpen: (UUID) -> Unit,
    conversationUuid: UUID,
    user: OldUser
) {
    val message = Message(
        id = "kdjqsoidoidjqsoidjis",
        content = "Ceci est un test Ceci est un test Ceci est un test Ceci est un test Ceci est un test Ceci est un test",
        colorIndex = 1,
        createdAt = Instant.now(),
        seen = false,
        authorId = "1"
    )

    Button(
        modifier = Modifier
            .fillMaxWidth(),
        onClick = { onClickOpen(conversationUuid) },
        shape = RoundedCornerShape(24.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Profile Icon",
                tint = Color.Black,
                modifier = Modifier
                    .size(80.dp)
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 12.dp)
            ) {
                Text(
                    text = "${user.forename} ${user.name}",
                    fontSize = 18.sp,
                    color = Color.Black
                )
                Text(
                    text = message.content
                )
            }

        }
    }
}

@Composable
@Preview
fun ConversationBannerPreview() {
    ConversationBanner(
        onClickOpen = {},
        conversationUuid = UUID.randomUUID(),
        user = OldUser(
            id = "kjqopkdqoskdpqosds",
            forename = "Léna",
            name = "Mabille",
            isOnline = true
        )
    )
}