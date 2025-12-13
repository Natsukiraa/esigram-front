package com.example.esigram.viewModels.factories

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.esigram.domains.usecase.conversation.ConversationUseCases
import com.example.esigram.domains.usecase.friend.FriendUseCases
import com.example.esigram.viewModels.ConversationViewModel

class ConversationListViewModelFactory(
    private val conversationUseCases: ConversationUseCases,
    private val friendUseCases: FriendUseCases,
    private val context: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ConversationViewModel::class.java)) {
            return ConversationViewModel(conversationUseCases, friendUseCases, context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}