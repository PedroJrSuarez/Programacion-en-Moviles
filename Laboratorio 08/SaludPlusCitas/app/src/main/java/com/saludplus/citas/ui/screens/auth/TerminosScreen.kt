package com.saludplus.citas.ui.screens.auth

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
fun TerminosScreen(navController: NavController) {
    val secciones = listOf(
        "1. Uso del servicio" to "SaludPlus permite agendar citas médicas. El usuario se compromete a brindar datos verdaderos.",
        "2. Citas" to "Las citas pueden cancelarse desde el detalle de la cita. Se recomienda llegar 10 minutos antes.",
        "3. Privacidad" to "Tus datos personales se usan solo para gestionar tus citas y no se comparten con terceros.",
        "4. Responsabilidad" to "La app no reemplaza una atención de emergencia. Ante una urgencia, acude al centro de salud más cercano."
    )
    Scaffold(topBar = { BarraSuperior("Términos y condiciones", navController) }) { pad ->
        Column(Modifier.padding(pad).padding(20.dp).verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(14.dp)) {
            secciones.forEach { (titulo, cuerpo) ->
                Text(titulo, fontWeight = FontWeight.Bold)
                Text(cuerpo)
            }
            BotonPrincipal("Entendido") { navController.popBackStack() }
        }
    }
}
