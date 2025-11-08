package com.example.esigram.ui.components.friends

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.esigram.models.CorrectUserToDelete

@Composable
fun AddFriendRow() {
    val selectedUser = remember { mutableStateOf<CorrectUserToDelete?>(null) }
    val showDialog = remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(16.dp)) {
        FriendSearchOverlay(
            allUsers = listOf(
                CorrectUserToDelete("1", "Alice", "a@test.com"),
                CorrectUserToDelete("2", "Bob", "b@test.com"),
            ), modifier = Modifier.fillMaxWidth(), onUserSelected = { user ->
                selectedUser.value = user
                showDialog.value = true
            })
    }

    if (showDialog.value && selectedUser.value != null) {
        AddFriendDialog(user = selectedUser.value!!, onAdd = {
            showDialog.value = false
            selectedUser.value = null
        }, onCancel = {
            showDialog.value = false
            selectedUser.value = null
        })
    }
}


@Composable
@Preview
fun FriendAddRowPreview() {
    AddFriendRow()
}