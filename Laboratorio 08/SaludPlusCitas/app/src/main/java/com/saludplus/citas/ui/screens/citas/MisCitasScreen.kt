package com.saludplus.citas.ui.screens.citas

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
fun MisCitasScreen(navController: NavController) {
    val citas = Repositorio.citasDelUsuario()

    Scaffold(bottomBar = { BarraInferior(Rutas.MIS_CITAS, navController) }) { pad ->
        Column(Modifier.padding(pad).padding(20.dp)) {
            Text("Mis citas", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(12.dp))
            if (citas.isEmpty()) {
                EstadoVacio("Aún no tienes citas agendadas")
                BotonPrincipal("Agendar mi primera cita") { navController.navigate(Rutas.ESPECIALIDADES) }
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    items(citas, key = { it.id }) { c ->
                        TarjetaCita(c) { navController.navigate(Rutas.detalle(c.id)) }
                    }
                }
            }
        }
    }
}
