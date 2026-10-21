package com.suarez.lavacampus.ui.components.graficos

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun GraficoDona(datos: List<DatoGrafico>, modifier: Modifier = Modifier) {
    val total = datos.sumOf { it.valor.toDouble() }.toFloat()
    Row(modifier, verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        Canvas(Modifier.size(140.dp)) {
            val grosor = 26.dp.toPx()
            var inicio = -90f
            datos.forEach { d ->
                val barrido = if (total > 0f) 360f * d.valor / total else 0f
                drawArc(
                    color = d.color, startAngle = inicio, sweepAngle = (barrido - 1.5f).coerceAtLeast(0.5f),
                    useCenter = false, topLeft = Offset(grosor / 2, grosor / 2),
                    size = Size(size.width - grosor, size.height - grosor), style = Stroke(grosor)
                )
                inicio += barrido
            }
        }
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            datos.forEach { d ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(Modifier.size(10.dp).clip(CircleShape).background(d.color))
                    Spacer(Modifier.width(8.dp))
                    Text("${d.etiqueta}: ${d.valor.toInt()}")
                }
            }
        }
    }
}
