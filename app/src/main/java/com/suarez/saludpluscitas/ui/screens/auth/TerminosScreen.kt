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
fun TerminosScreen(navController: NavController) {
    val secciones = listOf(
        "1. Uso del servicio" to "SaludPlus permite agendar citas médicas. El usuario se compromete a brindar datos verdaderos.",
        "2. Citas" to "Las citas pueden cancelarse desde el detalle de la cita. Se recomienda llegar 10 minutos antes.",
        "3. Privacidad" to "Tus datos personales se usan solo para gestionar tus citas y no se comparten con terceros.",
        "4. Responsabilidad" to "La app no reemplaza una atención de emergencia. Ante una urgencia, acude al centro de salud más cercano."
    )
    Scaffold(containerColor = Color.White, topBar = { BarraSuperior("Términos y Condiciones", navController) }) { pad ->
        Column(Modifier.padding(pad).padding(20.dp).verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(14.dp)) {
            secciones.forEach { (titulo, cuerpo) ->
                TarjetaBase {
                    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(titulo, fontWeight = FontWeight.Bold, color = Navy)
                        Text(cuerpo, color = Gris, fontSize = 14.sp)
                    }
                }
            }
            BotonPrincipal("Entendido") { navController.popBackStack() }
        }
    }
}
