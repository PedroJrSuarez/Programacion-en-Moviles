package com.suarez.tecsupfit.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.suarez.tecsupfit.data.listaClases
import com.suarez.tecsupfit.navigation.Rutas
import com.suarez.tecsupfit.ui.components.ChipFiltro
import com.suarez.tecsupfit.ui.components.TarjetaClase
import com.suarez.tecsupfit.ui.theme.TealPrimary
import com.suarez.tecsupfit.ui.theme.TextDark

@Composable
fun PantallaInicio(navController: NavController) {
    val filtros = listOf("Hoy", "Esta semana")
    var filtroSeleccionado by remember { mutableStateOf(filtros[0]) }
    val clasesFiltradas = listaClases.filter { it.dia == filtroSeleccionado }

    Column(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(TealPrimary)
                .padding(horizontal = 20.dp, vertical = 20.dp)
        ) {
            Text(
                text = "TECSUP Fit",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = "Hola, Diego",
                color = Color.White.copy(alpha = 0.85f),
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Column(modifier = Modifier.padding(20.dp)) {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                filtros.forEach { filtro ->
                    ChipFiltro(
                        texto = filtro,
                        seleccionado = filtro == filtroSeleccionado,
                        onClick = { filtroSeleccionado = filtro }
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Clases disponibles",
                color = TextDark,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(12.dp))

            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                items(clasesFiltradas) { clase ->
                    TarjetaClase(
                        clase = clase,
                        onClick = { navController.navigate(Rutas.Detalle.crearRuta(clase.id)) }
                    )
                }
            }
        }
    }
}
