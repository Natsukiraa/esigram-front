package com.example.esigram.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.esigram.models.CorrectUserToDelete
import com.example.esigram.ui.components.friends.AddFriendRow
import com.example.esigram.ui.components.friends.FriendList

@Composable
fun FriendsScreen() {
    Column {
        AddFriendRow()
        FriendList(friends = listOf(
            CorrectUserToDelete("1", "Alice", "a@.fr"),
            CorrectUserToDelete("1", "Alice", "a@.fr"),
            CorrectUserToDelete("1", "Alice", "a@.fr"),
            CorrectUserToDelete("1", "Alice", "a@.fr"),
            CorrectUserToDelete("1", "Alice", "a@.fr")
            ),

        )
    }
}

@Composable
@Preview(showBackground = true)
fun FriendsScreenPreview() {
    FriendsScreen()
}