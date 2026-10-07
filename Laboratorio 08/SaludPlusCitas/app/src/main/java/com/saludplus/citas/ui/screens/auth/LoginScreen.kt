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

@Composable
fun LoginScreen(navController: NavController) {
    var correo by remember { mutableStateOf("") }
    var clave by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }

    Scaffold(topBar = { BarraSuperior("Iniciar sesión", navController) }) { pad ->
        Column(Modifier.padding(pad).padding(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text("Bienvenido de nuevo", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            CampoTexto(correo, { correo = it }, "Correo electrónico", teclado = KeyboardType.Email)
            CampoTexto(clave, { clave = it }, "Contraseña", password = true)
            MensajeError(error)
            BotonPrincipal("Ingresar") {
                if (correo.isBlank() || clave.isBlank()) {
                    error = "Completa todos los campos"
                } else if (Repositorio.iniciarSesion(correo, clave)) {
                    navController.navigate(Rutas.HOME) { popUpTo(Rutas.SPLASH) { inclusive = true } }
                } else {
                    error = "Correo o contraseña incorrectos"
                }
            }
            TextButton(onClick = { navController.navigate(Rutas.REGISTRO) }) { Text("¿No tienes cuenta? Regístrate") }
        }
    }
}
