package com.example.esigram.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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

@Composable
fun ConversationScreen(
    id: String,
    user: User,
    messages: List<Message>
) {

    val scrollState = rememberScrollState()
    val chatId: String = "2f19981f-3200-460d-9ad5-9fa365f74fcf"


    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFEEEEEE)
    ) {
        Box(
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
                    Spacer(modifier = Modifier.padding(bottom = 85.dp))
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
        forename = "John",
        name = "Doe",
        isOnline = true
    )

    val messages: List<Message> = listOf(
        Message(
            id = "djqosdoqsd",
            content = "Coucou 👋 ça va ?",
            colorIndex = 1,
            createdAt = Instant.now(),
            seen = true,
            authorId = "1"
        ),
        Message(
            id = "disqopdkqsopddqsd",
            content = "Oui et toi ?",
            colorIndex = 2,
            createdAt = Instant.now(),
            seen = false,
            authorId = "1"
        ),
        Message(
            id = "opqdkqposdk",
            content = "Dispo pour demain aprem ?",
            colorIndex = 2,
            createdAt = Instant.now(),
            seen = false,
            authorId = "1"
        ),
        Message(
            id = "dpkqspodkpqosd",
            content = "Coucou 👋 ça va ?",
            colorIndex = 1,
            createdAt = Instant.now(),
            seen = true,
            authorId = "1"
        ),
        Message(
            id = "sdpokqpodksd",
            content = "Oui et toi ?",
            colorIndex = 2,
            createdAt = Instant.now(),
            seen = false,
            authorId = "1"
        ),
        Message(
            id = "dpqkpodqskpd",
            content = "Dispo pour demain aprem ?",
            colorIndex = 2,
            createdAt = Instant.now(),
            seen = false,
            authorId = "1"
        ),
        Message(
            id = "zodkpoqskdos",
            content = "Coucou 👋 ça va ?",
            colorIndex = 1,
            createdAt = Instant.now(),
            seen = true,
            authorId = "1"
        ),
        Message(
            id = "qlsqmdlsdsqpdqsd",
            content = "Oui et toi ?",
            colorIndex = 2,
            createdAt = Instant.now(),
            seen = false,
            authorId = "1"
        ),
        Message(
            id = "dpqkpodqskpd",
            content = "Dispo pour demain aprem ?",
            colorIndex = 2,
            createdAt = Instant.now(),
            seen = false,
            authorId = "1"
        ),
        Message(
            id = "zodkpoqskdos",
            content = "Coucou 👋 ça va ?",
            colorIndex = 1,
            createdAt = Instant.now(),
            seen = true,
            authorId = "1"
        ),
        Message(
            id = "qlsqmdlsdsqpdqsd",
            content = "Oui et toi ?",
            colorIndex = 2,
            createdAt = Instant.now(),
            seen = false,
            authorId = "1"
        ),
        Message(
            id = "dpqkpodqskpd",
            content = "Dispo pour demain aprem ?",
            colorIndex = 2,
            createdAt = Instant.now(),
            seen = false,
            authorId = "1"
        ),
        Message(
            id = "zodkpoqskdos",
            content = "Coucou 👋 ça va ?",
            colorIndex = 1,
            createdAt = Instant.now(),
            seen = true,
            authorId = "1"
        ),
    )

    ConversationScreen(
        id = "UUID.randomUUID()",
        user = user,
        messages = messages
    )
}