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
import java.util.Locale
import kotlin.math.roundToInt

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
    var nota1 by remember { mutableStateOf(0f) }
    var nota2 by remember { mutableStateOf(0f) }
    var nota3 by remember { mutableStateOf(0f) }
    var nota4 by remember { mutableStateOf(0f) }

    var redondear by remember { mutableStateOf(false) }
    var confirmado by remember { mutableStateOf(false) }
    var calculado by remember { mutableStateOf(false) }

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

            Spacer(modifier = Modifier.height(16.dp))

            ItemCurso(
                nombre = "Fundamentos de Programación",
                porcentaje = "20%",
                nota = nota1,
                purpleColor = purpleColor,
                onValueChange = { nota1 = it; calculado = false }
            )

            ItemCurso(
                nombre = "Programación Orientada a Objetos",
                porcentaje = "25%",
                nota = nota2,
                purpleColor = purpleColor,
                onValueChange = { nota2 = it; calculado = false }
            )

            ItemCurso(
                nombre = "Programación en Móviles",
                porcentaje = "30%",
                nota = nota3,
                purpleColor = purpleColor,
                onValueChange = { nota3 = it; calculado = false }
            )

            ItemCurso(
                nombre = "Base de Datos",
                porcentaje = "25%",
                nota = nota4,
                purpleColor = purpleColor,
                onValueChange = { nota4 = it; calculado = false }
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Redondear promedio final", fontSize = 14.sp, color = Color(0xFF1D1B20))
                Switch(
                    checked = redondear,
                    onCheckedChange = { redondear = it },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = purpleColor
                    )
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                Checkbox(
                    checked = confirmado,
                    onCheckedChange = { confirmado = it },
                    colors = CheckboxDefaults.colors(checkedColor = purpleColor)
                )
                Text("Confirmo que las notas son correctas", fontSize = 14.sp, color = Color(0xFF1D1B20))
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { calculado = true },
                enabled = confirmado,
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = purpleColor,
                    disabledContainerColor = Color(0xFFC8BFE7),
                    disabledContentColor = Color.White
                )
            ) {
                Text(
                    "CALCULAR PROMEDIO",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    letterSpacing = 0.5.sp
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedButton(
                onClick = {
                    nota1 = 0f
                    nota2 = 0f
                    nota3 = 0f
                    nota4 = 0f
                    redondear = false
                    confirmado = false
                    calculado = false
                },
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp)
            ) {
                Text(
                    "LIMPIAR",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = purpleColor
                )
            }

            if (!calculado) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Asigna las notas y confirma para calcular",
                    color = Color(0xFF79747E),
                    fontSize = 13.sp
                )
            } else {
                val pPonderado = calcularPonderado(nota1, nota2, nota3, nota4)
                val pFinal = if (redondear) pPonderado.roundToInt().toFloat() else pPonderado
                val (obsTexto, obsColor) = obtenerObservacion(pFinal)

                Spacer(modifier = Modifier.height(16.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {

                        Text(
                            text = "Aporte por curso:",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = Color(0xFF1D1B20)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("• Fundamentos: ${nota1.toInt()} × 20% = ${String.format(Locale.US, "%.2f", nota1 * 0.20f)}", fontSize = 13.sp, color = Color(0xFF49454F))
                        Text("• POO: ${nota2.toInt()} × 25% = ${String.format(Locale.US, "%.2f", nota2 * 0.25f)}", fontSize = 13.sp, color = Color(0xFF49454F))
                        Text("• Móviles: ${nota3.toInt()} × 30% = ${String.format(Locale.US, "%.2f", nota3 * 0.30f)}", fontSize = 13.sp, color = Color(0xFF49454F))
                        Text("• Base de Datos: ${nota4.toInt()} × 25% = ${String.format(Locale.US, "%.2f", nota4 * 0.25f)}", fontSize = 13.sp, color = Color(0xFF49454F))

                        Divider(modifier = Modifier.padding(vertical = 12.dp), color = Color(0xFFE6E0E9))

                        Row {
                            Text("Promedio ponderado: ", fontSize = 15.sp, color = Color(0xFF49454F))
                            Text(
                                String.format(Locale.US, "%.2f", pPonderado),
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF49454F)
                            )
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        Row(verticalAlignment = Alignment.Bottom) {
                            Text("Promedio final: ", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = purpleColor)
                            Text(
                                text = if (redondear) "${pFinal.toInt()}" else String.format(Locale.US, "%.2f", pFinal),
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                                color = purpleColor
                            )
                        }

                        if (redondear) {
                            Text("(redondeado)", fontSize = 12.sp, color = Color(0xFF79747E))
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Surface(
                            color = Color(0xFFE8F5E9),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = obsTexto,
                                color = obsColor,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("✓ ", color = Color(0xFF2E7D32), fontWeight = FontWeight.Bold)
                    Text(
                        text = "Promedio calculado correctamente",
                        color = Color(0xFF2E7D32),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = "Desarrollado por: Pedro Suarez",
                fontSize = 12.sp,
                color = Color(0xFF79747E),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
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
    onValueChange: (Float) -> Unit
) {
    val badgeBgColor = if (nota < 13f) Color(0xFFFFEBEE) else Color(0xFFE8F5E9)
    val badgeTextColor = if (nota < 13f) Color(0xFFC62828) else Color(0xFF2E7D32)

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
                color = badgeBgColor,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.widthIn(min = 36.dp)
            ) {
                Text(
                    text = "${nota.toInt()}",
                    fontWeight = FontWeight.Bold,
                    color = badgeTextColor,
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

fun calcularPonderado(n1: Float, n2: Float, n3: Float, n4: Float): Float {
    return (n1 * 0.20f) + (n2 * 0.25f) + (n3 * 0.30f) + (n4 * 0.25f)
}

fun obtenerObservacion(promedio: Float): Pair<String, Color> {
    return when {
        promedio >= 17f -> Pair("EXCELENTE", Color(0xFF2E7D32))
        promedio >= 13f -> Pair("APROBADO", Color(0xFF2E7D32))
        promedio >= 10f -> Pair("EN RECUPERACIÓN", Color(0xFFF57F17))
        else -> Pair("DESAPROBADO", Color(0xFFC62828))
    }
}