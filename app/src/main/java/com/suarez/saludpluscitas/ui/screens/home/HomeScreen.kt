package com.suarez.saludpluscitas.ui.screens.home

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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.suarez.saludpluscitas.data.repository.Repositorio
import com.suarez.saludpluscitas.navigation.Rutas
import com.suarez.saludpluscitas.ui.components.*
import com.suarez.saludpluscitas.ui.theme.*

@Composable
private fun TileHome(
    texto: String, icono: androidx.compose.ui.graphics.vector.ImageVector,
    color: Color, fondo: Color, modifier: Modifier, onClick: () -> Unit
) {
    Surface(onClick = onClick, modifier = modifier.height(112.dp), shape = RoundedCornerShape(18.dp), color = fondo) {
        Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(icono, null, tint = color, modifier = Modifier.size(38.dp))
            Spacer(Modifier.height(8.dp))
            Text(texto, color = color, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
        }
    }
}

@Composable
fun HomeScreen(navController: NavController) {
    val nombre = Repositorio.usuarioActual?.nombre?.substringBefore(" ") ?: "Paciente"
    val destacadas = Repositorio.especialidadesDestacadas()

    Scaffold(containerColor = Color.White, bottomBar = { BarraInferior(Rutas.HOME, navController) }) { pad ->
        Column(Modifier.padding(pad).padding(horizontal = 20.dp).verticalScroll(rememberScrollState())) {
            Spacer(Modifier.height(20.dp))
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.Top) {
                Column(Modifier.weight(1f)) {
                    Text("¡Hola, $nombre!", fontSize = 26.sp, fontWeight = FontWeight.ExtraBold, color = Navy)
                    Text("¿Qué deseas hacer hoy?", color = Gris, fontSize = 14.sp)
                }
                IconButton(onClick = { navController.navigate(Rutas.NOTIFICACIONES) }) {
                    Icon(Icons.Default.NotificationsNone, "Notificaciones", tint = Navy)
                }
            }
            Spacer(Modifier.height(22.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                TileHome("Agendar cita", Icons.Default.CalendarMonth, Azul, AzulClaro, Modifier.weight(1f)) {
                    navController.navigate(Rutas.ESPECIALIDADES)
                }
                TileHome("Mis citas", Icons.Default.EventAvailable, Verde, VerdeClaro, Modifier.weight(1f)) {
                    navController.navigate(Rutas.MIS_CITAS)
                }
            }
            Spacer(Modifier.height(14.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                TileHome("Mis datos", Icons.Default.Person, Morado, MoradoClaro, Modifier.weight(1f)) {
                    navController.navigate(Rutas.PERFIL)
                }
                TileHome("Resultados", Icons.Default.Description, Naranja, NaranjaClaro, Modifier.weight(1f)) {
                    navController.navigate(Rutas.RESULTADOS)
                }
            }
            Spacer(Modifier.height(24.dp))
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Text("Especialidades destacadas", Modifier.weight(1f), fontWeight = FontWeight.Bold, color = Navy, fontSize = 15.sp)
                Text("Ver todas", color = Azul, fontSize = 13.sp, fontWeight = FontWeight.Medium,
                    modifier = Modifier.clickable { navController.navigate(Rutas.ESPECIALIDADES) })
            }
            Spacer(Modifier.height(12.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                items(destacadas, key = { it.id }) { esp ->
                    val (icono, color) = estiloEspecialidad(esp.id)
                    Surface(
                        onClick = { navController.navigate(Rutas.medicos(esp.id)) },
                        shape = RoundedCornerShape(16.dp), color = Color.White, shadowElevation = 2.dp,
                        border = androidx.compose.foundation.BorderStroke(1.dp, Borde)
                    ) {
                        Column(Modifier.width(104.dp).height(112.dp).padding(8.dp),
                            verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                            IconoTile(icono, color, color.copy(alpha = 0.12f), 44.dp, forma = CircleShape)
                            Spacer(Modifier.height(8.dp))
                            Text(esp.nombre, textAlign = TextAlign.Center, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Navy)
                        }
                    }
                }
            }
            Spacer(Modifier.height(20.dp))
        }
    }
}
