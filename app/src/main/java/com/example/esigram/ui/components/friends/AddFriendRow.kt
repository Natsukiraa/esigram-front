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
import com.example.esigram.domains.models.TmpUser
import com.example.esigram.models.CorrectUserToDelete

@Composable
fun AddFriendRow(
    modifier: Modifier = Modifier,
    searchedUsers: List<TmpUser>,
    query: String,
    onQueryChanged: (String) -> Unit,
    onUserSelected: (TmpUser) -> Unit = {},
) {
    val selectedUser = remember { mutableStateOf<TmpUser?>(null) }
    val showDialog = remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(16.dp)) {
        FriendSearchOverlay(
            allUsers = searchedUsers,
            query = query,
            onQueryChanged = onQueryChanged,
            modifier = Modifier.fillMaxWidth(),
            onUserSelected = { user ->
                selectedUser.value = user
                showDialog.value = true
            })
    }

    if (showDialog.value && selectedUser.value != null) {
        AddFriendDialog(user = selectedUser.value!!, onAdd = {
            onUserSelected(it)
            showDialog.value = false
            selectedUser.value = null
        }, onCancel = {
            showDialog.value = false
            selectedUser.value = null
        })
    }
}

