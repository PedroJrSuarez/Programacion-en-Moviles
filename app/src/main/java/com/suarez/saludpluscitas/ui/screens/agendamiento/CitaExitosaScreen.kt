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

@Composable
fun CitaExitosaScreen(navController: NavController, citaId: Int) {
    val cita = Repositorio.obtenerCita(citaId)
    val medico = cita?.let { Repositorio.obtenerMedico(it.medicoId) }

    Column(
        Modifier.fillMaxSize().background(Color.White).padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.weight(1f))
        Box(Modifier.size(104.dp).clip(CircleShape).background(VerdeClaro), contentAlignment = Alignment.Center) {
            Box(Modifier.size(76.dp).clip(CircleShape).background(Verde), contentAlignment = Alignment.Center) {
                Icon(Icons.Default.Check, null, tint = Color.White, modifier = Modifier.size(46.dp))
            }
        }
        Spacer(Modifier.height(20.dp))
        Text("¡Cita agendada!", fontSize = 26.sp, fontWeight = FontWeight.ExtraBold, color = Navy)
        Text("Te enviaremos un recordatorio", color = Gris, fontSize = 14.sp)
        Spacer(Modifier.height(24.dp))
        if (cita != null) {
            TarjetaBase {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
                    CabeceraMedico(medico)
                    InfoFila(Icons.Default.CalendarMonth, "Fecha", formatearFecha(cita.fecha))
                    InfoFila(Icons.Default.AccessTime, "Hora", cita.hora)
                }
            }
        }
        Spacer(Modifier.weight(1f))
        BotonPrincipal("Ver mis citas") {
            navController.navigate(Rutas.MIS_CITAS) { popUpTo(Rutas.HOME) }
        }
        TextButton(onClick = {
            navController.navigate(Rutas.HOME) { popUpTo(Rutas.HOME) { inclusive = true } }
        }) { Text("Volver al inicio", color = Azul, fontWeight = FontWeight.Medium) }
    }
}
