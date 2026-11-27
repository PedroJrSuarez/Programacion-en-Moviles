package com.tecsup.avisoscampus.ui

import android.Manifest
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.tecsup.avisoscampus.model.PermisoUi
import com.tecsup.avisoscampus.notificacion.Notificador
import com.tecsup.avisoscampus.receiver.ModoAvionReceiver
import com.tecsup.avisoscampus.ui.theme.GrisSin
import com.tecsup.avisoscampus.ui.theme.RojoNo
import com.tecsup.avisoscampus.ui.theme.VerdeOk
import com.tecsup.avisoscampus.viewmodel.AvisosViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaAvisos(vm: AvisosViewModel) {
    val contexto = LocalContext.current

    val permiso by vm.permiso.collectAsState()
    val avisos by vm.avisos.collectAsState()
    val faltan by vm.faltan.collectAsState()
    val escuchando by vm.escuchandoAvion.collectAsState()
    val mensaje by vm.mensaje.collectAsState()

    // ---- Paso 2: permiso ----
    val lanzador = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { concedido ->
        vm.permisoRespondido(concedido)
    }

    LaunchedEffect(Unit) {
        if (Notificador.tienePermiso(contexto)) vm.permisoRespondido(true)
    }

    // ---- Pasos 3, 5, 6, 8: el ViewModel pide, la pantalla (con Context) muestra ----
    LaunchedEffect(Unit) {
        vm.eventos.collect { aviso ->
            val mostrado = Notificador.mostrar(contexto, aviso)
            if (!mostrado) vm.noSePudoMostrar()
        }
    }

    // ---- Paso 6: receptor del modo avión ----
    DisposableEffect(escuchando) {
        if (escuchando) {
            val receptor = ModoAvionReceiver { activo -> vm.alCambiarModoAvion(activo) }
            ContextCompat.registerReceiver(
                contexto,
                receptor,
                IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED),
                ContextCompat.RECEIVER_NOT_EXPORTED
            )
            onDispose { contexto.unregisterReceiver(receptor) }
        } else {
            onDispose { }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("AvisosCampus") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Tarjeta: Permiso de notificaciones
            Tarjeta("Permiso de notificaciones") {
                val (texto, color) = when (permiso) {
                    PermisoUi.SinPedir -> "Sin pedir" to GrisSin
                    PermisoUi.Concedido -> "Permitido" to VerdeOk
                    PermisoUi.Denegado -> "Denegado" to RojoNo
                }
                Surface(color = color, shape = RoundedCornerShape(50)) {
                    Text(
                        texto,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelLarge
                    )
                }
                Button(onClick = {
                    if (Build.VERSION.SDK_INT >= 33) {
                        lanzador.launch(Manifest.permission.POST_NOTIFICATIONS)
                    } else {
                        vm.permisoRespondido(true)
                    }
                }) { Text("Permitir notificaciones") }
            }

            // Mensaje de error + Abrir ajustes (Paso 8)
            if (mensaje != null || permiso == PermisoUi.Denegado) {
                Surface(
                    color = MaterialTheme.colorScheme.errorContainer,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        mensaje?.let {
                            Text(it, color = MaterialTheme.colorScheme.onErrorContainer)
                        }
                        OutlinedButton(onClick = {
                            val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
                                putExtra(Settings.EXTRA_APP_PACKAGE, contexto.packageName)
                            }
                            contexto.startActivity(intent)
                        }) { Text("Abrir ajustes") }
                    }
                }
            }

            // Tarjeta: Avisos
            Tarjeta("Avisos") {
                Button(onClick = { vm.avisarAhora() }) { Text("Mostrar aviso") }
            }

            // Tarjeta: Recordatorio
            Tarjeta("Recordatorio") {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(
                        onClick = { vm.recordarEn(10) },
                        enabled = faltan == null
                    ) { Text("Recordar en 10 s") }
                    OutlinedButton(
                        onClick = { vm.cancelarRecordatorio() },
                        enabled = faltan != null
                    ) { Text("Cancelar") }
                }
                faltan?.let { Text("Faltan $it s", fontWeight = FontWeight.SemiBold) }
            }

            // Tarjeta: Receptor de anuncios
            Tarjeta("Receptor de anuncios") {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Escuchar el modo avión")
                    Switch(checked = escuchando, onCheckedChange = { vm.cambiarEscucha(it) })
                }
                Text(
                    "Activa o desactiva el modo avión del celular",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Tarjeta: Historial
            HistorialAvisos(avisos)
        }
    }
}

@Composable
private fun Tarjeta(titulo: String, contenido: @Composable () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                titulo,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            contenido()
        }
    }
}
