package com.example.esigram.ui.components.conversations

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
    onValidate: (selectedFriends: List<String>, groupName: String?) -> Unit
) {
    var selectedFriends by remember { mutableStateOf(setOf<String>()) }
    var groupName by remember { mutableStateOf("") }
    val isGroupCreation = selectedFriends.size > 1

    Dialog(onDismissRequest = onCancel) {
        Surface(
            shape = RoundedCornerShape(28.dp),
            color = MaterialTheme.colorScheme.surfaceContainerHigh,
            shadowElevation = 16.dp,
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .heightIn(min = 400.dp, max = 640.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp)
            ) {
                Text(
                    text = if (isGroupCreation) stringResource(R.string.create_group_title) else stringResource(R.string.select_friends_title),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Divider(
                    color = MaterialTheme.colorScheme.outlineVariant,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                Text(
                    text = stringResource(
                        R.string.selected_friends_count, selectedFriends.size
                    ),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    if (friends.isEmpty()) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            CircularProgressIndicator(
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(Modifier.height(12.dp))
                            Text(
                                text = stringResource(R.string.loading_friends),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
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

                if (isGroupCreation) {
                    GroupCreationInput(
                        groupName = groupName,
                        onGroupNameChange = { groupName = it }
                    )
                    Spacer(Modifier.height(16.dp))
                }

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
                        onClick = {
                            onValidate(
                                selectedFriends.toList(),
                                if (isGroupCreation && groupName.isNotBlank()) groupName else null
                            )
                        },
                        enabled = selectedFriends.isNotEmpty()
                                && (!isGroupCreation || groupName.isNotBlank()),
                        modifier = Modifier.height(48.dp),
                        shape = RoundedCornerShape(12.dp),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
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

@Composable
fun GroupCreationInput(
    groupName: String,
    onGroupNameChange: (String) -> Unit
) {
    OutlinedTextField(
        value = groupName,
        onValueChange = onGroupNameChange,
        label = { Text(stringResource(R.string.group_name_label)) },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        },
        placeholder = { Text("azd")},
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        singleLine = true
    )
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
            onValidate = { selectedIds, groupName ->
                println("Sélectionnés: $selectedIds, Nom du groupe: $groupName")
            }
        )
    }
}