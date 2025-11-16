package com.example.esigram

import android.util.Log
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.esigram.ui.screens.AddFriendsScreen
import com.example.esigram.ui.screens.AuthScreen
import com.example.esigram.ui.screens.CompleteProfileScreen
import com.example.esigram.ui.screens.ConversationListScreen
import com.example.esigram.ui.screens.ConversationScreen
import com.example.esigram.ui.screens.FriendsScreen
import com.example.esigram.ui.screens.HomeScreen
import com.example.esigram.viewModels.AuthViewModel
import com.example.esigram.viewModels.CompleteProfileViewModel
import com.example.esigram.viewModels.ConversationViewModel
import com.example.esigram.viewModels.FriendViewModel
import com.example.esigram.viewModels.MessageViewModel

@Composable
fun NavGraph(
    authViewModel: AuthViewModel,
    completeProfileViewModel: CompleteProfileViewModel,
    convViewModel: ConversationViewModel,
    messageViewModel: MessageViewModel,
    friendViewModel: FriendViewModel
    ){
    val navController = rememberNavController()

    val startDestination = when {
        !authViewModel.isUserLoggedIn() -> Destinations.AUTH
        else -> Destinations.FRIENDS
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Destinations.HOME) {
            HomeScreen(
                authViewModel = authViewModel,
                convViewModel = convViewModel,
                onSignOut = {
                    navController.navigate(Destinations.AUTH)
                }
            )
        }

        composable(Destinations.COMPLETE_PROFILE) {
            CompleteProfileScreen (
                completeProfileViewModel = completeProfileViewModel,
                onSuccessSignUp = {
                    navController.navigate(Destinations.HOME) {
                        popUpTo(0)
                    }
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

        composable(
            route = Destinations.FRIENDS
        ) {
            FriendsScreen(
                friendViewModel = friendViewModel,
                onAddFriend = {
                    navController.navigate(Destinations.ADD_FRIENDS)
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = Destinations.ADD_FRIENDS,
            enterTransition = { slideInHorizontally { it } },
            exitTransition = { slideOutHorizontally { it } }) {
            AddFriendsScreen(
                friendViewModel = friendViewModel, onBack = { navController.popBackStack() })
        }
    }
}