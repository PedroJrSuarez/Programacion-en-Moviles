package com.suarez.saludpluscitas.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.suarez.saludpluscitas.data.repository.Repositorio
import com.suarez.saludpluscitas.navigation.Rutas
import com.suarez.saludpluscitas.ui.components.*
import com.suarez.saludpluscitas.ui.theme.*

@Composable
fun LoginScreen(navController: NavController) {
    var usuario by remember { mutableStateOf("") }
    var clave by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }

    Scaffold(containerColor = Color.White, topBar = { BarraSuperior("Iniciar sesión", navController) }) { pad ->
        Column(
            Modifier.padding(pad).verticalScroll(rememberScrollState()).padding(horizontal = 22.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(12.dp))
            LogoClinica(64.dp)
            Spacer(Modifier.height(10.dp))
            Text("Bienvenido de nuevo", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Navy)
            Text("Ingresa para gestionar tus citas", color = Gris, fontSize = 13.sp)
            Spacer(Modifier.height(24.dp))
            Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                CampoConIcono(Icons.Default.Person, "Correo o teléfono", usuario, { usuario = it })
                CampoConIcono(Icons.Default.Lock, "Contraseña", clave, { clave = it }, password = true)
            }
            Spacer(Modifier.height(8.dp))
            MensajeError(error)
            Spacer(Modifier.height(12.dp))
            BotonPrincipal("Iniciar sesión") {
                if (usuario.isBlank() || clave.isBlank()) {
                    error = "Completa todos los campos"
                } else if (Repositorio.iniciarSesion(usuario, clave)) {
                    navController.navigate(Rutas.HOME) { popUpTo(Rutas.SPLASH) { inclusive = true } }
                } else {
                    error = "Usuario o contraseña incorrectos"
                }
            }
            Spacer(Modifier.height(10.dp))
            Spacer(Modifier.height(24.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("¿No tienes cuenta? ", color = Navy, fontSize = 13.sp)
                Text(
                    "Regístrate", color = Azul, fontSize = 13.sp, fontWeight = FontWeight.Medium,
                    modifier = Modifier.clickable { navController.navigate(Rutas.REGISTRO) }
                )
            }
        }
    }
}
