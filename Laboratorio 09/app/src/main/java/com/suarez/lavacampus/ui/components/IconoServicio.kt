package com.suarez.lavacampus.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke

@Composable
fun IconoServicio(icono: String, modifier: Modifier = Modifier) {
    val color = MaterialTheme.colorScheme.primary
    val fondo = MaterialTheme.colorScheme.primaryContainer
    Canvas(modifier) {
        val w = size.minDimension
        fun p(x: Float, y: Float) = Offset(w * x, w * y)
        drawCircle(fondo, radius = w / 2, center = Offset(size.width / 2, size.height / 2))
        val trazo = Stroke(width = w * 0.045f, cap = StrokeCap.Round)
        when (icono) {
            "iron" -> {
                val cuerpo = Path().apply {
                    moveTo(w * .2f, w * .68f); lineTo(w * .82f, w * .68f)
                    quadraticBezierTo(w * .78f, w * .46f, w * .52f, w * .4f)
                    lineTo(w * .2f, w * .4f); close()
                }
                drawPath(cuerpo, color)
                drawLine(color, p(.3f, .4f), p(.3f, .3f), w * .045f, StrokeCap.Round)
                drawLine(color, p(.3f, .3f), p(.58f, .3f), w * .045f, StrokeCap.Round)
                drawLine(color, p(.58f, .3f), p(.58f, .42f), w * .045f, StrokeCap.Round)
            }
            "shirt" -> {
                val camisa = Path().apply {
                    moveTo(w * .35f, w * .22f); lineTo(w * .18f, w * .36f); lineTo(w * .28f, w * .48f)
                    lineTo(w * .34f, w * .43f); lineTo(w * .34f, w * .78f); lineTo(w * .66f, w * .78f)
                    lineTo(w * .66f, w * .43f); lineTo(w * .72f, w * .48f); lineTo(w * .82f, w * .36f)
                    lineTo(w * .65f, w * .22f); quadraticBezierTo(w * .5f, w * .34f, w * .35f, w * .22f); close()
                }
                drawPath(camisa, color)
            }
            else -> { // wash
                drawRoundRect(color, topLeft = p(.26f, .2f), size = Size(w * .48f, w * .6f),
                    cornerRadius = CornerRadius(w * .06f), style = trazo)
                drawLine(color, p(.26f, .34f), p(.74f, .34f), w * .04f)
                drawCircle(color, w * .03f, p(.35f, .27f))
                drawCircle(color, w * .15f, p(.5f, .58f), style = trazo)
            }
        }
    }
}
