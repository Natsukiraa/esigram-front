package com.example.esigram

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.esigram.models.Message
import com.example.esigram.ui.screens.HomeScreen
import com.example.esigram.ui.screens.AuthScreen
import com.example.esigram.ui.screens.CompleteProfileScreen
import com.example.esigram.ui.screens.ConversationListScreen
import com.example.esigram.ui.screens.ConversationScreen
import com.example.esigram.viewModels.AuthViewModel
import com.example.esigram.viewModels.CompleteProfileViewModel
import com.example.esigram.viewModels.ConversationViewModel

@Composable
fun NavGraph(
    authViewModel: AuthViewModel,
    completeProfileViewModel: CompleteProfileViewModel,
    convViewModel: ConversationViewModel
){
    val navController = rememberNavController()
    val userExistsPsql = completeProfileViewModel.userExistsInPsql.collectAsState()

    // Check if user is logged + if user is in PSQL
    LaunchedEffect(authViewModel.isUserLoggedIn()) {
        if (authViewModel.isUserLoggedIn() && userExistsPsql.value == null) {
            completeProfileViewModel.doesUserExistsInPsql()
        }
    }

    val startDestination = when {
        !authViewModel.isUserLoggedIn() -> Destinations.AUTH
        userExistsPsql.value == null -> Destinations.HOME
        userExistsPsql.value == false -> Destinations.COMPLETE_PROFILE
        userExistsPsql.value == true -> Destinations.HOME
        else -> Destinations.AUTH
    }


    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Destinations.HOME) {
            HomeScreen(
                authViewModel = authViewModel,
                convViewModel = convViewModel,
                completeProfileViewModel = completeProfileViewModel,
                onSignOut = {
                    navController.navigate(Destinations.AUTH)
                }
            )
        }

        composable(Destinations.COMPLETE_PROFILE) {
            CompleteProfileScreen (
                completeProfileViewModel = completeProfileViewModel
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