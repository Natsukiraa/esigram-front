package com.example.esigram.ui.components.friends

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.esigram.models.CorrectUserToDelete

@Composable
fun FriendSearchOverlay(
    modifier: Modifier = Modifier,
    allUsers: List<CorrectUserToDelete>,
    onUserSelected: (CorrectUserToDelete) -> Unit
) {
    // TODO : Fix overlay not overlaying
    var query by remember { mutableStateOf("") }

    Box(modifier = modifier.fillMaxWidth()) {
        Column {
            FriendSearchField(
                query = query, onQueryChanged = { query = it })
        }

        val filteredUsers = remember(query) {
            allUsers.filter { it.username.startsWith(query, ignoreCase = true) }
        }

        if (filteredUsers.isNotEmpty() && query.isNotBlank()) {

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 240.dp)
                    .padding(top = 56.dp)
                    .background(MaterialTheme.colorScheme.surface)
            ) {
                items(filteredUsers) { user ->
                    FriendSearchResultItem(user = user, onClick = { onUserSelected(it) })
                }
            }
        }
    }
}


@Composable
@Preview(showBackground = true)
fun FriendSearchWithResultsPreview() {
    val fakeUsers = listOf(
        CorrectUserToDelete("1", "Azeem", "a@test.com"),
        CorrectUserToDelete("2", "Azalea", "b@test.com"),
        CorrectUserToDelete("3", "Alice", "c@test.com"),
        CorrectUserToDelete("4", "Bob", "d@test.com"),
        CorrectUserToDelete("5", "Azra", "e@test.com")
    )

    FriendSearchOverlay(allUsers = fakeUsers, onUserSelected = {})
}
