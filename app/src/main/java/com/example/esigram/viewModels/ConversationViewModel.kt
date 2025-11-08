package com.example.esigram.viewModels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.esigram.domains.models.Conversation
import com.example.esigram.domains.models.ConversationFilterType
import com.example.esigram.domains.repositories.ConversationRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ConversationViewModel : ViewModel() {

    private val repo = ConversationRepository()
    private val _conversations = mutableStateListOf<Conversation>()
    private val conversationJobs = mutableListOf<Job>()

    var searchQuery by mutableStateOf("")
    var selectedFilter by mutableStateOf(ConversationFilterType.ALL)

    val filteredConversations: List<Conversation>
        get() = filterConversations()

    init {
        viewModelScope.launch {
            try {
                val ids = repo.getAll()
                Log.d("Firebase", "Conversations IDs: $ids")

                ids.forEach { id ->
                    val job = launch {
                        repo.observeConversation(id).collectLatest { conv ->
                            if (conv == null) return@collectLatest

                            val existingIndex = _conversations.indexOfFirst { it.id == id }
                            if (existingIndex >= 0) {
                                _conversations[existingIndex] = conv
                            } else {
                                _conversations.add(conv)
                            }

                            Log.d("Firebase", "Conversation mise à jour: ${conv.id}")
                        }
                    }
                    conversationJobs.add(job)
                }

            } catch (e: Exception) {
                Log.e("Firebase", "Erreur chargement conversations", e)
            }
        }
    }
    private fun filterConversations(): List<Conversation> {
        val normalizedQuery = searchQuery.trim().lowercase()

        var result = _conversations.toList()

        if (normalizedQuery.isNotEmpty()) {
            result = result.filter { conv ->
                conv.members.any { user ->
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

    override fun onCleared() {
        super.onCleared()
        conversationJobs.forEach { it.cancel() }
    }

}