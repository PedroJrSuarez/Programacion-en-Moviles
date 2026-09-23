package com.suarez.tecsupfit.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val TecsupFitColorScheme = lightColorScheme(
    primary = TealPrimary,
    onPrimary = White,
    primaryContainer = TealLight,
    onPrimaryContainer = TealPrimary,
    secondaryContainer = GrayCard,
    background = White,
    surface = White,
    surfaceVariant = GrayCard,
    onSurfaceVariant = TextGray
)

@Composable
fun TecsupFitTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = TecsupFitColorScheme,
        typography = MaterialTheme.typography,
        content = content
    )
}
