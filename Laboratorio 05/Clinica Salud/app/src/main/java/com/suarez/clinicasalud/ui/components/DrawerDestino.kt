package com.suarez.clinicasalud.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.suarez.clinicasalud.ui.theme.*

data class DrawerDestino(val label: String)

val destinosDrawer = listOf("Inicio", "Mis citas", "Historial médico", "Perfil")

@Composable
fun DrawerContent(
    destinoActual: String,
    onDestinoClick: (String) -> Unit
) {
    ModalDrawerSheet(
        drawerContainerColor = BackgroundWhite,
        modifier = Modifier.width(280.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(PurplePrimary)
                ) {
                    Text("JP", color = TextOnPurple, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text("Juan Pérez", fontWeight = FontWeight.Bold)
                    Text("Paciente", color = TextSecondary, style = MaterialTheme.typography.bodyMedium)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(color = DividerColor)
            Spacer(modifier = Modifier.height(8.dp))

            destinosDrawer.forEach { destino ->
                val seleccionado = destino == destinoActual
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(if (seleccionado) PurpleLight else Color.Transparent)
                        .clickable { onDestinoClick(destino) }
                        .padding(horizontal = 12.dp, vertical = 14.dp)
                ) {
                    Icon(
                        imageVector = if (seleccionado) Icons.Filled.Circle else Icons.Outlined.Circle,
                        contentDescription = null,
                        tint = if (seleccionado) PurplePrimary else TextSecondary,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(14.dp))
                    Text(
                        text = destino,
                        color = if (seleccionado) PurplePrimary else TextPrimary,
                        fontWeight = if (seleccionado) FontWeight.Bold else FontWeight.Normal
                    )
                }
            }
        }
    }
}
