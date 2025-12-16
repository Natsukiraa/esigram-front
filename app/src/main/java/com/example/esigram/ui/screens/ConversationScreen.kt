package com.example.esigram.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.esigram.datas.local.SessionManager
import com.example.esigram.domains.models.OldUser
import com.example.esigram.ui.components.message.AudioRecorder
import com.example.esigram.ui.components.message.ContactBanner
import com.example.esigram.ui.components.message.MediaAttachmentPreview
import com.example.esigram.ui.components.message.MessageBox
import com.example.esigram.ui.components.message.RecordingIndicator
import com.example.esigram.ui.components.message.SendBar
import com.example.esigram.viewModels.MessageViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConversationScreen(
    messageViewModel: MessageViewModel,
    sessionManager: SessionManager,
    chatId: String
) {
    val context = LocalContext.current

    var isRecording by remember { mutableStateOf(false) }
    val recorder by remember { mutableStateOf(AudioRecorder(context)) }
    var audioFile by remember { mutableStateOf<File?>(null) }
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()

    var messageText by rememberSaveable { mutableStateOf("") }
    val selectedMedias = remember { mutableStateListOf<Uri>() }
    var showBottomSheet by remember { mutableStateOf(false) }
    var selectedMessageId by remember { mutableStateOf<String?>(null) }

    var lastKnownMessageId by remember { mutableStateOf<String?>(null) }

    val userId by sessionManager.id.collectAsState(initial = "")
    val messages by messageViewModel.allMessages.collectAsState()
    val authors by messageViewModel.allAuthors.collectAsState()
    val chat by messageViewModel.chat.collectAsState()


    val fileLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents()
    ) { uris ->
        selectedMedias.addAll(uris)
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            val file = File(context.cacheDir, "audio_message.mp3")

            recorder.start(file)

            audioFile = file
            isRecording = true
        } else {
            Toast.makeText(context, "Permission micro refusée", Toast.LENGTH_SHORT).show()
        }
    }

    val user = OldUser(
        id = "",
        forename = "John",
        name = "Doe",
        image = "1761156065698.png"
    )

    LaunchedEffect(chatId, userId) {
        if (userId.isNotBlank()) {
            messageViewModel.startListening(chatId, userId)
            messageViewModel.getChatInfo(chatId)
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
            chat?.let {
                ContactBanner(
                    onBackClick = {},
                    chat = it,
                    userId = userId
                )
            }
        },
        bottomBar = {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                MediaAttachmentPreview(
                    medias = selectedMedias,
                    onRemove = { selectedMedias.remove(it) }
                )

                RecordingIndicator(isRecording = isRecording)

                SendBar(
                    onAddMedia = {
                        fileLauncher.launch("*/*")
                    },
                    value = messageText,
                    onValueChanged = { messageText = it },
                    onMicroPhoneActivate = {
                        if (!isRecording) {
                            if (ContextCompat.checkSelfPermission(
                                    context,
                                    Manifest.permission.RECORD_AUDIO
                                ) == PackageManager.PERMISSION_GRANTED
                            ) {
                                val fileName = "audio_${System.currentTimeMillis()}.mp3"
                                val file = File(context.cacheDir, fileName)

                                recorder.start(file)
                                audioFile = file
                                isRecording = true
                            } else {
                                permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
                            }
                        } else {
                            recorder.stop()
                            isRecording = false

                            audioFile?.let { file ->

                                messageViewModel.createMessage(
                                    context = context,
                                    chatId = chatId,
                                    content = "",
                                    file = file
                                )
                            }
                            audioFile = null
                        }
                    },
                    onSendClick = {
                        if (messageText.isNotBlank() || selectedMedias.isNotEmpty()) {
                            messageViewModel.createMessage(
                                context,
                                chatId,
                                messageText,
                                selectedMedias.toList()
                            )
                            messageText = ""
                            selectedMedias.clear()
                        }
                    }
                )
            }
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
                        loadAuthorInformations = { userId ->
                            messageViewModel.loadUserInformations(userId)
                        },
                        authors = authors,
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