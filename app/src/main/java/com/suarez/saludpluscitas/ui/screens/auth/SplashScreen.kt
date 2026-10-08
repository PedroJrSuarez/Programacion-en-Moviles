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
fun SplashScreen(navController: NavController) {
    Column(
        Modifier.fillMaxSize()
            .background(Brush.verticalGradient(listOf(Color(0xFFE6F0FF), Color.White)))
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(56.dp))
        LogoClinica(84.dp)
        Spacer(Modifier.height(12.dp))
        Text("Clínica", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Navy)
        Text("SaludPlus", fontSize = 38.sp, fontWeight = FontWeight.ExtraBold, color = Navy)
        Spacer(Modifier.height(4.dp))
        Text("Tu salud, nuestra prioridad", color = Gris, fontSize = 14.sp)
        Spacer(Modifier.weight(1f))
        IlustracionDoctor(Modifier.fillMaxWidth().height(250.dp))
        Spacer(Modifier.height(8.dp))
        BotonPrincipal("Comenzar") { navController.navigate(Rutas.REGISTRO) }
        TextButton(onClick = { navController.navigate(Rutas.LOGIN) }) {
            Text("Ya tengo una cuenta", color = Azul, fontWeight = FontWeight.Medium)
        }
        Spacer(Modifier.height(12.dp))
    }
}
