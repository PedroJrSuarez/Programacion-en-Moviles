package com.saludplus.citas.ui.screens.agendamiento

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
fun MedicosScreen(navController: NavController, especialidadId: Int) {
    val esp = Repositorio.obtenerEspecialidad(especialidadId)
    val lista = Repositorio.medicosPorEspecialidad(especialidadId)

    Scaffold(topBar = { BarraSuperior(esp?.nombre ?: "Médicos", navController) }) { pad ->
        Column(Modifier.padding(pad).padding(horizontal = 20.dp)) {
            Text("Médicos mejor calificados", color = MaterialTheme.colorScheme.outline)
            Spacer(Modifier.height(10.dp))
            if (lista.isEmpty()) EstadoVacio("No hay médicos disponibles")
            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp), contentPadding = PaddingValues(bottom = 20.dp)) {
                items(lista, key = { it.id }) { m ->
                    TarjetaMedico(m) { navController.navigate(Rutas.fechaHora(m.id)) }
                }
            }
        }
    }
}
