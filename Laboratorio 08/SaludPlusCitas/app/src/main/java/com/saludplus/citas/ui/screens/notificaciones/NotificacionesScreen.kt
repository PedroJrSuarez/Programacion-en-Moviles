package com.saludplus.citas.ui.screens.notificaciones

import androidx.compose.foundation.layout.Arrangement
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
fun NotificacionesScreen(navController: NavController) {
    val mensajes = Repositorio.citasDelUsuario().map { c ->
        val m = Repositorio.obtenerMedico(c.medicoId)?.nombre ?: "tu médico"
        "Recordatorio: cita con $m el ${formatearFecha(c.fecha)} a las ${c.hora}"
    }

    Scaffold(topBar = { BarraSuperior("Notificaciones", navController) }) { pad ->
        Column(Modifier.padding(pad).padding(horizontal = 20.dp)) {
            if (mensajes.isEmpty()) EstadoVacio("No tienes notificaciones")
            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                items(mensajes) { msg ->
                    Card(Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                        Text("🔔 $msg", Modifier.padding(16.dp))
                    }
                }
            }
        }
    }
}
