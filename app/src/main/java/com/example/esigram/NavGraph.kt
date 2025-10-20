package com.example.mytodoz

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.esigram.Destinations
import com.example.esigram.ui.screens.ConversationListScreen
import com.example.esigram.viewModels.ConversationViewModel

@Composable
fun NavGraph(convViewModel: ConversationViewModel){

    // STATE
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Destinations.CONVERSATION
    ) {
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
            val convId = backStackEntry.arguments?.getInt("ConvId") ?: "";
        }
    }
}