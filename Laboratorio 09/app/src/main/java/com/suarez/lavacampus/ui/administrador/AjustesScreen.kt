package com.suarez.lavacampus.ui.administrador

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.suarez.lavacampus.data.local.entity.Usuario
import com.suarez.lavacampus.ui.components.FilaSwitch
import com.suarez.lavacampus.ui.components.PantallaBase
import com.suarez.lavacampus.viewmodel.SesionViewModel

@Composable
fun AjustesScreen(sesion: SesionViewModel, usuario: Usuario) {
    val oscuro by sesion.modoOscuro.collectAsState()
    val avisos by sesion.notificaciones.collectAsState()
    val recibir by sesion.recibirPedidos.collectAsState()

    PantallaBase("Ajustes") { pad ->
        Column(
            Modifier.padding(pad).fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Card(Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp)) {
                    Text(usuario.nombre, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                    Text(usuario.correo, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
            OutlinedButton(onClick = sesion::cambiarPerfil, Modifier.fillMaxWidth()) { Text("Cambiar de perfil") }
            Card(Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp)) {
                    FilaSwitch("Modo oscuro", oscuro, sesion::guardarModoOscuro)
                    FilaSwitch("Notificaciones", avisos, sesion::guardarNotificaciones)
                    FilaSwitch("Recibir pedidos", recibir, sesion::guardarRecibirPedidos)
                }
            }
            Button(onClick = sesion::cerrarSesion, Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)) {
                Text("Cerrar sesión")
            }
        }
    }
}
