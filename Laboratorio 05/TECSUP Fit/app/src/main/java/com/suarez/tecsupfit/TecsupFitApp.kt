package com.suarez.tecsupfit

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.suarez.tecsupfit.data.ReservaClase
import com.suarez.tecsupfit.data.listaClases
import com.suarez.tecsupfit.navigation.BarraInferior
import com.suarez.tecsupfit.navigation.Rutas
import com.suarez.tecsupfit.navigation.rutasConBottomBar
import com.suarez.tecsupfit.ui.screens.PantallaConfirmacion
import com.suarez.tecsupfit.ui.screens.PantallaDetalleClase
import com.suarez.tecsupfit.ui.screens.PantallaInicio
import com.suarez.tecsupfit.ui.screens.PantallaPerfil
import com.suarez.tecsupfit.ui.screens.PantallaReservas
import com.suarez.tecsupfit.ui.screens.PantallaRutinas

@Composable
fun TecsupFitApp() {
    val navController = rememberNavController()

    val reservas = remember { mutableStateListOf<ReservaClase>() }

    val backStackEntry by navController.currentBackStackEntryAsState()
    val rutaActual = backStackEntry?.destination?.route
    val mostrarBottomBar = rutaActual in rutasConBottomBar

    Scaffold(
        bottomBar = {
            if (mostrarBottomBar) {
                BarraInferior(navController = navController, rutaActual = rutaActual)
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = Rutas.Inicio.ruta,
            modifier = Modifier.padding(padding)
        ) {
            composable(Rutas.Inicio.ruta) {
                PantallaInicio(navController = navController)
            }
            composable(Rutas.Detalle.ruta) { entry ->
                val claseId = entry.arguments?.getString("claseId")?.toIntOrNull()
                val clase = listaClases.find { it.id == claseId }
                if (clase != null) {
                    PantallaDetalleClase(clase = clase, navController = navController)
                }
            }
            composable(Rutas.Confirmacion.ruta) { entry ->
                val claseId = entry.arguments?.getString("claseId")?.toIntOrNull()
                val clase = listaClases.find { it.id == claseId }
                if (clase != null) {
                    PantallaConfirmacion(
                        clase = clase,
                        navController = navController,
                        onConfirmar = {
                            val yaExiste = reservas.any { it.clase.id == clase.id }
                            if (!yaExiste) {
                                reservas.add(0, ReservaClase(clase, "Hoy, ${clase.horario}", "Confirmada"))
                            }
                        }
                    )
                }
            }
            composable(Rutas.Reservas.ruta) {
                PantallaReservas(reservas = reservas)
            }
            composable(Rutas.Rutinas.ruta) {
                PantallaRutinas()
            }
            composable(Rutas.Perfil.ruta) {
                PantallaPerfil(totalReservas = reservas.size)
            }
        }
    }
}
