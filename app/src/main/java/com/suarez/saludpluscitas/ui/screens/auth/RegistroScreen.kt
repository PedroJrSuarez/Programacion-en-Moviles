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
fun RegistroScreen(navController: NavController) {
    var nombre by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var clave by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }

    Column(
        Modifier.fillMaxSize().background(Color.White).verticalScroll(rememberScrollState()).padding(horizontal = 22.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(48.dp))
        Text("Crear cuenta", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Navy)
        Text("Regístrate para agendar tus citas", color = Gris, fontSize = 13.sp)
        Spacer(Modifier.height(24.dp))
        Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
            CampoConIcono(Icons.Default.Person, "Nombres y apellidos", nombre, { nombre = it })
            CampoConIcono(Icons.Default.Phone, "Teléfono", telefono, { telefono = it.filter(Char::isDigit).take(9) }, teclado = KeyboardType.Phone)
            CampoConIcono(Icons.Default.Email, "Correo (opcional)", correo, { correo = it }, teclado = KeyboardType.Email)
            CampoConIcono(Icons.Default.Lock, "Contraseña", clave, { clave = it }, password = true)
        }
        Spacer(Modifier.height(8.dp))
        MensajeError(error)
        Spacer(Modifier.height(12.dp))
        BotonPrincipal("Registrarme") {
            error = when {
                nombre.isBlank() -> "Ingresa tus nombres y apellidos"
                telefono.length != 9 -> "El teléfono debe tener 9 dígitos"
                correo.isNotBlank() && !correo.contains("@") -> "Correo no válido"
                clave.length < 6 -> "La contraseña debe tener al menos 6 caracteres"
                !Repositorio.registrarUsuario(nombre.trim(), correo.trim(), clave, telefono) ->
                    "Ya existe una cuenta con ese teléfono o correo"
                else -> ""
            }
            if (error.isEmpty()) {
                navController.navigate(Rutas.HOME) { popUpTo(Rutas.SPLASH) { inclusive = true } }
            }
        }
        Spacer(Modifier.height(14.dp))
        Text("Al registrarte aceptas nuestros", color = Gris, fontSize = 12.sp)
        Text(
            "Términos y Condiciones", color = Azul, fontSize = 12.sp, fontWeight = FontWeight.Medium,
            modifier = Modifier.clickable { navController.navigate(Rutas.TERMINOS) }
        )
        Spacer(Modifier.height(28.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("¿Ya tienes cuenta? ", color = Navy, fontSize = 13.sp)
            Text(
                "Iniciar sesión", color = Azul, fontSize = 13.sp, fontWeight = FontWeight.Medium,
                modifier = Modifier.clickable { navController.navigate(Rutas.LOGIN) }
            )
        }
        Spacer(Modifier.height(24.dp))
    }
}
