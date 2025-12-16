package com.example.esigram.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.esigram.R
import com.example.esigram.ui.components.friends.FriendList
import com.example.esigram.ui.components.friends.FriendSearchField
import com.example.esigram.ui.components.navigation.NavigationBar
import com.example.esigram.ui.components.utils.PullToRefreshBox
import com.example.esigram.viewModels.FriendViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FriendsScreen(
    friendViewModel: FriendViewModel, onAddFriend: () -> Unit = { }, onBack: () -> Unit = { }
) {
    val friends by friendViewModel.friends.collectAsState()
    val query by friendViewModel.searchQuery.collectAsState()
    val isRefreshing by friendViewModel.isRefreshing.collectAsState()

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
                    title = "Friends", showBackButton = true, onBackClick = onBack, endContent = {
                        IconButton(
                            onClick = onAddFriend, modifier = Modifier.fillMaxHeight()
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.person_add_24px),
                                contentDescription = "Ajouter un ami"
                            )
                        }
                    })

                FriendSearchField(
                    query = query,
                    onQueryChanged = { friendViewModel.updateSearchQuery(it) },
                    modifier = Modifier.padding(8.dp)
                )

                FriendList(
                    friends = if (query.isBlank()) friends.data else friends.data.filter {
                        it.username.contains(
                            query, ignoreCase = true
                        )
                    },
                    modifier = Modifier.weight(0.5f),
                    onDeleteClick = { friendViewModel.removeFriend(it.id) })
            }
        }
    }
}
