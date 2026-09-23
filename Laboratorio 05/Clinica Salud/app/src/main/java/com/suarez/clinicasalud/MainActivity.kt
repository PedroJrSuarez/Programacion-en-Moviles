package com.suarez.clinicasalud

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.suarez.clinicasalud.model.*
import com.suarez.clinicasalud.ui.components.DrawerContent
import com.suarez.clinicasalud.ui.screens.*
import com.suarez.clinicasalud.ui.theme.ClinicaSaludTheme
import kotlinx.coroutines.launch

private object Rutas {
    const val INICIO = "inicio"
    const val PERFIL_MEDICO = "perfil_medico"
    const val AGENDAR_CITA = "agendar_cita"
    const val CONFIRMACION = "confirmacion"
    const val MIS_CITAS = "mis_citas"
    const val HISTORIAL_MEDICO = "historial_medico"
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ClinicaSaludTheme {
                ClinicaSaludApp()
            }
        }
    }
}

@OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
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
