package com.suarez.saludpluscitas.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val Esquema = lightColorScheme(
    primary = Azul,
    onPrimary = Color.White,
    primaryContainer = AzulClaro,
    onPrimaryContainer = Azul,
    background = Color.White,
    surface = Color.White,
    onSurface = Navy,
    onBackground = Navy,
    outline = Gris,
    error = Rojo
)

@Composable
fun SaludPlusTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = Esquema, content = content)
}
