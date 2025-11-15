package com.example.esigram.ui.components.friends

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
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
    Box(modifier = modifier.fillMaxWidth()) {

        if (query.isNotBlank()) {
            val popupModifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 240.dp)
                .absoluteOffset(y = 56.dp)
                .zIndex(10f)
                .background(Color(0xFF112233))

            if (allUsers.isNotEmpty()) {
                LazyColumn(modifier = popupModifier) {
                    items(allUsers) { user ->
                        FriendSearchResultItem(user = user) { onUserSelected(it) }
                    }
                }
            } else {
                Box(
                    modifier = popupModifier.padding(16.dp)
                ) {
                    Text(
                        text = "No users found",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }

}
