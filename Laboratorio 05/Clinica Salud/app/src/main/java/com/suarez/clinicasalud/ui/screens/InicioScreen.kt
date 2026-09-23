package com.suarez.clinicasalud.ui.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.suarez.clinicasalud.model.Medico
import com.suarez.clinicasalud.model.especialidades
import com.suarez.clinicasalud.model.medicosDisponibles
import com.suarez.clinicasalud.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InicioScreen(
    onMenuClick: () -> Unit,
    onMedicoClick: (Medico) -> Unit
) {
    var especialidadSeleccionada by remember { mutableStateOf(especialidades.first()) }

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(PurplePrimary)
                    .padding(horizontal = 16.dp, vertical = 18.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = onMenuClick, modifier = Modifier.size(28.dp)) {
                        Icon(Icons.Filled.Menu, contentDescription = "Menú", tint = TextOnPurple)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Clínica Salud+", color = TextOnPurple, style = MaterialTheme.typography.titleLarge)
                }
                Text(
                    "Hola, Juan",
                    color = TextOnPurpleFaded,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(start = 36.dp, top = 2.dp)
                )
            }
        },
        containerColor = BackgroundWhite
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {

            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 14.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(especialidades) { especialidad ->
                    val seleccionada = especialidad == especialidadSeleccionada
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(50))
                            .background(if (seleccionada) PurplePrimary else PurpleLight)
                            .clickable { especialidadSeleccionada = especialidad }
                            .padding(horizontal = 18.dp, vertical = 9.dp)
                    ) {
                        Text(
                            especialidad,
                            color = if (seleccionada) TextOnPurple else TextPrimary,
                            fontWeight = if (seleccionada) FontWeight.Bold else FontWeight.Normal,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }

            Text(
                "Médicos disponibles",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
            )

            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(medicosDisponibles) { medico ->
                    MedicoCard(medico = medico, onClick = { onMedicoClick(medico) })
                }
            }
        }
    }
}

@Composable
private fun MedicoCard(medico: Medico, onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(CardBackground)
            .clickable(onClick = onClick)
            .padding(12.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(42.dp)
                .clip(CircleShape)
                .background(PurplePrimary)
        ) {
            Icon(Icons.Filled.LocalHospital, contentDescription = null, tint = TextOnPurple, modifier = Modifier.size(20.dp))
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(medico.nombre, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyLarge)
            Text(medico.especialidad, color = TextSecondary, style = MaterialTheme.typography.bodyMedium)
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Filled.Star, contentDescription = null, tint = StarYellow, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(2.dp))
            Text(medico.calificacion.toString(), fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyMedium)
        }
    }
}
