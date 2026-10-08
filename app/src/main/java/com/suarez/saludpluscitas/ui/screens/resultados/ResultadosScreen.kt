package com.suarez.saludpluscitas.ui.screens.resultados

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

data class ResultadoMedico(val titulo: String, val fecha: String, val disponible: Boolean)

private val resultados = listOf(
    ResultadoMedico("Hemograma completo", "2026-09-12", true),
    ResultadoMedico("Perfil lipídico", "2026-09-20", true),
    ResultadoMedico("Electrocardiograma", "2026-09-28", true),
    ResultadoMedico("Radiografía de tórax", "2026-10-03", false)
)

@Composable
fun ResultadosScreen(navController: NavController) {
    Scaffold(
        containerColor = Color.White,
        topBar = { BarraSuperior("Resultados", navController) },
        bottomBar = { BarraInferior(Rutas.RESULTADOS, navController) }
    ) { pad ->
        LazyColumn(
            Modifier.padding(pad).padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(resultados) { r ->
                TarjetaBase {
                    Row(Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                        IconoTile(Icons.Default.Description, Naranja, NaranjaClaro, 48.dp)
                        Spacer(Modifier.width(14.dp))
                        Column(Modifier.weight(1f)) {
                            Text(r.titulo, fontWeight = FontWeight.Bold, color = Navy, fontSize = 15.sp)
                            Text(formatearFecha(r.fecha), fontSize = 12.sp, color = Gris)
                        }
                        if (r.disponible) Insignia("Disponible") else Insignia("En proceso", NaranjaClaro, Naranja)
                    }
                }
            }
        }
    }
}
