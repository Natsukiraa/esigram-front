package com.example.esigram.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.esigram.R
import com.example.esigram.models.Conversation
import com.example.esigram.models.Message
import com.example.esigram.models.User
import com.example.esigram.ui.utils.formatConversationDate
import java.time.Duration
import java.time.Instant

@Composable
fun ConversationItem(
    conversation: Conversation,
    modifier: Modifier = Modifier
) {

    Surface(){
        Row(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            //Image
            ProfileImage(url = conversation.participants[0].avatarUrl ?: "", modifier = Modifier.size(48.dp))

            Column(
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text(
                    conversation.participants[0].pseudo,
                    color = colorResource(id = R.color.textPrimary
                    )
                )

                conversation.lastMessage?.let{ message ->
                    Text(
                        message.text,
                        color = colorResource(id = R.color.textSecondary)
                    )
                }
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.End
            ) {
                conversation.lastMessage?.let{ message ->
                    Text(
                        text = formatConversationDate(message.createdAt),
                        color = colorResource(id = R.color.textSecondary)
                    )

                    if(conversation.unreadCount > 0) {
                        Text(conversation.unreadCount.toString())
                    }
                }
            }
        }
    }

}


@Preview
@Composable
fun ConversationItemPreview() {
    val user = User(
        id = 1,
        pseudo = "Fantom",
        avatarUrl = "https://randomuser.me/api/portraits/men/1.jpg"
    )

    val message = Message(
        id = 1,
        text = "coucou",
        Instant.now().minus(Duration.ofDays(4))
    )
    val conversation = Conversation(
        1,
        participants = mutableListOf(user),
        lastMessage = message,
        createdAt = Instant.now(),
        unreadCount = 2)
    ConversationItem(conversation)
}