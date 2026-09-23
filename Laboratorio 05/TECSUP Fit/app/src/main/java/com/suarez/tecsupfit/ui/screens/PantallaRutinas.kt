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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.suarez.tecsupfit.ui.components.IconoTeal
import com.suarez.tecsupfit.ui.theme.GrayCard
import com.suarez.tecsupfit.ui.theme.TextDark

private data class Rutina(val nombre: String, val detalle: String)

private val rutinas = listOf(
    Rutina("Rutina de fuerza", "4 ejercicios · 40 min"),
    Rutina("Rutina de cardio", "3 ejercicios · 30 min"),
    Rutina("Rutina de movilidad", "5 ejercicios · 20 min")
)

@Composable
fun PantallaRutinas() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Text(
            text = "Rutinas",
            fontWeight = FontWeight.Bold,
            color = TextDark,
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.height(20.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items(rutinas) { rutina ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(GrayCard)
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconoTeal(icono = Icons.Filled.FitnessCenter)
                    Column(modifier = Modifier.padding(start = 12.dp)) {
                        Text(rutina.nombre, fontWeight = FontWeight.Bold, color = TextDark)
                        Text(rutina.detalle, color = TextDark.copy(alpha = 0.6f), style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
    }
}

