package com.suarez.laboratorio03_tarea

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RegistroNotasTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFFECE6F0)
                ) {
                    RegistroNotasApp()
                }
            }
        }
    }
}

@Composable
fun RegistroNotasTheme(content: @Composable () -> Unit) {
    val purpleTheme = lightColorScheme(
        primary = Color(0xFF65558F),
        onPrimary = Color.White,
        primaryContainer = Color(0xFFE8DEF8),
        onPrimaryContainer = Color(0xFF1D192B),
        surface = Color.White,
        background = Color(0xFFECE6F0)
    )
    MaterialTheme(
        colorScheme = purpleTheme,
        content = content
    )
}

@Composable
fun RegistroNotasApp() {
}