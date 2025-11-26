package com.example.esigram.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.example.esigram.domains.models.User
import com.example.esigram.ui.components.friends.AddFriendDialog
import com.example.esigram.ui.components.friends.FriendRequestList
import com.example.esigram.ui.components.friends.FriendSearchField
import com.example.esigram.ui.components.friends.FriendSearchOverlay
import com.example.esigram.ui.components.navigation.NavigationBar
import com.example.esigram.ui.components.utils.PullToRefreshBox
import com.example.esigram.viewModels.FriendViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddFriendsScreen(
    friendViewModel: FriendViewModel, onBack: () -> Unit = {}
) {
    val friends by friendViewModel.friends.collectAsState()
    val searchedUsers by friendViewModel.searchedUsers.collectAsState()
    val query by friendViewModel.searchQuery.collectAsState()
    val inboundRequests by friendViewModel.inboundFriendRequests.collectAsState()
    val outboundRequests by friendViewModel.outboundFriendRequests.collectAsState()
    val isRefreshing by friendViewModel.isRefreshing.collectAsState()
    val selectedUser = remember { mutableStateOf<User?>(null) }
    val showDialog = remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    PullToRefreshBox(
        onRefresh = { friendViewModel.refreshAllData() }, isRefreshing = isRefreshing
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {

                NavigationBar(
                    title = "Add friends", showBackButton = true, onBackClick = onBack
                )

                FriendSearchField(
                    query = query,
                    onQueryChanged = { friendViewModel.updateSearchQuery(it) },
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                )

                if (query.isNotBlank()) {
                    FriendSearchOverlay(
                        allUsers = searchedUsers.data.filter {
                            it.id !in friends.data.map { friend -> friend.id } && it.id !in outboundRequests.data.map { request -> request.userAsked?.id }
                        }, query = query, onUserSelected = { user ->
                            selectedUser.value = user
                            showDialog.value = true
                        }, modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp)
                    )
                } else {
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
                                friendViewModel.rejectFriend(id)
                            }
                        },
                        modifier = Modifier.weight(1f)
                    )
                }

                if (showDialog.value && selectedUser.value != null) {
                    AddFriendDialog(user = selectedUser.value!!, onAdd = {
                        friendViewModel.askFriend(it.id)
                        showDialog.value = false
                        selectedUser.value = null
                        friendViewModel.updateSearchQuery("")
                        focusManager.clearFocus()
                    }, onCancel = {
                        showDialog.value = false
                        selectedUser.value = null
                    })
                }
            }
        }
    }
}
