package com.suarez.saludplus.ui.screens.citas

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
fun DetalleCitaScreen(navController: NavController, citaId: Int) {
    val cita = Repositorio.obtenerCita(citaId)
    val medico = cita?.let { Repositorio.obtenerMedico(it.medicoId) }
    val esp = medico?.let { Repositorio.obtenerEspecialidad(it.especialidadId) }
    var mostrarDialogo by remember { mutableStateOf(false) }

    Scaffold(topBar = { BarraSuperior("Detalle de cita", navController) }) { pad ->
        Column(Modifier.padding(pad).padding(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            if (cita == null) {
                EstadoVacio("La cita ya no existe")
            } else {
                Card(Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                    Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(medico?.nombre ?: "", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                        Text(esp?.nombre ?: "", color = MaterialTheme.colorScheme.outline)
                        Spacer(Modifier.height(8.dp))
                        Text("📅 ${formatearFecha(cita.fecha)}")
                        Text("🕒 ${cita.hora}")
                        Text("💳 S/ ${medico?.precio?.toInt() ?: 0}")
                    }
                }
                Spacer(Modifier.weight(1f))
                Button(
                    onClick = { mostrarDialogo = true },
                    modifier = Modifier.fillMaxWidth().height(52.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) { Text("Cancelar cita") }
            }
        }
    }

    if (mostrarDialogo) {
        AlertDialog(
            onDismissRequest = { mostrarDialogo = false },
            title = { Text("¿Cancelar cita?") },
            text = { Text("Esta acción no se puede deshacer.") },
            confirmButton = {
                TextButton(onClick = {
                    Repositorio.cancelarCita(citaId)
                    mostrarDialogo = false
                    navController.popBackStack()
                }) { Text("Sí, cancelar") }
            },
            dismissButton = { TextButton(onClick = { mostrarDialogo = false }) { Text("No") } }
        )
    }
}
