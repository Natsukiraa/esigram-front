package com.example.esigram.ui.components.conversations

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.esigram.domains.models.Conversation
import com.example.esigram.domains.models.Message
import com.example.esigram.domains.models.UserConversation
import com.example.esigram.ui.utils.formatConversationDate
import java.time.Instant

@Composable
fun ConversationItem(
    conversation: Conversation,
    modifier: Modifier = Modifier,
    currentUserId: String,
    onClick: () -> Unit
) {
    val colorScheme = MaterialTheme.colorScheme

    val otherUsers = conversation.members.filter { it.id != currentUserId }

    val displayName = if (conversation.isGroup) {
        conversation.title ?: otherUsers.joinToString(", ") { it.username }
    } else {
        otherUsers.firstOrNull()?.username ?: ""
    }

    Log.d("la", currentUserId)

    val displayImage = if (conversation.isGroup) {
        conversation.members[0].profilePicture?.signedUrl
    } else {
        otherUsers.firstOrNull()?.profilePicture?.signedUrl
    }

    Surface(
        modifier = modifier.clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ProfileImage(
                url = displayImage,
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .border(BorderStroke(1.dp, colorScheme.outline), CircleShape)
            )

            Column(
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text(
                    displayName,
                    fontSize = 18.sp, fontWeight = FontWeight.SemiBold,
                    color = colorScheme.onSurface
                )

                conversation.lastMessage?.let { message ->
                    val messagePreviewColor = if (conversation.unreadCount > 0) {
                        colorScheme.primary
                    } else {
                        colorScheme.onSurfaceVariant
                    }

                    Text(
                        message.content,
                        fontSize = 12.sp,
                        color = messagePreviewColor
                    )
                }
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.End
            ) {
                conversation.lastMessage?.let { message ->
                    Text(
                        text = formatConversationDate(message.createdAt),
                        fontSize = 12.sp,
                        color = colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    if (conversation.unreadCount > 0) {
                        Badge(
                            modifier = Modifier
                                .size(22.dp),
                            text = conversation.unreadCount.toString(),
                            fontSize = 12
                        )
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun ConversationItemPreview() {
    val user1 = UserConversation(
        id = "user1",
        username = "azdazd",
    )

    val user2 = UserConversation(
        id = "user2",
        username = "azdazd",
    )

    val user3 = UserConversation(
        id = "user3",
        username = "azdazd",
    )

    val message = Message(
        id = "rkokdoqdko",
        content = "coucou",
        authorId = "1",
        colorIndex = 0
    )
    val conversation = Conversation(
        "dkqsjdioqsjd",
        members = mutableListOf(user1, user2, user3),
        lastMessage = message,
        createdAt = Instant.now(),
        unreadCount = 2
    )
    ConversationItem(conversation, Modifier, "user1", {})
}