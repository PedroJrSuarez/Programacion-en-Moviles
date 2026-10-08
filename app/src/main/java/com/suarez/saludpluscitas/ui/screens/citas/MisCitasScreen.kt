package com.suarez.saludpluscitas.ui.screens.citas

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
fun MisCitasScreen(navController: NavController) {
    val citas = Repositorio.citasDelUsuario()

    Scaffold(
        containerColor = Color.White,
        topBar = { BarraSuperior("Mis citas", navController) },
        bottomBar = { BarraInferior(Rutas.MIS_CITAS, navController) }
    ) { pad ->
        Column(Modifier.padding(pad).padding(horizontal = 20.dp)) {
            if (citas.isEmpty()) {
                Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                    IconoTile(Icons.Default.EventAvailable, Azul, AzulClaro, 88.dp, forma = CircleShape)
                    Spacer(Modifier.height(16.dp))
                    Text("Aún no tienes citas", fontWeight = FontWeight.Bold, color = Navy, fontSize = 18.sp)
                    Text("Agenda tu primera cita médica", color = Gris, fontSize = 14.sp)
                    Spacer(Modifier.height(20.dp))
                    BotonPrincipal("Agendar cita") { navController.navigate(Rutas.ESPECIALIDADES) }
                }
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp), contentPadding = PaddingValues(vertical = 8.dp)) {
                    items(citas, key = { it.id }) { c ->
                        TarjetaCita(c) { navController.navigate(Rutas.detalle(c.id)) }
                    }
                }
            }
        }
    }
}
