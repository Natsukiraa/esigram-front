package com.example.esigram.ui.components.friends

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.esigram.ui.theme.EsigramExtraColors

@Composable
fun FriendSearchField(
    modifier: Modifier = Modifier,
    query: String,
    onQueryChanged: (String) -> Unit,
) {

    TextField(
        modifier = modifier.fillMaxWidth(),
        value = query,
        onValueChange = onQueryChanged,
        placeholder = { Text("Search for friends...") },
        singleLine = true,
        leadingIcon = {
            Icon(
                Icons.Default.Search, contentDescription = "Search icon"
            )
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        shape = RoundedCornerShape(42.dp)
    )
}

@Composable
@Preview
fun FriendSearchPreview() {
    FriendSearchField(
        query = "", onQueryChanged = {})
}