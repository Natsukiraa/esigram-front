package com.example.esigram.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.esigram.ui.components.friends.AddFriendRow
import com.example.esigram.ui.components.friends.FriendList
import com.example.esigram.ui.components.friends.FriendRequestList
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

    PullToRefreshBox(
        onRefresh = { friendViewModel.refreshAllData() },
        isRefreshing = isRefreshing
    ) {
        Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
            AddFriendRow(
                searchedUsers = searchedUsers.data,
                query = query,
                onQueryChanged = { friendViewModel.updateSearchQuery(it) },
                onUserSelected = { friendViewModel.askFriend(it.id) },
                modifier = Modifier.weight(0.2f)
            )

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
                        friendViewModel.askFriend(
                            id,
                            true
                        )
                    }
                },
                onDeclineClick = { it.userAsking?.id?.let { id -> friendViewModel.rejectFriend(id) } },
                modifier = Modifier.weight(0.3f)
            )
        }
    }
}
