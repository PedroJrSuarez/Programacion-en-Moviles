package com.suarez.laboratorio03_tarea

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
    var nota1 by remember { mutableStateOf(0f) }
    var nota2 by remember { mutableStateOf(0f) }
    var nota3 by remember { mutableStateOf(0f) }
    var nota4 by remember { mutableStateOf(0f) }

    val purpleColor = Color(0xFF5B45A0)
    val lightPurpleChip = Color(0xFFEDE7F6)

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

            Spacer(modifier = Modifier.height(16.dp))

            ItemCurso(
                nombre = "Fundamentos de Programación",
                porcentaje = "20%",
                nota = nota1,
                purpleColor = purpleColor,
                chipColor = lightPurpleChip,
                onValueChange = { nota1 = it }
            )
            ItemCurso(
                nombre = "Programación Orientada a Objetos",
                porcentaje = "25%",
                nota = nota2,
                purpleColor = purpleColor,
                chipColor = lightPurpleChip,
                onValueChange = { nota2 = it }
            )
            ItemCurso(
                nombre = "Programación en Móviles",
                porcentaje = "30%",
                nota = nota3,
                purpleColor = purpleColor,
                chipColor = lightPurpleChip,
                onValueChange = { nota3 = it }
            )
            ItemCurso(
                nombre = "Base de Datos",
                porcentaje = "25%",
                nota = nota4,
                purpleColor = purpleColor,
                chipColor = lightPurpleChip,
                onValueChange = { nota4 = it }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemCurso(
    nombre: String,
    porcentaje: String,
    nota: Float,
    purpleColor: Color,
    chipColor: Color,
    onValueChange: (Float) -> Unit
) {
    Column(modifier = Modifier.padding(bottom = 8.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(nombre, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color(0xFF1D1B20))
                Spacer(modifier = Modifier.width(4.dp))
                Text("($porcentaje)", fontSize = 11.sp, color = Color(0xFF938F96))
            }

            Surface(
                color = chipColor,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.widthIn(min = 36.dp)
            ) {
                Text(
                    text = "${nota.toInt()}",
                    fontWeight = FontWeight.Bold,
                    color = purpleColor,
                    fontSize = 13.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }

        Slider(
            value = nota,
            onValueChange = onValueChange,
            valueRange = 0f..20f,
            colors = SliderDefaults.colors(
                thumbColor = Color(0xFF65558F),
                activeTickColor = Color.Transparent,
                inactiveTickColor = Color.Transparent
            ),
            thumb = {
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .background(Color(0xFF65558F), shape = CircleShape)
                )
            },
            track = { sliderState ->
                val fraction = (sliderState.value - sliderState.valueRange.start) /
                        (sliderState.valueRange.endInclusive - sliderState.valueRange.start)

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                        .background(Color(0xFFE6E0E9), shape = CircleShape)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(fraction)
                            .fillMaxHeight()
                            .background(Color(0xFF65558F), shape = CircleShape)
                    )
                }
            }
        )
    }
}