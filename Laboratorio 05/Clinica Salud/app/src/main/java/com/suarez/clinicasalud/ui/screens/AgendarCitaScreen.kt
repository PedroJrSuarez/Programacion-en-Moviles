package com.suarez.clinicasalud.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.suarez.clinicasalud.model.Medico
import com.suarez.clinicasalud.model.fechasDisponibles
import com.suarez.clinicasalud.model.horasDisponibles
import com.suarez.clinicasalud.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgendarCitaScreen(
    medico: Medico,
    onBack: () -> Unit,
    onConfirmar: (diaSemana: String, numeroDia: String, hora: String) -> Unit
) {
    var fechaSeleccionada by remember { mutableStateOf(fechasDisponibles[1]) } // Vie 27 por defecto
    var horaSeleccionada by remember { mutableStateOf(horasDisponibles[1]) }   // 10:30 por defecto

    Scaffold(
        topBar = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 12.dp)
            ) {
                IconButton(onClick = onBack) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Volver", tint = TextPrimary)
                }
                Text("Agendar cita", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
            }
        },
        bottomBar = {
            Button(
                onClick = { onConfirmar(fechaSeleccionada.first, fechaSeleccionada.second, horaSeleccionada) },
                colors = ButtonDefaults.buttonColors(containerColor = PurplePrimary),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .height(52.dp)
            ) {
                Text("Confirmar cita", color = TextOnPurple, fontWeight = FontWeight.Bold)
            }
        },
        containerColor = BackgroundWhite
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(horizontal = 20.dp, vertical = 8.dp)) {

            Text("Selecciona fecha", fontWeight = FontWeight.Bold, modifier = Modifier.padding(vertical = 8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                fechasDisponibles.forEach { fecha ->
                    OpcionSeleccionable(
                        seleccionado = fecha == fechaSeleccionada,
                        onClick = { fechaSeleccionada = fecha },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(fecha.first, style = MaterialTheme.typography.bodyMedium, color = if (fecha == fechaSeleccionada) TextOnPurple else TextSecondary)
                        Text(fecha.second, fontWeight = FontWeight.Bold, color = if (fecha == fechaSeleccionada) TextOnPurple else TextPrimary)
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text("Selecciona hora", fontWeight = FontWeight.Bold, modifier = Modifier.padding(vertical = 8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                horasDisponibles.forEach { hora ->
                    OpcionSeleccionable(
                        seleccionado = hora == horaSeleccionada,
                        onClick = { horaSeleccionada = hora },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            hora,
                            fontWeight = FontWeight.Bold,
                            color = if (hora == horaSeleccionada) TextOnPurple else TextPrimary
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun OpcionSeleccionable(
    seleccionado: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(if (seleccionado) PurplePrimary else PurpleLight)
            .selectable(selected = seleccionado, onClick = onClick)
            .padding(vertical = 12.dp),
        content = content
    )
}
