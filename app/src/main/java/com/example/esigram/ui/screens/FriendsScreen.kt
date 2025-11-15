package com.example.esigram.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.esigram.domains.models.TmpUser
import com.example.esigram.ui.components.friends.AddFriendDialog
import com.example.esigram.ui.components.friends.FriendList
import com.example.esigram.ui.components.friends.FriendRequestList
import com.example.esigram.ui.components.friends.FriendSearchField
import com.example.esigram.ui.components.friends.FriendSearchOverlay
import com.example.esigram.ui.components.utils.PullToRefreshBox
import com.example.esigram.viewModels.FriendViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FriendsScreen(
    friendViewModel: FriendViewModel
) {
    val friends by friendViewModel.friends.collectAsState()
    val searchedUsers by friendViewModel.searchedUsers.collectAsState()
    val query by friendViewModel.searchQuery.collectAsState()
    val inboundRequests by friendViewModel.inboundFriendRequests.collectAsState()
    val outboundRequests by friendViewModel.outboundFriendRequests.collectAsState()
    val isRefreshing by friendViewModel.isRefreshing.collectAsState()
    val selectedUser = remember { mutableStateOf<TmpUser?>(null) }
    val showDialog = remember { mutableStateOf(false) }

    PullToRefreshBox(
        onRefresh = { friendViewModel.refreshAllData() },
        isRefreshing = isRefreshing
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {

                FriendSearchField(
                    query = query,
                    onQueryChanged = { friendViewModel.updateSearchQuery(it) },
                    modifier = Modifier.padding(8.dp)
                )
                if (showDialog.value && selectedUser.value != null) {
                    AddFriendDialog(user = selectedUser.value!!, onAdd = {
                        friendViewModel.askFriend(it.id)
                        showDialog.value = false
                        selectedUser.value = null
                        friendViewModel.updateSearchQuery("")
                    }, onCancel = {
                        showDialog.value = false
                        selectedUser.value = null
                    })
                }
                FriendList(
                    friends = friends.data,
                    modifier = Modifier.weight(0.5f),
                    onDeleteClick = { friendViewModel.removeFriend(it.id) }
                )
                FriendRequestList(
                    inboundRequests = inboundRequests.data,
                    outboundRequests = outboundRequests.data,
                    onAcceptClick = {
                        it.userAsking?.id?.let { id ->
                            friendViewModel.askFriend(id, true)
                        }
                    },
                    onDeclineClick = {
                        it.userAsking?.id?.let { id ->
                            friendViewModel.rejectFriend(
                                id
                            )
                        }
                    },
                    modifier = Modifier.weight(0.3f)
                )
            }

            if (query.isNotBlank()) {
                FriendSearchOverlay(
                    allUsers = searchedUsers.data,
                    query = query,
                    onQueryChanged = { friendViewModel.updateSearchQuery(it) },
                    onUserSelected = { user ->
                        selectedUser.value = user
                        showDialog.value = true
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .absoluteOffset(y = 16.dp)
                        .zIndex(10f)
                        .padding(horizontal = 8.dp)
                )
            }
        }
    }
}

