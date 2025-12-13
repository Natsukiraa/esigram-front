package com.example.esigram.ui.components.conversations

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.esigram.R
import com.example.esigram.domains.models.ConversationFilterType
import androidx.compose.ui.res.stringResource

@Composable
fun ConversationFilter(
    onFilterSelected: (ConversationFilterType) -> Unit
) {
    val colorScheme = MaterialTheme.colorScheme

    var expanded by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .size(48.dp)
            .clip(CircleShape)
            .background(colorScheme.surfaceVariant)
            .clickable { expanded = true },
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            painter = painterResource(R.drawable.outline_filter_list_24),
            contentDescription = "Filter",
            tint = colorScheme.onSurfaceVariant
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            ConversationFilterType.entries.forEach { filterType ->
                DropdownMenuItem(
                    text = { Text(stringResource(filterType.resId)) },
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
    ConversationFilter({})
}