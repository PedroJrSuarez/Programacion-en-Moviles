package com.suarez.semana05_navegacion.navigation

sealed class Screen(val route: String) {

    object Login : Screen(route = "login")

    object Home : Screen(route = "home")

    object Directory : Screen(route = "directory")

    object Profile : Screen(route = "profile")

    object AcademicRecord : Screen(route = "academic_record/{studentId}") {
        fun createRoute(studentId: Int): String = "academic_record/$studentId"
    }
}
