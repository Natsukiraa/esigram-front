package com.example.esigram.ui.components.friends

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.esigram.domains.models.User

@Composable
fun FriendList(
    modifier: Modifier = Modifier,
    friends: List<User> = emptyList(),
    onMessageClick: (User) -> Unit = {},
    onDeleteClick: (User) -> Unit = {}
) {
    Surface(
        tonalElevation = 2.dp,
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {


            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 350.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(friends) { user ->
                    FriendListItem(
                        user = user,
                        onMessageClick = onMessageClick,
                        onDeleteClick = onDeleteClick
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun FriendListPreview() {
    val fakeFriends = listOf(
        User("1", "Alice", "alice@test.com"),
        User("2", "Bob", "bob@test.com"),
        User("3", "Charlie", "charlie@test.com"),
        User("4", "Diana", "diana@test.com"),
        User("5", "Eve", "eve@test.com"),
        User("6", "Frank", "frank@test.com"),
    )
    FriendList(friends = fakeFriends)
}
