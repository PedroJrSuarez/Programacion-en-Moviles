package com.saludplus.citas.ui.screens.auth

import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.saludplus.citas.data.repository.Repositorio
import com.saludplus.citas.navigation.Rutas
import com.saludplus.citas.ui.components.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroScreen(navController: NavController) {
    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var clave by remember { mutableStateOf("") }
    var confirmar by remember { mutableStateOf("") }
    var acepta by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf("") }

    Scaffold(topBar = { BarraSuperior("Crear cuenta", navController) }) { pad ->
        Column(
            Modifier.padding(pad).padding(20.dp).verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            CampoTexto(nombre, { nombre = it }, "Nombre completo")
            CampoTexto(correo, { correo = it }, "Correo electrónico", teclado = KeyboardType.Email)
            CampoTexto(telefono, { telefono = it }, "Teléfono", teclado = KeyboardType.Phone)
            CampoTexto(clave, { clave = it }, "Contraseña (mín. 6)", password = true)
            CampoTexto(confirmar, { confirmar = it }, "Confirmar contraseña", password = true)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(acepta, { acepta = it })
                TextButton(onClick = { navController.navigate(Rutas.TERMINOS) }) {
                    Text("Acepto los términos y condiciones")
                }
            }
            MensajeError(error)
            BotonPrincipal("Registrarme") {
                error = when {
                    nombre.isBlank() -> "Ingresa tu nombre"
                    !correo.contains("@") || !correo.contains(".") -> "Correo no válido"
                    telefono.length < 9 -> "Teléfono no válido (9 dígitos)"
                    clave.length < 6 -> "La contraseña debe tener al menos 6 caracteres"
                    clave != confirmar -> "Las contraseñas no coinciden"
                    !acepta -> "Debes aceptar los términos"
                    !Repositorio.registrarUsuario(nombre.trim(), correo.trim(), clave, telefono.trim()) ->
                        "Ese correo ya está registrado"
                    else -> ""
                }
                if (error.isEmpty()) {
                    navController.navigate(Rutas.HOME) { popUpTo(Rutas.SPLASH) { inclusive = true } }
                }
            }
        }
    }
}
