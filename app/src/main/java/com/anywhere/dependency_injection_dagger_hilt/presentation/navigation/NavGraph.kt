package com.anywhere.dependency_injection_dagger_hilt.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.anywhere.dependency_injection_dagger_hilt.presentation.event_details.EventDetailScreen
import com.anywhere.dependency_injection_dagger_hilt.presentation.event_list.EventListingScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController, startDestination = "list") {
        composable("list") {
            EventListingScreen(navController)
        }
        composable(
            route = "detail/{eventId}",
            arguments = listOf(navArgument("eventId") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("eventId") ?: 0
            EventDetailScreen(id)
        }
    }
}
