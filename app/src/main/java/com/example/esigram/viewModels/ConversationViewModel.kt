package com.example.esigram.viewModels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.esigram.models.Conversation
import com.example.esigram.repositories.ConversationRepository

class ConversationViewModel: ViewModel() {
    private val repo = ConversationRepository()
    private val _conversation = mutableStateListOf<Conversation>().apply { addAll(repo.getAll()) }
    val conversation: List<Conversation> = _conversation
}