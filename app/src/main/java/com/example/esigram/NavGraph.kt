package com.example.esigram

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.esigram.ui.screens.HomeScreen
import com.example.esigram.ui.screens.AuthScreen
import com.example.esigram.viewModels.AuthViewModel

@Composable
fun NavGraph(viewModel: AuthViewModel){
    val navController = rememberNavController()

    val startDestination = if (viewModel.isUserLoggedIn()) {
        Destinations.HOME
    } else {
        Destinations.AUTH
    }

    NavHost(
        navController = navController,
    ) {
        startDestination = startDestination
        composable(Destinations.HOME) {
            HomeScreen(
                viewModel = viewModel,
                onSignOut = {
                    navController.navigate(Destinations.AUTH)
                }
            )
        }
        composable(Destinations.AUTH) {

            AuthScreen (
                viewModel = viewModel,
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
            arguments = listOf(navArgument("ConvId") { type = NavType.StringType})
            route = "${Destinations.MESSAGE}/{ConvId}",
        ) { backStackEntry ->
            val convId = backStackEntry.arguments?.getInt("ConvId") ?: "";
        }
    }
}