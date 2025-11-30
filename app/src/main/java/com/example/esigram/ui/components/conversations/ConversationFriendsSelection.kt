package com.example.esigram.ui.components.conversations

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
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
            shape = RoundedCornerShape(16.dp),
            color = Color.White,
            modifier = Modifier
                .width(320.dp)
                .height(480.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {

                Text(
                    text = "Sélectionner des amis",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(Modifier.height(12.dp))

                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
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

                Spacer(Modifier.height(20.dp))

                // Boutons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onCancel) {
                        Text("Annuler")
                    }
                    Spacer(Modifier.width(8.dp))
                    Button(
                        onClick = { onValidate(selectedFriends.toList()) }
                    ) {
                        Text("Valider")
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

    ConversationFriendsSelection(
        friends = friends,
        onCancel = {},
        onValidate = { selectedIds ->
            println("Sélectionnés: $selectedIds")
        }
    )
}