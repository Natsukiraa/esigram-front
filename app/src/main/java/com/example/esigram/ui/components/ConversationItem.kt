package com.example.esigram.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.esigram.R
import com.example.esigram.models.Conversation
import com.example.esigram.models.Message
import com.example.esigram.models.User
import com.example.esigram.ui.utils.formatConversationDate
import kotlinx.coroutines.sync.Mutex
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
            ProfileImage(url = conversation.participants[0].image ?: "",
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .border(BorderStroke(1.dp, androidx.compose.ui.graphics.Color.LightGray), CircleShape)
            )

            Column(
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text(
                    "${conversation.participants[0].name} ${conversation.participants[0].forename}",
                    fontSize = 24.sp, fontWeight = FontWeight.SemiBold,
                    color = colorResource(id = R.color.textPrimary
                    )
                )

                conversation.lastMessage?.let{ message ->

                    val color = if(conversation.unreadCount > 0) { R.color.primaryColor } else { R.color.textSecondary }
                    Text(
                        message.description,
                        fontSize = 18.sp,
                        color = colorResource(id = color)
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
                        fontSize = 18.sp,
                        color = colorResource(id = R.color.textSecondary)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    if(conversation.unreadCount > 0) {
                        Badge(
                            modifier = Modifier
                                .size(24.dp),
                            text = conversation.unreadCount.toString()
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
    val user = User(
        id = "sdoijisd",
        forename = "Fantom",
        name = "Fant",
        image = "https://randomuser.me/api/portraits/men/1.jpg"
    )

    val message = Message(
        id = "rkokdoqdko",
        description = "coucou",
        colorIndex = 0
    )
    val conversation = Conversation(
        "dkqsjdioqsjd",
        participants = mutableListOf(user),
        lastMessage = message,
        createdAt = Instant.now(),
        unreadCount = 2)
    ConversationItem(conversation)
}