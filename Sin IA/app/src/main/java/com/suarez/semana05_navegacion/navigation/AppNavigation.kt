package com.suarez.semana05_navegacion.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.suarez.semana05_navegacion.screens.DetailScreen
import com.suarez.semana05_navegacion.screens.HomeScreen
import com.suarez.semana05_navegacion.screens.ListScreen
import com.suarez.semana05_navegacion.screens.ProfileScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        // Ruta 1: Home
        composable(route = Screen.Home.route) {
            HomeScreen(navController = navController)
        }

        // Ruta 2: Lista
        composable(route = Screen.List.route) {
            ListScreen(navController = navController)
        }

        // Ruta 3: Perfil
        composable(route = Screen.Profile.route) {
            ProfileScreen(navController = navController)
        }

        // Ruta 4: Detalle (Con argumento tipo Int)
        composable(
            route = Screen.Detail.route,
            arguments = listOf(
                navArgument(name = "itemId") {
                    type = NavType.IntType
                    defaultValue = 0
                }
            )
        ) { backStackEntry ->
            val itemId = backStackEntry.arguments?.getInt("itemId") ?: 0
            DetailScreen(navController = navController, itemId = itemId)
        }
    }
}