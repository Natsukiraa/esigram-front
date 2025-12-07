package com.example.esigram.viewModels

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.esigram.datas.local.SessionManager
import com.example.esigram.domains.models.Conversation
import com.example.esigram.domains.models.ConversationFilterType
import com.example.esigram.domains.models.User
import com.example.esigram.domains.models.responses.PageModel
import com.example.esigram.domains.models.responses.PageModel.Companion.createEmptyPageModel
import com.example.esigram.domains.usecase.conversation.ConversationUseCases
import com.example.esigram.domains.usecase.friend.FriendUseCases
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class ConversationViewModel(
    private val conversationUseCases: ConversationUseCases,
    private val friendUseCases: FriendUseCases,
    context: Context
) : ViewModel() {

    private val sessionManager = SessionManager(context)
    private var _userId by mutableStateOf("")
    val userId: String
        get() = _userId
    private val _conversations = mutableStateListOf<Conversation>()
    private val conversationJobs = mutableListOf<Job>()
    private val _friends = MutableStateFlow(createEmptyPageModel<User>())
    val friends: StateFlow<PageModel<User>> = _friends

    var searchQuery by mutableStateOf("")
    var selectedFilter by mutableStateOf(ConversationFilterType.ALL)

    val filteredConversations: List<Conversation>
        get() = filterConversations()


    init {
        viewModelScope.launch {
            Log.d("conversation", "${_friends.value}")
            try {
                _userId = sessionManager.id.first()
                val ids = conversationUseCases.getAllUseCase(_userId)
                Log.d("Conversation", "Conversations IDs: $ids")

                ids.forEach { id ->
                    val job = launch {
                        conversationUseCases.observeConversationUseCase(id).collectLatest { conv ->
                            if (conv == null) return@collectLatest
                            val existingIndex = _conversations.indexOfFirst { it.id == id }
                            if (existingIndex >= 0) {
                                _conversations[existingIndex] = conv
                            } else {
                                _conversations.add(conv)
                            }

                        }
                    }
                    conversationJobs.add(job)
                }

            } catch (e: Exception) {
                Log.e("Conversation", "Error loading conversations", e)
            }
        }
    }

    private fun filterConversations(): List<Conversation> {
        val normalizedQuery = searchQuery.trim().lowercase()

        var result = _conversations.toList()

        if (normalizedQuery.isNotEmpty()) {
            result = result.filter { conv ->
                conv.members.any { user ->
                    user.username.contains(normalizedQuery)
                }
            }
        }

        result = when (selectedFilter) {
            ConversationFilterType.ALL -> result
            ConversationFilterType.UNREAD -> result.filter { it.unreadCount > 0 }
        }

        return result.sortedByDescending { it.createdAt }
    }

    fun refreshFriend(): Unit {
        viewModelScope.launch {
            val page = friendUseCases.getFriendsUseCase()
            _friends.value = page
        }
    }

    fun createConversation(
        ids: List<String>,
        groupName: String?,
        onOpenMessage: (String) -> Unit
    ) {
        if (ids.size > 1) {
            createGroupConversation(ids, groupName, onOpenMessage)
        } else {
            createPrivateConversation(ids[0], onOpenMessage)
        }
    }

    private fun createGroupConversation(
        ids: List<String>,
        groupName: String?,
        onOpenMessage: (String) -> Unit
    ) {
        viewModelScope.launch {
            val finalIds = ids + _userId
            val id = conversationUseCases.createGroupConversationUseCase(finalIds, groupName)
            if(id != null) {
                onOpenMessage(id)
            }
        }
    }

    private fun createPrivateConversation(
        otherId: String,
        onOpenMessage: (String) -> Unit
    ) {
        viewModelScope.launch {
            val existing = findConversationByTwoMemberIds(otherId, _userId)

            if (existing != null) {
                onOpenMessage(existing.id)
                return@launch
            }
            val id = conversationUseCases.createPrivateConversationUseCase(_userId, otherId)
            if(id != null) {
                onOpenMessage(id)
            }
        }
    }

    fun findConversationByTwoMemberIds(id1: String, id2: String): Conversation? {
        val targetIds = setOf(id1, id2)
        return _conversations.find { conversation ->
            val memberIds = conversation.members.map { it.id }.toSet()
            memberIds.size == 2 && memberIds == targetIds
        }
    }

    override fun onCleared() {
        super.onCleared()
        conversationJobs.forEach { it.cancel() }
    }

}