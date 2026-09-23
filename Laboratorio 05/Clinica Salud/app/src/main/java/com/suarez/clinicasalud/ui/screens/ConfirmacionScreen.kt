package com.suarez.clinicasalud.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.tecsup.clinicasalud.model.Medico
import com.tecsup.clinicasalud.ui.theme.*

@Composable
fun ConfirmacionScreen(
    medico: Medico,
    diaSemana: String,
    numeroDia: String,
    hora: String,
    onVerMisCitas: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundWhite)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(72.dp)
                .clip(CircleShape)
                .background(SuccessGreenBg)
        ) {
            Icon(Icons.Filled.Check, contentDescription = null, tint = SuccessGreenIcon, modifier = Modifier.size(34.dp))
        }
        Spacer(modifier = Modifier.height(18.dp))
        Text("¡Cita agendada!", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(6.dp))
        Text(medico.nombre, color = TextSecondary, textAlign = TextAlign.Center)
        Text("$diaSemana $numeroDia, $hora", color = TextSecondary, textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(28.dp))
        Button(
            onClick = onVerMisCitas,
            colors = ButtonDefaults.buttonColors(containerColor = CardBackground),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Ver mis citas", color = TextPrimary, fontWeight = FontWeight.Bold)
        }
    }
}
