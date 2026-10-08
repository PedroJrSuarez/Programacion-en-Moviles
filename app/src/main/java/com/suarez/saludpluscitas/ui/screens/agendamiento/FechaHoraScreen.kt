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

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight

@Composable
fun FechaHoraScreen(navController: NavController, medicoId: Int) {
    val medico = Repositorio.obtenerMedico(medicoId)

    // Semana mostrada: 0 = próximos 5 días hábiles desde hoy; no se puede ir antes de 0.
    var semana by remember { mutableIntStateOf(0) }
    val dias = remember(semana) { Fechas.semana(semana) }

    // Al cambiar de semana se elige el primer día; al cambiar de día se reinicia la hora.
    var fecha by remember(semana) { mutableStateOf(dias.first()) }
    var hora by remember(fecha) { mutableStateOf<String?>(null) }

    // Se recalcula solo cuando cambia 'fecha' y respeta los horarios ya reservados.
    val horarios = Repositorio.horariosDisponibles(medicoId, fecha.toString())

    Scaffold(containerColor = Color.White, topBar = { BarraSuperior("Seleccionar fecha y hora", navController) }) { pad ->
        Column(Modifier.padding(pad).padding(horizontal = 20.dp)) {
            CabeceraMedico(medico)
            Spacer(Modifier.height(16.dp))

            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { if (semana > 0) semana-- }, enabled = semana > 0) {
                    Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, "Semana anterior", tint = if (semana > 0) Navy else Borde)
                }
                Text(
                    Fechas.tituloMes(dias), Modifier.weight(1f), textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold, color = Navy, fontSize = 15.sp
                )
                IconButton(onClick = { semana++ }) {
                    Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, "Semana siguiente", tint = Navy)
                }
            }

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                dias.forEach { d ->
                    val sel = d == fecha
                    Column(
                        Modifier.weight(1f).clip(RoundedCornerShape(14.dp))
                            .background(if (sel) Azul else FondoSuave)
                            .clickable { fecha = d }.padding(vertical = 12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(Fechas.diaCorto(d), fontSize = 11.sp, color = if (sel) Color.White else Gris)
                        Spacer(Modifier.height(6.dp))
                        Text(d.dayOfMonth.toString(), fontWeight = FontWeight.Bold, color = if (sel) Color.White else Navy)
                    }
                }
            }

Spacer(Modifier.height(18.dp))
            if (horarios.isEmpty()) EstadoVacio("No hay horarios para este día")
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(horarios) { h ->
                    val sel = h == hora
                    Box(
                        Modifier.clip(RoundedCornerShape(14.dp))
                            .background(if (sel) Azul else FondoSuave)
                            .clickable { hora = h }.padding(vertical = 16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(h, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = if (sel) Color.White else Navy)
                    }
                }
            }
            Spacer(Modifier.height(10.dp))
            BotonPrincipal("Continuar", enabled = hora != null) {
                navController.navigate(Rutas.confirmar(medicoId, fecha.toString(), hora!!))
            }
            Spacer(Modifier.height(16.dp))
        }
    }
}
