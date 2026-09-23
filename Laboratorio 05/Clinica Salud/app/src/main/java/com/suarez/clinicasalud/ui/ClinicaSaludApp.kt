package com.suarez.clinicasalud.ui

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.suarez.clinicasalud.model.Cita
import com.suarez.clinicasalud.model.EstadoCita
import com.suarez.clinicasalud.model.Medico
import com.suarez.clinicasalud.model.citasIniciales
import com.suarez.clinicasalud.ui.components.DrawerContent
import com.suarez.clinicasalud.ui.screens.AgendarCitaScreen
import com.suarez.clinicasalud.ui.screens.ConfirmacionScreen
import com.suarez.clinicasalud.ui.screens.HistorialMedicoScreen
import com.suarez.clinicasalud.ui.screens.InicioScreen
import com.suarez.clinicasalud.ui.screens.MisCitasScreen
import com.suarez.clinicasalud.ui.screens.PerfilMedicoScreen
import kotlinx.coroutines.launch

object Rutas {
    const val INICIO = "inicio"
    const val PERFIL_MEDICO = "perfil_medico"
    const val AGENDAR_CITA = "agendar_cita"
    const val CONFIRMACION = "confirmacion"
    const val MIS_CITAS = "mis_citas"
    const val HISTORIAL_MEDICO = "historial_medico"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClinicaSaludApp() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    var citas by remember { mutableStateOf(citasIniciales) }

    var medicoElegido by remember { mutableStateOf<Medico?>(null) }
    var fechaElegida by remember { mutableStateOf<Pair<String, String>?>(null) }
    var horaElegida by remember { mutableStateOf<String?>(null) }

    val backStackEntry by navController.currentBackStackEntryAsState()
    val rutaActual = backStackEntry?.destination?.route ?: Rutas.INICIO
    val destinoDrawerActual = when (rutaActual) {
        Rutas.MIS_CITAS -> "Mis citas"
        Rutas.HISTORIAL_MEDICO -> "Historial médico"
        else -> "Inicio"
    }

    fun navegarDesdeDrawer(destino: String) {
        scope.launch { drawerState.close() }
        when (destino) {
            "Inicio" -> navController.navigate(Rutas.INICIO) { popUpTo(Rutas.INICIO) { inclusive = true } }
            "Mis citas" -> navController.navigate(Rutas.MIS_CITAS)
            "Historial médico" -> navController.navigate(Rutas.HISTORIAL_MEDICO)
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
                destinoActual = destinoDrawerActual,
                onDestinoClick = { navegarDesdeDrawer(it) }
            )
        }
    ) {
        NavHost(navController = navController, startDestination = Rutas.INICIO) {

            composable(Rutas.INICIO) {
                InicioScreen(
                    onMenuClick = { scope.launch { drawerState.open() } },
                    onMedicoClick = { medico ->
                        medicoElegido = medico
                        navController.navigate(Rutas.PERFIL_MEDICO)
                    }
                )
            }

            composable(Rutas.PERFIL_MEDICO) {
                medicoElegido?.let { medico ->
                    PerfilMedicoScreen(
                        medico = medico,
                        onBack = { navController.popBackStack() },
                        onAgendarCita = { navController.navigate(Rutas.AGENDAR_CITA) }
                    )
                }
            }

            composable(Rutas.AGENDAR_CITA) {
                medicoElegido?.let { medico ->
                    AgendarCitaScreen(
                        medico = medico,
                        onBack = { navController.popBackStack() },
                        onConfirmar = { diaSemana, numeroDia, hora ->
                            fechaElegida = diaSemana to numeroDia
                            horaElegida = hora
                            // Se agrega la nueva cita a la lista de "Mis citas"
                            citas = citas + Cita(
                                medico = medico,
                                fecha = "$diaSemana $numeroDia",
                                hora = hora,
                                estado = EstadoCita.CONFIRMADA
                            )
                            navController.navigate(Rutas.CONFIRMACION)
                        }
                    )
                }
            }

            composable(Rutas.CONFIRMACION) {
                val medico = medicoElegido
                val fecha = fechaElegida
                val hora = horaElegida
                if (medico != null && fecha != null && hora != null) {
                    ConfirmacionScreen(
                        medico = medico,
                        diaSemana = fecha.first,
                        numeroDia = fecha.second,
                        hora = hora,
                        onVerMisCitas = {
                            navController.navigate(Rutas.MIS_CITAS) {
                                popUpTo(Rutas.INICIO)
                            }
                        }
                    )
                }
            }

            composable(Rutas.MIS_CITAS) {
                MisCitasScreen(
                    citas = citas,
                    onMenuClick = { scope.launch { drawerState.open() } }
                )
            }

            composable(Rutas.HISTORIAL_MEDICO) {
                HistorialMedicoScreen(
                    citas = citas,
                    onMenuClick = { scope.launch { drawerState.open() } }
                )
            }
        }
    }
}
