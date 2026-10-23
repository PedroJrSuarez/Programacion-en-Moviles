package com.suarez.lavacampus.ui.estudiante

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
import com.suarez.lavacampus.viewmodel.MisPedidosViewModel
import com.suarez.lavacampus.viewmodel.SesionViewModel
import java.util.Locale

@Composable
fun PerfilScreen(sesion: SesionViewModel, mis: MisPedidosViewModel, usuario: Usuario) {
    val activos by mis.activos.collectAsState()
    val historial by mis.historial.collectAsState()
    val kilos by mis.kilosLavados.collectAsState()
    val oscuro by sesion.modoOscuro.collectAsState()
    val avisos by sesion.notificaciones.collectAsState()

    PantallaBase("Perfil") { pad ->
        Column(
            Modifier.padding(pad).fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Card(Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp)) {
                    Text(usuario.nombre, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                    Text(usuario.codigo)
                    Text(usuario.correo, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Contador("Activos", activos.size.toString(), Modifier.weight(1f))
                Contador("Pedidos", (activos.size + historial.size).toString(), Modifier.weight(1f))
                Contador("Kilos", String.format(Locale.US, "%.1f", kilos), Modifier.weight(1f))
            }

            Card(Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp)) {
                    FilaSwitch("Modo oscuro", oscuro, sesion::guardarModoOscuro)
                    FilaSwitch("Avisos del pedido", avisos, sesion::guardarNotificaciones)
                }
            }

            OutlinedButton(onClick = sesion::cambiarPerfil, Modifier.fillMaxWidth()) { Text("Cambiar de perfil") }
            Button(onClick = sesion::cerrarSesion, Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)) {
                Text("Cerrar sesión")
            }
        }
    }
}

@Composable
private fun Contador(titulo: String, valor: String, modifier: Modifier) {
    Card(modifier, colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)) {
        Column(Modifier.padding(12.dp).fillMaxWidth(), horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally) {
            Text(valor, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            Text(titulo, style = MaterialTheme.typography.bodySmall)
        }
    }
}
