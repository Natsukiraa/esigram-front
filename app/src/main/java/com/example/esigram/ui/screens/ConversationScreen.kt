package com.example.esigram.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.esigram.datas.local.SessionManager
import com.example.esigram.domains.models.OldUser
import com.example.esigram.ui.components.ContactBanner
import com.example.esigram.ui.components.MessageBox
import com.example.esigram.ui.components.SendBar
import com.example.esigram.viewModels.MessageViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConversationScreen(
    messageViewModel: MessageViewModel,
    sessionManager: SessionManager,
    chatId: String
) {
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()

    var messageText by rememberSaveable { mutableStateOf("") }
    var showBottomSheet by remember { mutableStateOf(false) }
    var selectedMessageId by remember { mutableStateOf<String?>(null) }

    var lastKnownMessageId by remember { mutableStateOf<String?>(null) }

    val userId by sessionManager.id.collectAsState(initial = "")
    val messages by messageViewModel.allMessages.collectAsState()

    val user = OldUser(
        id = "",
        forename = "John",
        name = "Doe",
        image = "1761156065698.png"
    )

    LaunchedEffect(chatId, userId) {
        if (userId.isNotBlank()) {
            messageViewModel.startListening(chatId, userId)
        }
    }

    LaunchedEffect(listState, userId) {
        snapshotFlow { listState.firstVisibleItemIndex }
            .distinctUntilChanged()
            .filter { it <= 2 }
            .collect {
                if (messages.isNotEmpty()) {
                    messageViewModel.onLoadMore(chatId, userId)
                }
            }
    }

    LaunchedEffect(messages) {
        if (messages.isNotEmpty()) {
            val currentLastId = messages.last().id

            if (currentLastId != lastKnownMessageId) {
                lastKnownMessageId = currentLastId
                listState.animateScrollToItem(messages.size - 1)
            }
        }
    }

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                showBottomSheet = false
                selectedMessageId = null
            },
            sheetState = sheetState
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        selectedMessageId?.let { id ->
                            messageViewModel.deleteMessage(chatId, id)
                        }
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                showBottomSheet = false
                                selectedMessageId = null
                            }
                        }
                    }
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Delete,
                    contentDescription = "Delete message",
                    tint = Color.Red
                )
                Text(
                    text = "Delete message",
                    fontSize = 18.sp,
                    color = Color.Red
                )
            }
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .imePadding(),
        containerColor = Color(0xFFEEEEEE),
        topBar = {
            ContactBanner(
                onClickCall = {},
                onBackClick = {},
                onClickCallCamera = {},
                user = user
            )
        },
        bottomBar = {
            SendBar(
                onAddMedia = {},
                value = messageText,
                onValueChanged = { messageText = it },
                onMicroPhoneActivate = {},
                onSendClick = {
                    if (messageText.isNotBlank()) {
                        messageViewModel.createMessage(chatId, messageText)
                        messageText = ""
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            state = listState,
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(
                items = messages,
                key = { it.id }
            ) { message ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = if (message.authorId == userId) Arrangement.End else Arrangement.Start
                ) {
                    MessageBox(
                        message = message,
                        onHold = { id ->
                            if (message.authorId == userId) {
                                selectedMessageId = id
                                showBottomSheet = true
                            }
                        }
                    )
                }
            }
        }
    }
}