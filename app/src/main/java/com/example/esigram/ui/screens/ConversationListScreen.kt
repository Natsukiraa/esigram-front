package com.example.esigram.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.esigram.ui.components.conversations.AddConversationButton
import com.example.esigram.viewModels.ConversationViewModel
import androidx.compose.ui.res.stringResource
import com.example.esigram.R
import com.example.esigram.datas.local.SessionManager
import com.example.esigram.ui.components.conversations.ConversationFilter
import com.example.esigram.ui.components.conversations.ConversationFriendsSelection
import com.example.esigram.ui.components.conversations.ConversationItem
import com.example.esigram.ui.components.conversations.ConversationSearch
import com.example.esigram.ui.components.home.MainMenuTopBar

@Composable
fun ConversationListScreen(
    conversationViewModel: ConversationViewModel,
    onOpenMessage: (String) -> Unit,
    sessionManager: SessionManager,
    onNavigateProfile: () -> Unit,
    onNavigateFriendsList: () -> Unit
) {
    val conversations = conversationViewModel.filteredConversations
    val searchQuery = conversationViewModel.searchQuery

    val friendsState by conversationViewModel.friends.collectAsState()
    val friends = friendsState.data
    val userId = conversationViewModel.userId
    var showFriendDialog by remember { mutableStateOf(false) }

    Surface(
        color = MaterialTheme.colorScheme.background
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(9.dp)
            ) {
                MainMenuTopBar(
                    onNavigateProfile = onNavigateProfile,
                    sessionManager = sessionManager,
                    onNavigateFriendsList = onNavigateFriendsList,
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ConversationSearch(modifier = Modifier.weight(0.8f), searchQuery) { newValue ->
                        conversationViewModel.searchQuery = newValue
                    }

                    ConversationFilter { selectedFilter ->
                        conversationViewModel.selectedFilter = selectedFilter
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                Spacer(modifier = Modifier.height(24.dp))

                LazyVerticalGrid(
                    columns = GridCells.Fixed(1),
                    modifier = Modifier.padding(horizontal = 4.dp)
                ) {
                    items(conversations, key = { it.id }) { conv ->
                        ConversationItem(
                            conversation = conv,
                            currentUserId = userId,
                            modifier = Modifier.fillMaxWidth(),
                            onClick = { onOpenMessage(conv.id) }
                        )
                    }
                }
            }

            AddConversationButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
            ) {
                showFriendDialog = true
                conversationViewModel.refreshFriend()
            }

            if (showFriendDialog) {
                ConversationFriendsSelection(
                    friends = friends,
                    onCancel = { showFriendDialog = false },
                    onValidate = { selectedIds, groupName ->
                        showFriendDialog = false
                        conversationViewModel.createConversation(selectedIds, groupName) { convId ->
                            onOpenMessage(convId)
                        }
                    }
                )
            }
        }
    }
}