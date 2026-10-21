package com.suarez.lavacampus.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.suarez.lavacampus.model.Rol

private val EstudianteClaro = lightColorScheme(
    primary = TurquesaPrimario, onPrimary = Color.White,
    primaryContainer = TurquesaContenedor, onPrimaryContainer = Color(0xFF083344),
    background = TurquesaFondo, onBackground = Color(0xFF0F172A),
    surface = TurquesaFondo, onSurface = Color(0xFF0F172A),
    surfaceVariant = Color(0xFFE0F2F7), onSurfaceVariant = Color(0xFF475569),
    surfaceContainerHighest = Color.White,
    tertiaryContainer = Color(0xFFFEF3C7), error = RojoError,
    errorContainer = Color(0xFFFEE2E2), onErrorContainer = Color(0xFF7F1D1D)
)
private val AdminClaro = lightColorScheme(
    primary = VioletaPrimario, onPrimary = Color.White,
    primaryContainer = VioletaContenedor, onPrimaryContainer = Color(0xFF2E1065),
    background = VioletaFondo, onBackground = Color(0xFF0F172A),
    surface = VioletaFondo, onSurface = Color(0xFF0F172A),
    surfaceVariant = Color(0xFFEDE9F6), onSurfaceVariant = Color(0xFF475569),
    surfaceContainerHighest = Color.White,
    tertiaryContainer = Color(0xFFFEF3C7), error = RojoError,
    errorContainer = Color(0xFFFEE2E2), onErrorContainer = Color(0xFF7F1D1D)
)
private val EstudianteOscuro = darkColorScheme(
    primary = Color(0xFF22D3EE), onPrimary = Color(0xFF083344),
    primaryContainer = Color(0xFF155E75), onPrimaryContainer = TurquesaContenedor,
    background = Color(0xFF0B1A1F), onBackground = Color(0xFFE2F4F8),
    surface = Color(0xFF0B1A1F), onSurface = Color(0xFFE2F4F8),
    surfaceVariant = Color(0xFF1B3038), onSurfaceVariant = Color(0xFFA7BEC6),
    surfaceContainerHighest = Color(0xFF16272D),
    tertiaryContainer = Color(0xFF78350F), error = Color(0xFFF87171),
    errorContainer = Color(0xFF7F1D1D), onErrorContainer = Color(0xFFFEE2E2)
)
private val AdminOscuro = darkColorScheme(
    primary = Color(0xFFA78BFA), onPrimary = Color(0xFF2E1065),
    primaryContainer = Color(0xFF4C1D95), onPrimaryContainer = VioletaContenedor,
    background = Color(0xFF15111F), onBackground = Color(0xFFEDE9FE),
    surface = Color(0xFF15111F), onSurface = Color(0xFFEDE9FE),
    surfaceVariant = Color(0xFF2A2340), onSurfaceVariant = Color(0xFFB7AED0),
    surfaceContainerHighest = Color(0xFF211B33),
    tertiaryContainer = Color(0xFF78350F), error = Color(0xFFF87171),
    errorContainer = Color(0xFF7F1D1D), onErrorContainer = Color(0xFFFEE2E2)
)

@Composable
fun LavaTheme(
    rol: Rol,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colores: ColorScheme = when {
        rol == Rol.ADMIN && darkTheme -> AdminOscuro
        rol == Rol.ADMIN -> AdminClaro
        darkTheme -> EstudianteOscuro
        else -> EstudianteClaro
    }
    MaterialTheme(colorScheme = colores, typography = LavaTypography, content = content)
}
