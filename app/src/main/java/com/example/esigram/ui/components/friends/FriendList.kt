package com.example.esigram.ui.components.friends

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.esigram.models.CorrectUserToDelete

@Composable
fun FriendList(
    friends: List<CorrectUserToDelete> = emptyList(),
    onMessageClick: (CorrectUserToDelete) -> Unit = {},
    onDeleteClick: (CorrectUserToDelete) -> Unit = {}
) {
    Surface(
        tonalElevation = 2.dp,
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Friends",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

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
        CorrectUserToDelete("1", "Alice", "alice@test.com"),
        CorrectUserToDelete("2", "Bob", "bob@test.com"),
        CorrectUserToDelete("3", "Charlie", "charlie@test.com"),
        CorrectUserToDelete("4", "Diana", "diana@test.com"),
        CorrectUserToDelete("5", "Eve", "eve@test.com"),
        CorrectUserToDelete("6", "Frank", "frank@test.com"),
    )
    FriendList(friends = fakeFriends)
}
