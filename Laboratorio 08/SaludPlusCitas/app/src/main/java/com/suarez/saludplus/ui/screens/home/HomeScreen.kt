package com.suarez.saludplus.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
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
fun HomeScreen(navController: NavController) {
    val usuario = Repositorio.usuarioActual
    val proximas = Repositorio.citasDelUsuario()
    val destacadas = Repositorio.especialidadesDestacadas()

    Scaffold(bottomBar = { BarraInferior(Rutas.HOME, navController) }) { pad ->
        Column(
            Modifier.padding(pad).padding(20.dp).verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.weight(1f)) {
                    Text("Hola,", color = MaterialTheme.colorScheme.outline)
                    Text(usuario?.nombre ?: "Paciente", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                }
                IconButton(onClick = { navController.navigate(Rutas.NOTIFICACIONES) }) {
                    Icon(Icons.Default.Notifications, "Notificaciones")
                }
            }

            Card(
                onClick = { navController.navigate(Rutas.ESPECIALIDADES) },
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Column(Modifier.padding(20.dp)) {
                    Text("Agendar cita", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimary)
                    Text("Elige especialidad, médico y horario", color = MaterialTheme.colorScheme.onPrimary)
                }
            }

            Card(
                onClick = { navController.navigate(Rutas.MIS_CITAS) },
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            ) {
                Column(Modifier.padding(20.dp)) {
                    Text("Mis citas", fontWeight = FontWeight.Bold)
                    Text(if (proximas.isEmpty()) "Aún no tienes citas" else "Tienes ${proximas.size} cita(s) programada(s)")
                }
            }

            Text("Especialidades destacadas", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                items(destacadas, key = { it.id }) { esp ->
                    Card(
                        onClick = { navController.navigate(Rutas.medicos(esp.id)) },
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Column(Modifier.width(120.dp).padding(14.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(esp.emoji, style = MaterialTheme.typography.headlineMedium)
                            Spacer(Modifier.height(6.dp))
                            Text(esp.nombre, textAlign = TextAlign.Center, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
            }
        }
    }
}
