package com.suarez.saludplus.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.suarez.saludplus.ui.screens.agendamiento.*
import com.suarez.saludplus.ui.screens.auth.*
import com.suarez.saludplus.ui.screens.citas.*
import com.suarez.saludplus.ui.screens.home.HomeScreen
import com.suarez.saludplus.ui.screens.notificaciones.NotificacionesScreen
import com.suarez.saludplus.ui.screens.perfil.PerfilScreen
import com.suarez.saludplus.ui.screens.resultados.ResultadosScreen

private fun entero(nombre: String): NamedNavArgument = navArgument(nombre) { type = NavType.IntType }
private fun texto(nombre: String): NamedNavArgument = navArgument(nombre) { type = NavType.StringType }

@Composable
fun AppNavigation() {
    val nav = rememberNavController()

    NavHost(navController = nav, startDestination = Rutas.SPLASH) {
        composable(Rutas.SPLASH) { SplashScreen(nav) }
        composable(Rutas.REGISTRO) { RegistroScreen(nav) }
        composable(Rutas.LOGIN) { LoginScreen(nav) }
        composable(Rutas.TERMINOS) { TerminosScreen(nav) }
        composable(Rutas.HOME) { HomeScreen(nav) }
        composable(Rutas.ESPECIALIDADES) { EspecialidadesScreen(nav) }
        composable(Rutas.MEDICOS, arguments = listOf(entero("especialidadId"))) {
            MedicosScreen(nav, it.arguments!!.getInt("especialidadId"))
        }
        composable(Rutas.FECHA_HORA, arguments = listOf(entero("medicoId"))) {
            FechaHoraScreen(nav, it.arguments!!.getInt("medicoId"))
        }
        composable(
            Rutas.CONFIRMAR,
            arguments = listOf(entero("medicoId"), texto("fecha"), texto("hora"))
        ) {
            val a = it.arguments!!
            ConfirmarCitaScreen(nav, a.getInt("medicoId"), a.getString("fecha")!!, a.getString("hora")!!)
        }
        composable(Rutas.EXITO, arguments = listOf(entero("citaId"))) {
            CitaExitosaScreen(nav, it.arguments!!.getInt("citaId"))
        }
        composable(Rutas.MIS_CITAS) { MisCitasScreen(nav) }
        composable(Rutas.DETALLE, arguments = listOf(entero("citaId"))) {
            DetalleCitaScreen(nav, it.arguments!!.getInt("citaId"))
        }
        composable(Rutas.PERFIL) { PerfilScreen(nav) }
        composable(Rutas.RESULTADOS) { ResultadosScreen(nav) }
        composable(Rutas.NOTIFICACIONES) { NotificacionesScreen(nav) }
    }
}
