package com.suarez.saludpluscitas.ui.screens.agendamiento

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

import java.time.LocalTime

@Composable
fun ConfirmarCitaScreen(navController: NavController, medicoId: Int, fecha: String, hora: String) {
    val medico = Repositorio.obtenerMedico(medicoId)
    val horaFin = LocalTime.parse(hora).plusMinutes(30).toString()
    var motivo by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }

    Scaffold(containerColor = Color.White, topBar = { BarraSuperior("Confirmar cita", navController) }) { pad ->
        Column(Modifier.padding(pad).padding(horizontal = 20.dp)) {
            Column(Modifier.weight(1f).verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                CabeceraMedico(medico, mostrarCmp = true)
                InfoFila(Icons.Default.CalendarMonth, "Fecha", formatearFecha(fecha))
                InfoFila(Icons.Default.AccessTime, "Hora", "$hora a $horaFin")
                InfoFila(Icons.Default.MedicalServices, "Tipo de atención", "Consulta presencial")
                InfoFila(Icons.Default.LocationOn, "Dirección", "Av. Los Olivos 123, Lima")
                Row {
                    Text("Motivo de consulta ", fontWeight = FontWeight.Bold, color = Navy, fontSize = 14.sp)
                    Text("(opcional)", color = Gris, fontSize = 14.sp)
                }
                OutlinedTextField(
                    value = motivo, onValueChange = { motivo = it },
                    placeholder = { Text("Consulta de rutina", color = Gris) },
                    shape = RoundedCornerShape(14.dp), colors = colorCampo(),
                    modifier = Modifier.fillMaxWidth().height(90.dp)
                )
                MensajeError(error)
            }
            Spacer(Modifier.height(10.dp))
            BotonPrincipal("Agendar cita") {
                val cita = Repositorio.agendarCita(medicoId, fecha, hora)
                if (cita == null) {
                    error = "No se pudo agendar: el horario ya fue tomado o no hay sesión"
                } else {
                    // popUpTo(HOME): borra el flujo de agendamiento del historial
                    navController.navigate(Rutas.exito(cita.id)) { popUpTo(Rutas.HOME) }
                }
            }
            Spacer(Modifier.height(16.dp))
        }
    }
}
