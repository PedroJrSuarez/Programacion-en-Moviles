package com.tecsup.avisoscampus.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Paleta Material 3 Expressive / Bento Ultra-Modern
val IndigoPrimary = Color(0xFF6366F1)
val PurpleSecondary = Color(0xFFA855F7)
val CyanTertiary = Color(0xFF06B6D4)
val EmeraldOk = Color(0xFF10B981)
val RoseNo = Color(0xFFF43F5E)
val AmberWarning = Color(0xFFF59E0B)

val SlateBackground = Color(0xFF0F172A)
val SlateSurface = Color(0xFF1E293B)
val SlateSurfaceVariant = Color(0xFF334155)

val VerdeOk = EmeraldOk
val RojoNo = RoseNo
val GrisSin = Color(0xFF64748B)

private val ExpressiveDarkScheme = darkColorScheme(
    primary = IndigoPrimary,
    onPrimary = Color.White,
    primaryContainer = Color(0xFF312E81),
    onPrimaryContainer = Color(0xFFE0E7FF),
    secondary = PurpleSecondary,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFF581C87),
    onSecondaryContainer = Color(0xFFF3E8FF),
    tertiary = CyanTertiary,
    background = SlateBackground,
    surface = SlateSurface,
    surfaceVariant = SlateSurfaceVariant,
    onBackground = Color(0xFFF8FAFC),
    onSurface = Color(0xFFF8FAFC),
    error = RoseNo,
    errorContainer = Color(0xFF881337),
    onErrorContainer = Color(0xFFFFE4E6)
)

private val ExpressiveLightScheme = lightColorScheme(
    primary = IndigoPrimary,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFEEF2FF),
    onPrimaryContainer = Color(0xFF1E1B4B),
    secondary = PurpleSecondary,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFF3E8FF),
    onSecondaryContainer = Color(0xFF3B0764),
    tertiary = CyanTertiary,
    background = Color(0xFFF8FAFC),
    surface = Color.White,
    surfaceVariant = Color(0xFFF1F5F9),
    onBackground = Color(0xFF0F172A),
    onSurface = Color(0xFF0F172A),
    error = RoseNo,
    errorContainer = Color(0xFFFFE4E6),
    onErrorContainer = Color(0xFF881337)
)

@Composable
fun AvisosCampusTheme(
    darkTheme: Boolean = false, // Modo claro/oscuro expresivo
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) ExpressiveDarkScheme else ExpressiveLightScheme
    MaterialTheme(
        colorScheme = colors,
        content = content
    )
}
