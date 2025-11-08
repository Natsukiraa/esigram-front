package com.example.esigram.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.esigram.R
import com.example.esigram.models.ConversationFilterType
import com.example.esigram.ui.theme.EsigramExtraColors

@Composable
fun ConversationFilter(
    modifier: Modifier = Modifier,
    onFilterSelected: (ConversationFilterType) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .size(48.dp)
            .clip(CircleShape)
            .background(EsigramExtraColors.chatBubble)
            .clickable { expanded = true },
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            painter = painterResource(R.drawable.outline_filter_list_24),
            contentDescription = "Filter"
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            ConversationFilterType.entries.forEach { filterType ->
                DropdownMenuItem(
                    text = { Text(context.getString(filterType.resId)) },
                    onClick = {
                        onFilterSelected(filterType)
                        expanded = false
                    }
                )
            }
        }
    }

}


@Preview
@Composable
fun ConversationFilterPreview() {
    ConversationFilter(Modifier, {})
}