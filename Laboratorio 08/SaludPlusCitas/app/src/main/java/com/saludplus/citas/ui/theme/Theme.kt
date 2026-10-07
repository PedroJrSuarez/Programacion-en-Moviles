package com.saludplus.citas.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val Esquema = lightColorScheme(
    primary = Turquesa,
    onPrimary = Color.White,
    primaryContainer = TurquesaClaro,
    onPrimaryContainer = TurquesaOscuro,
    secondary = Color(0xFF1565C0),
    background = Fondo,
    surface = Color.White
)

@Composable
fun SaludPlusTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = Esquema, content = content)
}
