package com.tecsup.avisoscampus.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tecsup.avisoscampus.model.Aviso
import com.tecsup.avisoscampus.model.Origen
import com.tecsup.avisoscampus.ui.theme.CyanTertiary
import com.tecsup.avisoscampus.ui.theme.EmeraldOk
import com.tecsup.avisoscampus.ui.theme.IndigoPrimary
import com.tecsup.avisoscampus.ui.theme.PurpleSecondary

/** Bento UI: Historial interactivo con badges coloridas y animación de entrada. */
@Composable
fun HistorialAvisos(avisos: List<Aviso>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .clip(CircleShape)
                            .background(
                                Brush.linearGradient(
                                    listOf(IndigoPrimary, PurpleSecondary)
                                )
                            )
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Historial de Avisos",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        ),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                Surface(
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.primaryContainer
                ) {
                    Text(
                        text = "${avisos.size} registros",
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }

            if (avisos.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Sin avisos registrados todavía",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                    )
                }
            } else {
                avisos.take(20).forEachIndexed { i, aviso ->
                    if (i > 0) {
                        HorizontalDivider(
                            color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f),
                            thickness = 1.dp
                        )
                    }

                    AnimatedVisibility(
                        visible = true,
                        enter = fadeIn() + slideInVertically()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            // Timeline dot
                            Box(
                                modifier = Modifier
                                    .padding(top = 4.dp, end = 12.dp)
                                    .size(10.dp)
                                    .clip(CircleShape)
                                    .background(obtenerColorOrigen(aviso.origen))
                            )

                            Column(modifier = Modifier.weight(1f)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    OrigenBadge(origen = aviso.origen)
                                    Text(
                                        text = aviso.hora,
                                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Medium),
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = aviso.titulo,
                                    style = MaterialTheme.typography.bodyLarge.copy(
                                        fontWeight = FontWeight.Bold
                                    ),
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = aviso.texto,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun OrigenBadge(origen: Origen) {
    val color = obtenerColorOrigen(origen)
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = color.copy(alpha = 0.15f)
    ) {
        Text(
            text = origen.texto.uppercase(),
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.ExtraBold,
                fontSize = 10.sp
            ),
            color = color
        )
    }
}

private fun obtenerColorOrigen(origen: Origen): Color {
    return when (origen) {
        Origen.BOTON -> IndigoPrimary
        Origen.RECORDATORIO -> PurpleSecondary
        Origen.MODO_AVION -> CyanTertiary
        Origen.CARGADOR -> EmeraldOk
    }
}
