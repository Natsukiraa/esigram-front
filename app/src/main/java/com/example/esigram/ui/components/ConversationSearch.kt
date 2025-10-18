package com.example.esigram.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.esigram.R
import com.example.esigram.ui.theme.EsigramExtraColors

@Composable
fun ConversationSearch(
    modifier: Modifier = Modifier,
    query: String,
    onQueryChanged: (String) -> Unit,
) {

    Row(
        modifier = modifier
            .height(58.dp)
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ){
        TextField(
            modifier = modifier.fillMaxWidth()
            ,
            value = query,
            onValueChange = onQueryChanged,
            placeholder = { Text(LocalContext.current.getString(R.string.search))},
            singleLine = true,
            leadingIcon = {
                Icon(
                    Icons.Default.Search,
                    contentDescription = "Search icon"
                )
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = EsigramExtraColors.chatBubble,
                unfocusedContainerColor = EsigramExtraColors.chatBubble,
                disabledContainerColor = EsigramExtraColors.chatBubble,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            ),
            shape = RoundedCornerShape(42.dp)
        )
    }

}


@Preview
@Composable
fun ConversationSearchPreview() {
    ConversationSearch(Modifier, "", {})
}