package com.suarez.lab03registroproducto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.suarez.lab03registroproducto.ui.theme.Lab03RegistroProductoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab03RegistroProductoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    PantallaRegistro(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun PantallaRegistro(modifier: Modifier = Modifier) {

    var nombre by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var cantidad by remember { mutableStateOf("") }
    var mostrarResumen by remember { mutableStateOf(false) }
    var mensajeError by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Nuevo producto",
            style = MaterialTheme.typography.headlineSmall
        )
        Text(
            text = "Completa los datos y presiona Agregar",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.outline
        )
        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = nombre,
            onValueChange = {
                nombre = it
                if (mensajeError.isNotBlank()) mensajeError = ""
            },
            label = { Text("Nombre del producto") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = precio,
                onValueChange = {
                    precio = it
                    if (mensajeError.isNotBlank()) mensajeError = ""
                },
                label = { Text("Precio (S/)") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            OutlinedTextField(
                value = cantidad,
                onValueChange = {
                    cantidad = it
                    if (mensajeError.isNotBlank()) mensajeError = ""
                },
                label = { Text("Cantidad") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = {
                    val precioNum = precio.toDoubleOrNull()
                    val cantidadNum = cantidad.toIntOrNull()
                    val soloLetrasRegex = Regex("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$")

                    if (nombre.isBlank() || precio.isBlank() || cantidad.isBlank()) {
                        mensajeError = "Todos los campos son obligatorios"
                        mostrarResumen = false
                    } else if (!nombre.trim().matches(soloLetrasRegex)) {
                        mensajeError = "El nombre del producto solo debe contener letras"
                        mostrarResumen = false
                    } else if (precioNum == null || cantidadNum == null) {
                        mensajeError = "Ingresa números válidos en Precio y Cantidad"
                        mostrarResumen = false
                    } else if (precioNum <= 0) {
                        mensajeError = "El precio debe ser mayor a 0"
                        mostrarResumen = false
                    } else if (cantidadNum <= 0) {
                        mensajeError = "La cantidad debe ser mayor a 0"
                        mostrarResumen = false
                    } else {
                        mensajeError = ""
                        mostrarResumen = true
                    }
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("AGREGAR PRODUCTO", fontSize = 12.sp)
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Mejorado a OutlinedButton para jerarquía visual secundaria
            OutlinedButton(
                onClick = {
                    nombre = ""
                    precio = ""
                    cantidad = ""
                    mostrarResumen = false
                    mensajeError = ""
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("LIMPIAR", fontSize = 12.sp)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (mensajeError.isNotBlank()) {
            Text(
                text = mensajeError,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        if (mostrarResumen) {
            val precioNum = precio.toDoubleOrNull() ?: 0.0
            val cantidadNum = cantidad.toIntOrNull() ?: 0
            val importe = precioNum * cantidadNum

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = nombre.trim(), style = MaterialTheme.typography.titleLarge)
                    Text(text = "Precio S/ " + String.format("%.2f", precioNum))
                    Text(text = "Cantidad: $cantidadNum")
                    Text(
                        text = "Importe total: S/ " + String.format("%.2f", importe),
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "✓ Producto registrado correctamente",
                color = Color(0xFF2E7D32),
                style = MaterialTheme.typography.bodyMedium
            )
        } else if (mensajeError.isBlank()) {
            Text(
                text = "Aún no has registrado ningún producto",
                color = MaterialTheme.colorScheme.outline
            )
        }
    }
}