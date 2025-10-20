package com.example.esigram.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.esigram.models.Message
import com.example.esigram.models.User
import com.example.esigram.ui.components.ContactBanner
import com.example.esigram.ui.components.MessageBox
import com.example.esigram.ui.components.SendBar
import java.time.Instant
import java.util.UUID

@Composable
fun ConversationScreen(
    id: UUID,
    user: User,
    messages: List<Message>
) {

    val scrollState = rememberScrollState()


    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFEEEEEE)
    ) {

        Box(
            modifier = Modifier,
            contentAlignment = Alignment.BottomCenter
        ){
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                ContactBanner(
                    onClickCall = {},
                    onBackClick = {},
                    onClickCallCamera = {},
                    user = user
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .verticalScroll(scrollState),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    for (message in messages) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = if (message.colorIndex % 2 == 0) Arrangement.End else Arrangement.Start
                        ) {
                            MessageBox(
                                message = message,
                                onHold = {}
                            )
                        }
                    }
                }
            }

            SendBar(
                onAddMedia = {},
                value = "",
                onValueChanged = {},
                onMicroPhoneActivate = {}
            )

        }



    }
}


@Composable
@Preview
fun ConversationScreenPreview() {

    val user = User(
        id = "dkoqjsodiqsjd",
        forename = "Léna",
        name = "Mabille",
        isOnline = true
    )

    val messages: List<Message> = listOf(
        Message(
            id = "djqosdoqsd",
            description = "Coucou 👋 ça va ?",
            colorIndex = 1,
            createdAt = Instant.now(),
            seen = true,
            sender = user
        ),
        Message(
            id = "disqopdkqsopddqsd",
            description = "Oui et toi ?",
            colorIndex = 2,
            createdAt = Instant.now(),
            seen = false,
            sender = user
        ),
        Message(
            id = "opqdkqposdk",
            description = "Dispo pour demain aprem ?",
            colorIndex = 2,
            createdAt = Instant.now(),
            seen = false,
            sender = user
        ),
        Message(
            id = "dpkqspodkpqosd",
            description = "Coucou 👋 ça va ?",
            colorIndex = 1,
            createdAt = Instant.now(),
            seen = true,
            sender = user
        ),
        Message(
            id = "sdpokqpodksd",
            description = "Oui et toi ?",
            colorIndex = 2,
            createdAt = Instant.now(),
            seen = false,
            sender = user
        ),
        Message(
            id = "dpqkpodqskpd",
            description = "Dispo pour demain aprem ?",
            colorIndex = 2,
            createdAt = Instant.now(),
            seen = false,
            sender = user
        ),
        Message(
            id = "zodkpoqskdos",
            description = "Coucou 👋 ça va ?",
            colorIndex = 1,
            createdAt = Instant.now(),
            seen = true,
            sender = user
        ),
        Message(
            id = "qlsqmdlsdsqpdqsd",
            description = "Oui et toi ?",
            colorIndex = 2,
            createdAt = Instant.now(),
            seen = false,
            sender = user
        ),
    )

    ConversationScreen(
        id = UUID.randomUUID(),
        user = user,
        messages = messages
    )
}