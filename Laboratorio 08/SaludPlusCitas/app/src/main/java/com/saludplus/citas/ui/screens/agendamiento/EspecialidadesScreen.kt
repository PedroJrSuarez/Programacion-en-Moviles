package com.saludplus.citas.ui.screens.agendamiento

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
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
fun EspecialidadesScreen(navController: NavController) {
    var busqueda by remember { mutableStateOf("") }
    val lista = Repositorio.buscarEspecialidades(busqueda)

    Scaffold(topBar = { BarraSuperior("Especialidades", navController) }) { pad ->
        Column(Modifier.padding(pad).padding(horizontal = 20.dp)) {
            OutlinedTextField(
                value = busqueda, onValueChange = { busqueda = it },
                placeholder = { Text("Buscar especialidad") },
                leadingIcon = { Icon(Icons.Default.Search, null) },
                singleLine = true, modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(12.dp))
            if (lista.isEmpty()) EstadoVacio("Sin resultados")
            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp), contentPadding = PaddingValues(bottom = 20.dp)) {
                items(lista, key = { it.id }) { esp ->
                    TarjetaEspecialidad(esp) { navController.navigate(Rutas.medicos(esp.id)) }
                }
            }
        }
    }
}
