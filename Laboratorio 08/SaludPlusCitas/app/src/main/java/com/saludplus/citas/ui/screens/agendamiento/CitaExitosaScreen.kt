package com.saludplus.citas.ui.screens.agendamiento

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import com.saludplus.citas.data.repository.Repositorio
import com.saludplus.citas.navigation.Rutas
import com.saludplus.citas.ui.components.*

@Composable
fun CitaExitosaScreen(navController: NavController, citaId: Int) {
    val cita = Repositorio.obtenerCita(citaId)
    val medico = cita?.let { Repositorio.obtenerMedico(it.medicoId) }

    Column(
        Modifier.fillMaxSize().padding(28.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(Modifier.size(96.dp).clip(CircleShape).background(MaterialTheme.colorScheme.primary), contentAlignment = Alignment.Center) {
            Icon(Icons.Default.Check, null, tint = Color.White, modifier = Modifier.size(56.dp))
        }
        Spacer(Modifier.height(20.dp))
        Text("¡Cita agendada!", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(8.dp))
        if (cita != null) {
            Text("${medico?.nombre ?: ""}", fontWeight = FontWeight.SemiBold)
            Text("${formatearFecha(cita.fecha)} · ${cita.hora}", color = MaterialTheme.colorScheme.outline, textAlign = TextAlign.Center)
        }
        Spacer(Modifier.height(36.dp))
        BotonPrincipal("Ver mis citas") {
            navController.navigate(Rutas.MIS_CITAS) { popUpTo(Rutas.HOME) }
        }
        Spacer(Modifier.height(12.dp))
        BotonSecundario("Volver al inicio") {
            navController.navigate(Rutas.HOME) { popUpTo(Rutas.HOME) { inclusive = true } }
        }
    }
}
