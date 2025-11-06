package com.example.esigram

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.esigram.datas.repositories.UserRepositoryImpl
import com.example.esigram.ui.screens.ConversationScreen
import com.example.esigram.ui.theme.EsigramTheme
import com.example.esigram.usecase.user.RegisterUserToDBUseCases
import com.example.esigram.usecase.user.UserUseCases
import com.example.esigram.viewModels.AuthViewModel
import com.example.esigram.viewModels.CompleteProfileViewModel
import com.example.esigram.viewModels.ConversationViewModel

class MainActivity : ComponentActivity() {
    // user repo implem
    private val userRepository: UserRepositoryImpl = UserRepositoryImpl()
    private val userUseCases: UserUseCases = UserUseCases(
        registerUserToDBUseCases = RegisterUserToDBUseCases(userRepository)
    )

    private val authViewModel: AuthViewModel by viewModels()
    private val completeProfileViewModel: CompleteProfileViewModel = CompleteProfileViewModel(userUseCases)
    private val conversationViewModel: ConversationViewModel by viewModels()
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
                            completeProfileViewModel = completeProfileViewModel
                        )
                    }
                }
            }
        }
    }
}