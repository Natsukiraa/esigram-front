package com.example.esigram.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.esigram.models.Conversation
import com.example.esigram.models.ConversationFilterType
import com.example.esigram.repositories.ConversationRepository

class ConversationViewModel: ViewModel() {
    private val repo = ConversationRepository()
    private val _conversations = mutableStateListOf<Conversation>().apply { addAll(repo.getAll()) }

    var searchQuery by mutableStateOf("")
    var selectedFilter by mutableStateOf(ConversationFilterType.ALL)

    val filteredConversations: List<Conversation>
        get() = filterConversations()

    private fun filterConversations(): List<Conversation> {
        val normalizedQuery = searchQuery.trim().lowercase()

        var result = _conversations.toList()

        if (normalizedQuery.isNotEmpty()) {
            result = result.filter { conv ->
                conv.participants.any { user ->
                    val fullName1 = "${user.name} ${user.forename}".lowercase()
                    val fullName2 = "${user.forename} ${user.name}".lowercase()
                    fullName1.contains(normalizedQuery) || fullName2.contains(normalizedQuery)
                }
            }
        }

        result = when (selectedFilter) {
            ConversationFilterType.ALL -> result
            ConversationFilterType.UNREAD -> result.filter { it.unreadCount > 0 }
        }

        return result.sortedByDescending { it.createdAt }
    }
}