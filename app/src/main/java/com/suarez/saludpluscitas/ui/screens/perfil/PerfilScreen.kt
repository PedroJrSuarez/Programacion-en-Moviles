package com.suarez.saludpluscitas.ui.screens.perfil

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.suarez.saludpluscitas.data.repository.Repositorio
import com.suarez.saludpluscitas.navigation.Rutas
import com.suarez.saludpluscitas.ui.components.*
import com.suarez.saludpluscitas.ui.theme.*

@Composable
fun PerfilScreen(navController: NavController) {
    val u = Repositorio.usuarioActual
    val totalCitas = Repositorio.citasDelUsuario().size

    Scaffold(
        containerColor = Color.White,
        topBar = { BarraSuperior("Mis datos", navController) },
        bottomBar = { BarraInferior(Rutas.PERFIL, navController) }
    ) { pad ->
        Column(Modifier.padding(pad).padding(horizontal = 20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(Modifier.height(12.dp))
            IconoTile(Icons.Default.Person, Morado, MoradoClaro, 92.dp, forma = CircleShape)
            Spacer(Modifier.height(10.dp))
            Text(u?.nombre ?: "Invitado", fontSize = 22.sp, fontWeight = FontWeight.ExtraBold, color = Navy)
            Text("Paciente SaludPlus", color = Gris, fontSize = 13.sp)
            Spacer(Modifier.height(20.dp))
            TarjetaBase {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    InfoFila(Icons.Default.Person, "Nombres y apellidos", u?.nombre ?: "-")
                    InfoFila(Icons.Default.Phone, "Teléfono", u?.telefono?.ifBlank { "-" } ?: "-")
                    InfoFila(Icons.Default.Email, "Correo", u?.correo?.ifBlank { "-" } ?: "-")
                    InfoFila(Icons.Default.EventAvailable, "Citas agendadas", totalCitas.toString())
                }
            }
            Spacer(Modifier.weight(1f))
            BotonSecundario("Cerrar sesión", color = Rojo) {
                Repositorio.cerrarSesion()
                navController.navigate(Rutas.SPLASH) {
                    popUpTo(navController.graph.id) { inclusive = true }
                }
            }
            Spacer(Modifier.height(16.dp))
        }
    }
}
