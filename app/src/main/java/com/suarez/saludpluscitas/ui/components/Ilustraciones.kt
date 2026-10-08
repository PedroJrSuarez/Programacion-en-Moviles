package com.suarez.saludpluscitas.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.suarez.saludpluscitas.ui.theme.*

private val Piel = Color(0xFFF2C6A0)
private val Pelo = Color(0xFF2B2230)

/** Avatar dibujado (sin imágenes externas): mujer = pelo largo, hombre = pelo corto y bata. */
@Composable
fun AvatarMedico(nombre: String, tam: Dp = 64.dp) {
    val hombre = nombre.startsWith("Dr.")
    Canvas(Modifier.size(tam).clip(CircleShape)) {
        val w = size.width
        drawRect(Color(0xFFDCE6F2))
        if (!hombre) drawRoundRect(Pelo, Offset(w * 0.24f, w * 0.2f), Size(w * 0.52f, w * 0.55f), CornerRadius(w * 0.26f))
        drawOval(if (hombre) Color(0xFFF7F9FC) else Color(0xFF7F9DBA), Offset(w * 0.1f, w * 0.68f), Size(w * 0.8f, w * 0.6f))
        if (hombre) {
            val v = Path().apply {
                moveTo(w * 0.42f, w * 0.7f); lineTo(w * 0.58f, w * 0.7f); lineTo(w * 0.5f, w * 0.88f); close()
            }
            drawPath(v, Color(0xFF7FA8D8))
        }
        drawRect(Piel, Offset(w * 0.43f, w * 0.55f), Size(w * 0.14f, w * 0.18f))
        drawOval(Piel, Offset(w * 0.32f, w * 0.24f), Size(w * 0.36f, w * 0.42f))
        drawArc(Pelo, 180f, 180f, true, Offset(w * 0.3f, w * 0.2f), Size(w * 0.4f, w * 0.26f))
        drawCircle(Pelo, w * 0.018f, Offset(w * 0.43f, w * 0.45f))
        drawCircle(Pelo, w * 0.018f, Offset(w * 0.57f, w * 0.45f))
    }
}

/** Logo de la clínica: cruz azul con corazón blanco. */
@Composable
fun LogoClinica(tam: Dp = 84.dp) {
    Box(Modifier.size(tam), contentAlignment = Alignment.Center) {
        Canvas(Modifier.size(tam)) {
            val w = size.width
            drawRoundRect(Azul, Offset(w * 0.3f, 0f), Size(w * 0.4f, w), CornerRadius(w * 0.14f))
            drawRoundRect(Azul, Offset(0f, w * 0.3f), Size(w, w * 0.4f), CornerRadius(w * 0.14f))
            drawRoundRect(Color(0xFF3AA6C9), Offset(w * 0.62f, w * 0.02f), Size(w * 0.32f, w * 0.3f), CornerRadius(w * 0.12f))
        }
        Icon(Icons.Default.Favorite, null, tint = Color.White, modifier = Modifier.size(tam * 0.34f))
    }
}

/** Ilustración del doctor de la pantalla Splash. */
@Composable
fun IlustracionDoctor(modifier: Modifier = Modifier) {
    Canvas(modifier) {
        val w = size.width
        val h = size.height
        // hojas
        rotate(-25f, Offset(w * 0.08f, h * 0.7f)) {
            drawOval(Color(0xFF4DB39E), Offset(w * 0.0f, h * 0.45f), Size(w * 0.14f, h * 0.42f))
        }
        rotate(20f, Offset(w * 0.2f, h * 0.7f)) {
            drawOval(Color(0xFF86C9A8), Offset(w * 0.1f, h * 0.55f), Size(w * 0.12f, h * 0.35f))
        }
        rotate(25f, Offset(w * 0.92f, h * 0.65f)) {
            drawOval(Color(0xFF3B8EA5), Offset(w * 0.84f, h * 0.35f), Size(w * 0.15f, h * 0.5f))
        }
        rotate(-15f, Offset(w * 0.8f, h * 0.7f)) {
            drawOval(Color(0xFF6BB7C7), Offset(w * 0.74f, h * 0.5f), Size(w * 0.11f, h * 0.35f))
        }
        // bata
        drawRoundRect(Color.White, Offset(w * 0.2f, h * 0.52f), Size(w * 0.6f, h * 0.8f), CornerRadius(w * 0.2f))
        drawRoundRect(Borde, Offset(w * 0.2f, h * 0.52f), Size(w * 0.6f, h * 0.8f), CornerRadius(w * 0.2f), style = Stroke(2f))
        // camisa, corbata
        drawPath(Path().apply {
            moveTo(w * 0.4f, h * 0.52f); lineTo(w * 0.6f, h * 0.52f); lineTo(w * 0.5f, h * 0.8f); close()
        }, Color(0xFF7FA8D8))
        drawPath(Path().apply {
            moveTo(w * 0.485f, h * 0.58f); lineTo(w * 0.515f, h * 0.58f); lineTo(w * 0.525f, h * 0.76f)
            lineTo(w * 0.5f, h * 0.8f); lineTo(w * 0.475f, h * 0.76f); close()
        }, Navy)
        // estetoscopio
        drawArc(Color(0xFF5B6B80), 10f, 160f, false, Offset(w * 0.33f, h * 0.5f), Size(w * 0.34f, h * 0.38f), style = Stroke(w * 0.014f))
        // portapapeles
        rotate(-12f, Offset(w * 0.52f, h * 0.88f)) {
            drawRoundRect(Azul, Offset(w * 0.44f, h * 0.76f), Size(w * 0.17f, h * 0.24f), CornerRadius(8f))
        }
        // mano que señala
        drawCircle(Piel, w * 0.032f, Offset(w * 0.77f, h * 0.5f))
        // cuello y cabeza
        drawRect(Piel, Offset(w * 0.465f, h * 0.4f), Size(w * 0.07f, h * 0.15f))
        drawOval(Piel, Offset(w * 0.4f, h * 0.12f), Size(w * 0.2f, h * 0.34f))
        drawOval(Piel, Offset(w * 0.385f, h * 0.27f), Size(w * 0.03f, h * 0.07f))
        drawOval(Piel, Offset(w * 0.585f, h * 0.27f), Size(w * 0.03f, h * 0.07f))
        // pelo
        drawArc(Pelo, 170f, 200f, true, Offset(w * 0.385f, h * 0.07f), Size(w * 0.23f, h * 0.24f))
        // ojos y sonrisa
        drawCircle(Pelo, w * 0.01f, Offset(w * 0.46f, h * 0.29f))
        drawCircle(Pelo, w * 0.01f, Offset(w * 0.54f, h * 0.29f))
        drawArc(Color(0xFFB5543F), 20f, 140f, false, Offset(w * 0.47f, h * 0.31f), Size(w * 0.06f, h * 0.06f), style = Stroke(3f))
    }
}
