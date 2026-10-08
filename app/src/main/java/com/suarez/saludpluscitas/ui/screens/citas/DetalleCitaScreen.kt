package com.suarez.saludpluscitas.ui.screens.citas

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.suarez.saludpluscitas.data.repository.Repositorio
import com.suarez.saludpluscitas.navigation.Rutas
import com.suarez.saludpluscitas.ui.components.*
import com.suarez.saludpluscitas.ui.theme.*

@Composable
fun DetalleCitaScreen(navController: NavController, citaId: Int) {
    val cita = Repositorio.obtenerCita(citaId)
    val medico = cita?.let { Repositorio.obtenerMedico(it.medicoId) }
    var mostrarDialogo by remember { mutableStateOf(false) }

    Scaffold(containerColor = Color.White, topBar = { BarraSuperior("Detalle de cita", navController) }) { pad ->
        Column(Modifier.padding(pad).padding(horizontal = 20.dp)) {
            if (cita == null) {
                EstadoVacio("La cita ya no existe")
            } else {
                Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    CabeceraMedico(medico, mostrarCmp = true)
                    InfoFila(Icons.Default.CalendarMonth, "Fecha", formatearFecha(cita.fecha))
                    InfoFila(Icons.Default.AccessTime, "Hora", cita.hora)
                    InfoFila(Icons.Default.MedicalServices, "Tipo de atención", "Consulta presencial")
                    InfoFila(Icons.Default.LocationOn, "Dirección", "Av. Los Olivos 123, Lima")
                }
                BotonSecundario("Cancelar cita", color = Rojo) { mostrarDialogo = true }
                Spacer(Modifier.height(16.dp))
            }
        }
    }

    if (mostrarDialogo) {
        AlertDialog(
            onDismissRequest = { mostrarDialogo = false },
            containerColor = Color.White,
            title = { Text("¿Cancelar cita?", fontWeight = FontWeight.Bold, color = Navy) },
            text = { Text("Esta acción no se puede deshacer.", color = Gris) },
            confirmButton = {
                TextButton(onClick = {
                    Repositorio.cancelarCita(citaId)
                    mostrarDialogo = false
                    navController.popBackStack()
                }) { Text("Sí, cancelar", color = Rojo) }
            },
            dismissButton = { TextButton(onClick = { mostrarDialogo = false }) { Text("No", color = Azul) } }
        )
    }
}
