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
import com.example.esigram.domains.models.TmpUser
import com.example.esigram.models.CorrectUserToDelete

@Composable
fun FriendSearchOverlay(
    modifier: Modifier = Modifier,
    allUsers: List<TmpUser>,
    query: String,
    onQueryChanged: (String) -> Unit,
    onUserSelected: (TmpUser) -> Unit
) {
    // TODO : Fix overlay not overlaying


    Box(modifier = modifier.fillMaxWidth()) {
        Column {
            FriendSearchField(
                query = query, onQueryChanged = onQueryChanged
            )
        }

        if (query.isNotBlank() && allUsers.isNotEmpty()) {

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 240.dp)
                    .padding(top = 56.dp)
                    .background(MaterialTheme.colorScheme.surface)
            ) {
                items(allUsers) { user ->
                    FriendSearchResultItem(user = user, onClick = { onUserSelected(it) })
                }
            }
        } else if (query.isNotBlank() && allUsers.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 56.dp)
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(16.dp)
            ) {
                Text(
                    text = "No users found",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}