package com.example.esigram.ui.components

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
import com.example.esigram.models.Message
import com.example.esigram.models.User
import java.time.Instant
import java.util.UUID

@Composable
fun ConversationBanner(
    onClickOpen: (UUID) -> Unit,
    conversationUuid: UUID,
    user : User
){
    val message = Message(
        id = "kdjqsoidoidjqsoidjis",
        description = "Ceci est un test Ceci est un test Ceci est un test Ceci est un test Ceci est un test Ceci est un test",
        colorIndex = 1,
        createdAt = Instant.now(),
        seen = false,
        sender = user
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
                    text = message.description
                )
            }

        }
    }
}

@Composable
@Preview
fun ConversationBannerPreview(){
    ConversationBanner(
        onClickOpen = {},
        conversationUuid = UUID.randomUUID(),
        user = User(
            id = "kjqopkdqoskdpqosds",
            forename = "Léna",
            name = "Mabille",
            isOnline = true
        )
    )
}