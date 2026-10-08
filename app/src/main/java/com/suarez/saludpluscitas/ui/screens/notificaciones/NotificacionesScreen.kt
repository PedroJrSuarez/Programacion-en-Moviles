package com.suarez.saludpluscitas.ui.screens.notificaciones

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
fun NotificacionesScreen(navController: NavController) {
    val mensajes = Repositorio.citasDelUsuario().map { c ->
        val m = Repositorio.obtenerMedico(c.medicoId)?.nombre ?: "tu médico"
        "Recordatorio: cita con $m el ${formatearFecha(c.fecha)} a las ${c.hora}"
    }

    Scaffold(containerColor = Color.White, topBar = { BarraSuperior("Notificaciones", navController) }) { pad ->
        Column(Modifier.padding(pad).padding(horizontal = 20.dp)) {
            if (mensajes.isEmpty()) EstadoVacio("No tienes notificaciones")
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp), contentPadding = PaddingValues(vertical = 8.dp)) {
                items(mensajes) { msg ->
                    TarjetaBase {
                        Row(Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                            IconoTile(Icons.Default.NotificationsNone, Azul, AzulClaro, 44.dp)
                            Spacer(Modifier.width(12.dp))
                            Text(msg, color = Navy, fontSize = 14.sp)
                        }
                    }
                }
            }
        }
    }
}
