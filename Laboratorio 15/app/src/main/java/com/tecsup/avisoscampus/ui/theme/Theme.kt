package com.tecsup.avisoscampus.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val AzulTema = Color(0xFF1565C0)
val VerdeOk = Color(0xFF2E7D32)
val RojoNo = Color(0xFFC62828)
val GrisSin = Color(0xFF757575)

private val Colores = lightColorScheme(
    primary = AzulTema,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFD6E4FF),
    onPrimaryContainer = Color(0xFF001B3E),
    secondary = Color(0xFF455A64),
    background = Color(0xFFF4F6FA),
    surface = Color.White,
    error = RojoNo,
    errorContainer = Color(0xFFFFDAD6),
    onErrorContainer = Color(0xFF410002)
)

@Composable
fun AvisosCampusTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = Colores, content = content)
}
