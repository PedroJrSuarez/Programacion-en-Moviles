package com.suarez.saludplus.ui.screens.perfil

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.draw.clip
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
fun PerfilScreen(navController: NavController) {
    val u = Repositorio.usuarioActual
    val totalCitas = Repositorio.citasDelUsuario().size

    Scaffold(bottomBar = { BarraInferior(Rutas.PERFIL, navController) }) { pad ->
        Column(
            Modifier.padding(pad).padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(Modifier.size(96.dp).clip(CircleShape).background(MaterialTheme.colorScheme.primaryContainer), contentAlignment = Alignment.Center) {
                Icon(Icons.Default.Person, null, Modifier.size(56.dp), tint = MaterialTheme.colorScheme.primary)
            }
            Text(u?.nombre ?: "Invitado", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            Card(Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                Column(Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Mis datos", fontWeight = FontWeight.Bold)
                    Text("Correo: ${u?.correo ?: "-"}")
                    Text("Teléfono: ${u?.telefono?.ifBlank { "-" } ?: "-"}")
                    Text("Citas agendadas: $totalCitas")
                }
            }
            Spacer(Modifier.weight(1f))
            BotonSecundario("Cerrar sesión") {
                Repositorio.cerrarSesion()
                navController.navigate(Rutas.SPLASH) {
                    popUpTo(navController.graph.id) { inclusive = true }
                }
            }
        }
    }
}
