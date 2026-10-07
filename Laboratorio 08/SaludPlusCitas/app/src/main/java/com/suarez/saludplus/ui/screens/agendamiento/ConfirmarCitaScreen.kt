package com.suarez.saludplus.ui.screens.agendamiento

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.suarez.saludplus.data.repository.Repositorio
import com.suarez.saludplus.navigation.Rutas
import com.suarez.saludplus.ui.components.*

@Composable
fun ConfirmarCitaScreen(navController: NavController, medicoId: Int, fecha: String, hora: String) {
    val medico = Repositorio.obtenerMedico(medicoId)
    val esp = medico?.let { Repositorio.obtenerEspecialidad(it.especialidadId) }
    var error by remember { mutableStateOf("") }

    Scaffold(topBar = { BarraSuperior("Confirmar cita", navController) }) { pad ->
        Column(Modifier.padding(pad).padding(20.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
            Card(Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Fila("Médico", medico?.nombre ?: "-")
                    Fila("Especialidad", esp?.nombre ?: "-")
                    Fila("Fecha", formatearFecha(fecha))
                    Fila("Hora", hora)
                    Fila("Costo", "S/ ${medico?.precio?.toInt() ?: 0}")
                }
            }
            MensajeError(error)
            Spacer(Modifier.weight(1f))
            BotonPrincipal("Confirmar cita") {
                val cita = Repositorio.agendarCita(medicoId, fecha, hora)
                if (cita == null) {
                    error = "No se pudo agendar: el horario ya fue tomado o no hay sesión"
                } else {
                    // popUpTo(HOME): borra el flujo de agendamiento del historial
                    navController.navigate(Rutas.exito(cita.id)) { popUpTo(Rutas.HOME) }
                }
            }
            BotonSecundario("Cambiar horario") { navController.popBackStack() }
        }
    }
}

@Composable
private fun Fila(etiqueta: String, valor: String) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(etiqueta, color = MaterialTheme.colorScheme.outline)
        Text(valor, fontWeight = FontWeight.SemiBold)
    }
}
