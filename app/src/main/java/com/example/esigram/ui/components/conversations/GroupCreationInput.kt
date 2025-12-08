package com.example.esigram.ui.components.conversations

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.esigram.R

@Composable
fun GroupCreationInput(
    groupName: String,
    onGroupNameChange: (String) -> Unit
) {
    OutlinedTextField(
        value = groupName,
        onValueChange = onGroupNameChange,
        label = { Text(stringResource(R.string.group_name_label)) },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        singleLine = true
    )
}