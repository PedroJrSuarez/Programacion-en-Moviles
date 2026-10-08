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
fun EspecialidadesScreen(navController: NavController) {
    var busqueda by remember { mutableStateOf("") }
    val lista = Repositorio.buscarEspecialidades(busqueda)

    Scaffold(containerColor = Color.White, topBar = { BarraSuperior("Especialidades", navController) }) { pad ->
        Column(Modifier.padding(pad).padding(horizontal = 20.dp)) {
            OutlinedTextField(
                value = busqueda, onValueChange = { busqueda = it },
                placeholder = { Text("Buscar especialidad...", color = Gris) },
                leadingIcon = { Icon(Icons.Default.Search, null, tint = Gris) },
                singleLine = true, shape = RoundedCornerShape(14.dp), colors = colorCampo(),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(14.dp))
            if (lista.isEmpty()) EstadoVacio("Sin resultados")
            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp), contentPadding = PaddingValues(bottom = 20.dp)) {
                items(lista, key = { it.id }) { esp ->
                    TarjetaEspecialidad(esp) { navController.navigate(Rutas.medicos(esp.id)) }
                }
            }
        }
    }
}
