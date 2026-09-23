package com.suarez.clinicasalud.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.suarez.clinicasalud.model.Cita
import com.suarez.clinicasalud.model.EstadoCita
import com.suarez.clinicasalud.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MisCitasScreen(
    citas: List<Cita>,
    onMenuClick: () -> Unit
) {
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
                Text("Mis citas", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleLarge)
            }
        },
        containerColor = BackgroundWhite
    ) { padding ->
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(padding)
        ) {
            items(citas) { cita ->
                CitaCard(cita)
            }
        }
    }
}

@Composable
private fun CitaCard(cita: Cita) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(CardBackground)
            .padding(16.dp)
    ) {
        Text(cita.medico.nombre, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyLarge)
        Text("${cita.fecha}, ${cita.hora}", color = TextSecondary, style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(8.dp))
        val esConfirmada = cita.estado == EstadoCita.CONFIRMADA
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(50))
                .background(if (esConfirmada) StatusConfirmadaBg else StatusCompletadaBg)
                .padding(horizontal = 10.dp, vertical = 4.dp)
        ) {
            Text(
                if (esConfirmada) "Confirmada" else "Completada",
                color = if (esConfirmada) StatusConfirmadaText else StatusCompletadaText,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
