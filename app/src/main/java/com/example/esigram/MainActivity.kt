package com.example.esigram

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.esigram.datas.repositories.AuthRepositoryImpl
import com.example.esigram.datas.repositories.ConversationRepositoryImpl
import com.example.esigram.datas.repositories.MessageRepositoryImpl
import com.example.esigram.datas.repositories.UserRepositoryImpl
import com.example.esigram.domains.repositories.ConversationRepository
import com.example.esigram.ui.theme.EsigramTheme
import com.example.esigram.domains.usecase.auth.AuthUseCases
import com.example.esigram.domains.usecase.auth.GetCurrentUserUseCase
import com.example.esigram.domains.usecase.auth.GetUserIdTokenUseCase
import com.example.esigram.domains.usecase.auth.SignOutUseCase
import com.example.esigram.domains.usecase.conversation.ConversationUseCases
import com.example.esigram.domains.usecase.conversation.GetAllUseCase
import com.example.esigram.domains.usecase.conversation.GetByIdUseCase
import com.example.esigram.domains.usecase.conversation.ObserveConversationUseCase
import com.example.esigram.domains.usecase.message.CreateMessageUseCase
import com.example.esigram.domains.usecase.message.DeleteMessageUseCase
import com.example.esigram.domains.usecase.message.ListenMessagesUseCase
import com.example.esigram.domains.usecase.message.MessageUseCases
import com.example.esigram.domains.usecase.user.GetMeCase
import com.example.esigram.domains.usecase.user.RegisterUserToDBUseCase
import com.example.esigram.domains.usecase.user.UserUseCases
import com.example.esigram.viewModels.AuthViewModel
import com.example.esigram.viewModels.CompleteProfileViewModel
import com.example.esigram.viewModels.ConversationViewModel
import com.example.esigram.viewModels.MessageViewModel
import com.example.esigram.viewModels.ProfileViewModel

class MainActivity : ComponentActivity() {
    // auth repo implem
    private val authRepository: AuthRepositoryImpl = AuthRepositoryImpl()
    private val authUseCases: AuthUseCases = AuthUseCases(
        getCurrentUserUseCase = GetCurrentUserUseCase(authRepository),
        signOutUseCase = SignOutUseCase(authRepository),
        getUserIdTokenUseCase = GetUserIdTokenUseCase(authRepository),
    )

    // user repo implem
    private val userRepository: UserRepositoryImpl = UserRepositoryImpl()
    private val userUseCases: UserUseCases = UserUseCases(
        getMeCase = GetMeCase(userRepository),
        registerUserToDBUseCase = RegisterUserToDBUseCase(userRepository)
    )

    // conv repo implem
    private val conversationRepository: ConversationRepository = ConversationRepositoryImpl()
    private val conversationUseCases: ConversationUseCases = ConversationUseCases(
        getAllUseCase = GetAllUseCase(conversationRepository),
        getByIdUseCase = GetByIdUseCase(conversationRepository),
        observeConversationUseCase = ObserveConversationUseCase(conversationRepository)
    )

    // message repo implem
    private val messageRepository: MessageRepositoryImpl = MessageRepositoryImpl()
    private val messageUseCases: MessageUseCases = MessageUseCases(
        listenMessagesUseCase = ListenMessagesUseCase(messageRepository),
        createMessageUseCase = CreateMessageUseCase(messageRepository),
        deleteMessageUseCase = DeleteMessageUseCase(messageRepository)
    )

    private val authViewModel: AuthViewModel = AuthViewModel(authUseCases)
    private val completeProfileViewModel: CompleteProfileViewModel = CompleteProfileViewModel(userUseCases)
    private val conversationViewModel: ConversationViewModel = ConversationViewModel(conversationUseCases)
    private val messageViewModel: MessageViewModel = MessageViewModel(messageUseCases)
    private val profileViewModel: ProfileViewModel = ProfileViewModel(userUseCases)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EsigramTheme {
                Scaffold { innerPadding ->
                    Surface(
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        NavGraph(
                            authViewModel = authViewModel,
                            convViewModel = conversationViewModel,
                            completeProfileViewModel = completeProfileViewModel,
                            messageViewModel = messageViewModel,
                            profileViewModel = profileViewModel
                        )
                    }
                }
            }
        }
    }
}