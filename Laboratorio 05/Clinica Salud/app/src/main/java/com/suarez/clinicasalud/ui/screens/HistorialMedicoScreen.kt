package com.suarez.clinicasalud.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.suarez.clinicasalud.model.Cita
import com.suarez.clinicasalud.model.EstadoCita
import com.suarez.clinicasalud.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistorialMedicoScreen(
    citas: List<Cita>,
    onMenuClick: () -> Unit
) {
    val completadas = citas.filter { it.estado == EstadoCita.COMPLETADA }
    Scaffold(
        topBar = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 16.dp)
            ) {
                IconButton(onClick = onMenuClick) {
                    Icon(Icons.Filled.Menu, contentDescription = "Menú", tint = TextPrimary)
                }
                Text("Historial médico", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleLarge)
            }
        },
        containerColor = BackgroundWhite
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(horizontal = 20.dp)) {
            if (completadas.isEmpty()) {
                Text("Aún no tienes citas completadas.", color = TextSecondary)
            } else {
                completadas.forEach { cita ->
                    Column(modifier = Modifier.padding(vertical = 10.dp)) {
                        Text(cita.medico.nombre, fontWeight = FontWeight.Bold)
                        Text("${cita.fecha} · ${cita.hora}", color = TextSecondary)
                    }
                }
            }
        }
    }
}
