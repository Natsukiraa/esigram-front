package com.example.esigram.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.esigram.R
import com.example.esigram.ui.components.ConversationFilter
import com.example.esigram.ui.components.ConversationItem
import com.example.esigram.ui.components.ConversationSearch
import com.example.esigram.viewModels.ConversationViewModel

@Composable
fun ConversationListScreen(
    conversationViewModel: ConversationViewModel,
    onOpenMessage: (String) -> Unit
) {
    val context = LocalContext.current

    val conversations = conversationViewModel.filteredConversations
    val searchQuery = conversationViewModel.searchQuery


    Surface {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(9.dp)
        )
        {
            Text(
                context.getString(R.string.message),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.textPrimary)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ConversationSearch(modifier = Modifier.weight(0.8f), searchQuery) { newValue ->
                    conversationViewModel.searchQuery = newValue
                }

                ConversationFilter(modifier = Modifier.weight(0.2f)) { selectedFilter ->
                    conversationViewModel.selectedFilter = selectedFilter
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            /*
            LazyHorizontalGrid(
                rows = GridCells.Fixed(1),
                modifier = Modifier.height(80.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(storyViewModel.stories) { story ->
                    StoryItem(story, Modifier.size(54.dp))
                }
            } */

            Spacer(modifier = Modifier.height(24.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(1),
                modifier = Modifier.padding(horizontal = 4.dp)
            ) {
                items(conversations) { conv ->
                    ConversationItem(conversation = conv, currentUserId = "1") {
                        onOpenMessage(conv.id)
                    }
                }
            }
        }
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview
@Composable
fun ConversationListScreenPreview() {
    val view = ConversationViewModel()

    ConversationListScreen(
        conversationViewModel = view,
        onOpenMessage = {}
    )
}