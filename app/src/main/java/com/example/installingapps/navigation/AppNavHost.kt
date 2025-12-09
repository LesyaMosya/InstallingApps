package com.example.installingapps.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.installingapps.ui.screen.AppDetailsScreen
import com.example.installingapps.ui.screen.AppsListScreen

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController()
) {

    NavHost(
        navController = navController,
        startDestination = Destination.Home.route
    ) {
        composable(Destination.Home.route) {
            AppsListScreen(
                navController = navController
            )
        }
        composable(Destination.Details.route) {
            AppDetailsScreen(
                navController = navController
            )
        }
    }
}