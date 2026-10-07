package com.suarez.saludplus.ui.screens.agendamiento

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import java.time.LocalDate
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
fun FechaHoraScreen(navController: NavController, medicoId: Int) {
    val medico = Repositorio.obtenerMedico(medicoId)
    val fechas = remember { (1..7).map { LocalDate.now().plusDays(it.toLong()).toString() } }
    var fecha by remember { mutableStateOf(fechas.first()) }
    var hora by remember(fecha) { mutableStateOf<String?>(null) }
    val horarios = Repositorio.horariosDisponibles(medicoId, fecha)
    val p = MaterialTheme.colorScheme.primary

    Scaffold(topBar = { BarraSuperior("Fecha y hora", navController) }) { pad ->
        Column(Modifier.padding(pad).padding(horizontal = 20.dp)) {
            Text(medico?.nombre ?: "", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(14.dp))
            Text("Elige un día", fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(8.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(fechas) { f ->
                    val sel = f == fecha
                    Column(
                        Modifier.clip(RoundedCornerShape(12.dp))
                            .background(if (sel) p else MaterialTheme.colorScheme.surface)
                            .clickable { fecha = f }.padding(horizontal = 14.dp, vertical = 10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        val c = if (sel) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
                        Text(formatearFecha(f, "EEE"), color = c, style = MaterialTheme.typography.bodySmall)
                        Text(formatearFecha(f, "d"), color = c, fontWeight = FontWeight.Bold)
                    }
                }
            }
            Spacer(Modifier.height(18.dp))
            Text("Horarios disponibles", fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(8.dp))
            if (horarios.isEmpty()) EstadoVacio("No hay horarios para este día")
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(horarios) { h ->
                    val sel = h == hora
                    Box(
                        Modifier.clip(RoundedCornerShape(12.dp))
                            .background(if (sel) p else MaterialTheme.colorScheme.primaryContainer)
                            .clickable { hora = h }.padding(vertical = 14.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(h, fontWeight = FontWeight.SemiBold,
                            color = if (sel) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onPrimaryContainer)
                    }
                }
            }
            Spacer(Modifier.height(8.dp))
            BotonPrincipal("Continuar", enabled = hora != null) {
                navController.navigate(Rutas.confirmar(medicoId, fecha, hora!!))
            }
            Spacer(Modifier.height(16.dp))
        }
    }
}
