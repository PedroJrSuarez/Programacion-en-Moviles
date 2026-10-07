package com.saludplus.citas.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
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
fun SplashScreen(navController: NavController) {
    val p = MaterialTheme.colorScheme.primary
    Column(
        Modifier.fillMaxSize()
            .background(Brush.verticalGradient(listOf(MaterialTheme.colorScheme.primaryContainer, Color.White)))
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(Modifier.size(120.dp).clip(CircleShape).background(p), contentAlignment = Alignment.Center) {
            Icon(Icons.Default.Favorite, null, tint = Color.White, modifier = Modifier.size(60.dp))
        }
        Spacer(Modifier.height(20.dp))
        Text("SaludPlus", style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Bold, color = p)
        Text("Agenda tu cita médica en minutos", color = MaterialTheme.colorScheme.outline, textAlign = TextAlign.Center)
        Spacer(Modifier.height(48.dp))
        BotonPrincipal("Crear cuenta") { navController.navigate(Rutas.REGISTRO) }
        Spacer(Modifier.height(12.dp))
        BotonSecundario("Iniciar sesión") { navController.navigate(Rutas.LOGIN) }
    }
}
