package com.suarez.saludplus.ui.screens.resultados

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
import com.suarez.saludplus.data.repository.Repositorio
import com.suarez.saludplus.navigation.Rutas
import com.suarez.saludplus.ui.components.*

data class ResultadoMedico(val titulo: String, val fecha: String, val estado: String)

private val resultados = listOf(
    ResultadoMedico("Hemograma completo", "2026-09-12", "Disponible"),
    ResultadoMedico("Perfil lipídico", "2026-09-20", "Disponible"),
    ResultadoMedico("Electrocardiograma", "2026-09-28", "Disponible"),
    ResultadoMedico("Radiografía de tórax", "2026-10-03", "En proceso")
)

@Composable
fun ResultadosScreen(navController: NavController) {
    Scaffold(bottomBar = { BarraInferior(Rutas.RESULTADOS, navController) }) { pad ->
        Column(Modifier.padding(pad).padding(20.dp)) {
            Text("Resultados", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(12.dp))
            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                items(resultados) { r ->
                    Card(Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                        Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                            Column(Modifier.weight(1f)) {
                                Text(r.titulo, fontWeight = FontWeight.Bold)
                                Text(formatearFecha(r.fecha), style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.outline)
                            }
                            Text(r.estado, color = if (r.estado == "Disponible") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline)
                        }
                    }
                }
            }
        }
    }
}
