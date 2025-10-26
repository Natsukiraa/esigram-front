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
import androidx.compose.runtime.remember
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
import com.example.esigram.viewModels.MessageViewModel

@Composable
fun ConversationScreen(
    chatId: String
) {
    val scrollState = rememberScrollState()

    val messageViewModel = remember { MessageViewModel() }
    messageViewModel.startListening(chatId)
    val user = User(
        id = "",
        forename = "John",
        name = "Doe",
        image = "1761156065698.png"
    )
    val messages: List<Message> = messageViewModel.messages

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
    ConversationScreen(
        chatId = "7bc4b585-4c37-4410-bebb-14533c3b862e"
    )
}