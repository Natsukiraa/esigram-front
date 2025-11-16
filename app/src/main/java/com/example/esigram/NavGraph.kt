package com.example.esigram

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.esigram.ui.screens.HomeScreen
import com.example.esigram.ui.screens.AuthScreen
import com.example.esigram.ui.screens.CompleteProfileScreen
import com.example.esigram.ui.screens.ConversationListScreen
import com.example.esigram.ui.screens.ConversationScreen
import com.example.esigram.ui.screens.ProfileScreen
import com.example.esigram.viewModels.AuthViewModel
import com.example.esigram.viewModels.CompleteProfileViewModel
import com.example.esigram.viewModels.ConversationViewModel
import com.example.esigram.viewModels.MessageViewModel
import com.example.esigram.viewModels.ProfileViewModel

@Composable
fun NavGraph(
    authViewModel: AuthViewModel,
    completeProfileViewModel: CompleteProfileViewModel,
    convViewModel: ConversationViewModel,
    messageViewModel: MessageViewModel,
    profileViewModel: ProfileViewModel
){
    val navController = rememberNavController()

    val startDestination = when {
        !authViewModel.isUserLoggedIn() -> Destinations.AUTH
        else -> Destinations.HOME
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Destinations.HOME) {
            HomeScreen(
                profileViewModel = profileViewModel,
                convViewModel = convViewModel,
                onNavigateProfile = {
                    navController.navigate(Destinations.PROFILE)
                },
                sessionManager = authViewModel.sessionManager
            )
        }

        composable(Destinations.COMPLETE_PROFILE) {
            CompleteProfileScreen(
                completeProfileViewModel = completeProfileViewModel,
                onSuccessSignUp = {
                    navController.navigate(Destinations.HOME) {
                        popUpTo(0)
                    }
                },
                saveUser = {
                    authViewModel.saveUserSession()
                }
            )
        }

        composable(Destinations.AUTH) {
            AuthScreen (
                authViewModel = authViewModel,
                onSuccessSignIn = {
                    navController.navigate(Destinations.HOME) {
                        popUpTo(0)
                    }
                },
                onSignUp = {
                    navController.navigate(Destinations.COMPLETE_PROFILE) {
                        popUpTo(0)
                    }
                }
            )
        }

        composable(route = Destinations.PROFILE) {
            ProfileScreen(
                profileViewModel = profileViewModel,
                onBackClick = {
                    navController.navigate(Destinations.HOME) {
                        popUpTo(0)
                    }
                },
                onSignOut = {
                    authViewModel.signOut()
                    navController.navigate(Destinations.AUTH) {
                        popUpTo(0)
                    }
                }
            )
        }

        composable(route = Destinations.CONVERSATION) {
            ConversationListScreen(
                conversationViewModel = convViewModel,
                onOpenMessage = { convId ->
                    Log.d("ConversationList", "Opening conversation with ID: $convId")
                    navController.navigate("${Destinations.MESSAGE}/$convId")
                },
            )
        }

        composable(
            route = "${Destinations.MESSAGE}/{ConvId}",
            arguments = listOf(navArgument("ConvId") { type = NavType.StringType})
        ) { backStackEntry ->
            val convId = backStackEntry.arguments?.getString("ConvId") ?: ""
            ConversationScreen(
                messageViewModel = messageViewModel,
                chatId = convId
            )
        }
    }
}