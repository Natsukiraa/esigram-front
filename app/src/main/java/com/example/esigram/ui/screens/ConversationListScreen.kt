package com.example.esigram.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.esigram.ui.components.ConversationItem
import com.example.esigram.viewModels.ConversationViewModel

@Composable
fun ConversationListScreen(
    conversationViewModel: ConversationViewModel
) {


    LazyVerticalGrid(
        columns = GridCells.Fixed(1),
        modifier = Modifier.padding(horizontal = 4.dp)
    ) {
        items(conversationViewModel.conversation) { conv ->
            ConversationItem(conv)
        }
    }
}

@Preview
@Composable
fun ConversationListScreenPreview() {

}