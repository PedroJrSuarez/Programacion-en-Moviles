package com.suarez.lavacampus.ui.components.graficos

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import java.util.Locale

@Composable
fun GraficoBarras(datos: List<DatoGrafico>, modifier: Modifier = Modifier) {
    val maximo = (datos.maxOfOrNull { it.valor } ?: 0f).coerceAtLeast(1f)
    Column(modifier) {
        Canvas(Modifier.fillMaxWidth().weight(1f)) {
            val n = datos.size.coerceAtLeast(1)
            val ancho = size.width / n * 0.5f
            datos.forEachIndexed { i, d ->
                val alto = size.height * (d.valor / maximo)
                val cx = size.width * (i + 0.5f) / n
                drawRoundRect(
                    d.color, topLeft = Offset(cx - ancho / 2, size.height - alto),
                    size = Size(ancho, alto), cornerRadius = CornerRadius(6.dp.toPx())
                )
            }
        }
        Row(Modifier.fillMaxWidth().padding(top = 4.dp)) {
            datos.forEach { d ->
                Text(
                    "${d.etiqueta}\n${String.format(Locale.US, "S/ %.0f", d.valor)}", Modifier.weight(1f),
                    textAlign = TextAlign.Center, style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}
