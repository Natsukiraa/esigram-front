package com.example.esigram.ui.components.conversations

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.esigram.R
import com.example.esigram.domains.models.User


@Composable
fun ConversationFriendsSelection(
    friends: List<User>,
    onCancel: () -> Unit,
    onValidate: (List<String>) -> Unit
) {
    var selectedFriends by remember { mutableStateOf(setOf<String>()) }

    Dialog(onDismissRequest = onCancel) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 6.dp,
            shadowElevation = 8.dp,
            modifier = Modifier
                .width(340.dp)
                .height(500.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp)
            ) {
                // En-tête
                Text(
                    text = stringResource(R.string.select_friends_title),
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurface
                )

                if (selectedFriends.isNotEmpty()) {
                    Text(
                        text = stringResource(
                            R.string.selected_friends_count,
                            selectedFriends.size
                        ),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }

                Spacer(Modifier.height(16.dp))

                // Liste des amis
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    if (friends.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                CircularProgressIndicator(
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Text(
                                    text = stringResource(R.string.loading_friends),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            items(friends) { friend ->
                                FriendItem(
                                    friend = friend,
                                    isSelected = selectedFriends.contains(friend.id),
                                    onClick = {
                                        selectedFriends =
                                            if (selectedFriends.contains(friend.id))
                                                selectedFriends - friend.id
                                            else
                                                selectedFriends + friend.id
                                    }
                                )
                            }
                        }
                    }
                }

                Spacer(Modifier.height(24.dp))

                // Boutons d'action
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.End)
                ) {
                    TextButton(
                        onClick = onCancel,
                        modifier = Modifier.height(48.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.cancel),
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                    Button(
                        onClick = { onValidate(selectedFriends.toList()) },
                        enabled = selectedFriends.isNotEmpty(),
                        modifier = Modifier.height(48.dp),
                        shape = RoundedCornerShape(12.dp),
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 2.dp,
                            pressedElevation = 4.dp
                        )
                    ) {
                        Text(
                            text = stringResource(R.string.validate),
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ConversationFriendsSelectionPreview() {
    val friends = listOf(
        User(id = "1", username = "Arthur", email = "a@test.com", profilePicture = null),
        User(id = "2", username = "Léna", email = "l@test.com", profilePicture = null),
        User(id = "3", username = "Yanis", email = "y@test.com", profilePicture = null)
    )

    MaterialTheme {
        ConversationFriendsSelection(
            friends = friends,
            onCancel = {},
            onValidate = { selectedIds ->
                println("Sélectionnés: $selectedIds")
            }
        )
    }
}