package com.suarez.semana05_navegacion.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.suarez.semana05_navegacion.screens.*

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        // Ruta: Login
        composable(route = Screen.Login.route) {
            LoginScreen(navController = navController)
        }

        // Ruta: Home
        composable(route = Screen.Home.route) {
            HomeScreen(navController = navController)
        }

        // Ruta: Directorio
        composable(route = Screen.Directory.route) {
            DirectoryScreen(navController = navController)
        }

        // Ruta: Perfil
        composable(route = Screen.Profile.route) {
            ProfileScreen(navController = navController)
        }

        // Ruta: Expediente Académico
        composable(
            route = Screen.AcademicRecord.route,
            arguments = listOf(
                navArgument(name = "studentId") {
                    type = NavType.IntType
                    defaultValue = 0
                }
            )
        ) { backStackEntry ->
            val studentId = backStackEntry.arguments?.getInt("studentId") ?: 0
            AcademicRecordScreen(navController = navController, studentId = studentId)
        }
    }
}
