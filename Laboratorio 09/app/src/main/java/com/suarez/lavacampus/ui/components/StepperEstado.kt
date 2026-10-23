package com.suarez.lavacampus.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.suarez.lavacampus.model.EstadoPedido
import com.suarez.lavacampus.model.texto

private val etapas = listOf(EstadoPedido.RECIBIDO, EstadoPedido.EN_PROCESO, EstadoPedido.LISTO, EstadoPedido.ENTREGADO)
private val ALTO_ETAPA = 56.dp

/** Línea de seguimiento dibujada con Canvas. horizontal (Inicio) o vertical (Detalle). */
@Composable
fun StepperEstado(
    estado: EstadoPedido, vertical: Boolean, modifier: Modifier = Modifier,
    sobrePrimario: Boolean = false
) {
    val indice = etapas.indexOf(estado)   // -1 si está CANCELADO
    val scheme = MaterialTheme.colorScheme
    val activo = if (sobrePrimario) scheme.onPrimary else scheme.primary
    val inactivo = if (sobrePrimario) scheme.onPrimary.copy(alpha = 0.35f) else scheme.outline.copy(alpha = 0.5f)
    val colorTexto = if (sobrePrimario) scheme.onPrimary else scheme.onSurface

    if (vertical) {
        Row(modifier) {
            Canvas(Modifier.width(32.dp).height(ALTO_ETAPA * etapas.size)) {
                val r = 9.dp.toPx(); val alto = ALTO_ETAPA.toPx(); val cx = size.width / 2
                for (i in etapas.indices) {
                    val cy = alto * (i + 0.5f)
                    if (i < etapas.lastIndex) {
                        drawLine(if (i < indice) activo else inactivo, Offset(cx, cy + r), Offset(cx, cy + alto - r), 3.dp.toPx())
                    }
                    if (i <= indice) drawCircle(activo, r, Offset(cx, cy))
                    else drawCircle(inactivo, r, Offset(cx, cy), style = Stroke(2.dp.toPx()))
                    if (i == indice) drawCircle(activo.copy(alpha = 0.35f), r + 5.dp.toPx(), Offset(cx, cy), style = Stroke(2.dp.toPx()))
                }
            }
            Column {
                etapas.forEachIndexed { i, e ->
                    Box(Modifier.height(ALTO_ETAPA), contentAlignment = Alignment.CenterStart) {
                        Text(e.texto(), color = colorTexto,
                            fontWeight = if (i == indice) FontWeight.Bold else FontWeight.Normal)
                    }
                }
            }
        }
    } else {
        Column(modifier) {
            Canvas(Modifier.fillMaxWidth().height(28.dp)) {
                val r = 8.dp.toPx(); val n = etapas.size; val cy = size.height / 2
                for (i in 0 until n) {
                    val cx = size.width * (i + 0.5f) / n
                    if (i < n - 1) {
                        val sx = size.width * (i + 1.5f) / n
                        drawLine(if (i < indice) activo else inactivo, Offset(cx + r, cy), Offset(sx - r, cy), 3.dp.toPx())
                    }
                    if (i <= indice) drawCircle(activo, r, Offset(cx, cy))
                    else drawCircle(inactivo, r, Offset(cx, cy), style = Stroke(2.dp.toPx()))
                    if (i == indice) drawCircle(activo.copy(alpha = 0.35f), r + 4.dp.toPx(), Offset(cx, cy), style = Stroke(2.dp.toPx()))
                }
            }
            Row(Modifier.fillMaxWidth()) {
                etapas.forEachIndexed { i, e ->
                    Text(e.texto(), Modifier.weight(1f), textAlign = TextAlign.Center, color = colorTexto,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = if (i == indice) FontWeight.Bold else FontWeight.Normal)
                }
            }
        }
    }
}
