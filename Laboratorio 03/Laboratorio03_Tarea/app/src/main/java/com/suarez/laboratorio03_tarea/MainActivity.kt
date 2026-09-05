package com.suarez.laboratorio03_tarea

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
    MaterialTheme(colorScheme = purpleTheme, content = content)
}

@Composable
fun RegistroNotasApp() {
    val purpleColor = Color(0xFF5B45A0)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFECE6F0))
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(purpleColor)
                .padding(horizontal = 20.dp, vertical = 24.dp)
        ) {
            Text(
                text = "Registro de Notas",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Notas del ciclo",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color(0xFF1D1B20)
            )
            Text(
                text = "Desliza para asignar cada nota (0 a 20)",
                color = Color(0xFF79747E),
                fontSize = 13.sp
            )
        }
    }
}