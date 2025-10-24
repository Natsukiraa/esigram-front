package com.example.esigram

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.esigram.models.Message
import com.example.esigram.ui.screens.HomeScreen
import com.example.esigram.ui.screens.AuthScreen
import com.example.esigram.ui.screens.ConversationListScreen
import com.example.esigram.ui.screens.ConversationScreen
import com.example.esigram.viewModels.AuthViewModel
import com.example.esigram.viewModels.ConversationViewModel
import com.example.esigram.viewModels.MessageViewModel

@Composable
fun NavGraph(
    authViewModel: AuthViewModel,
    convViewModel: ConversationViewModel){
    val navController = rememberNavController()

    val startDestination = if (authViewModel.isUserLoggedIn()) {
        Destinations.HOME
    } else {
        Destinations.AUTH
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

        composable(Destinations.AUTH) {
            AuthScreen (
                viewModel = authViewModel,
                onSuccessSignIn = {
                    navController.navigate(Destinations.HOME)
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
                chatId = convId
            )
        }
    }
}