package com.tecsup.avisoscampus.ui

import android.Manifest
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.tecsup.avisoscampus.R
import com.tecsup.avisoscampus.model.PermisoUi
import com.tecsup.avisoscampus.notificacion.Notificador
import com.tecsup.avisoscampus.receiver.CargadorReceiver
import com.tecsup.avisoscampus.receiver.ModoAvionReceiver
import com.tecsup.avisoscampus.ui.theme.CyanTertiary
import com.tecsup.avisoscampus.ui.theme.EmeraldOk
import com.tecsup.avisoscampus.ui.theme.GrisSin
import com.tecsup.avisoscampus.ui.theme.IndigoPrimary
import com.tecsup.avisoscampus.ui.theme.PurpleSecondary
import com.tecsup.avisoscampus.ui.theme.RoseNo
import com.tecsup.avisoscampus.viewmodel.AvisosViewModel

@Composable
fun PantallaAvisos(vm: AvisosViewModel) {
    val contexto = LocalContext.current

    val permiso by vm.permiso.collectAsState()
    val avisos by vm.avisos.collectAsState()
    val faltan by vm.faltan.collectAsState()
    val escuchando by vm.escuchandoAvion.collectAsState()
    val mensaje by vm.mensaje.collectAsState()
    val escuchandoCargador by vm.escuchandoCargador.collectAsState()

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

    // ---- Fase 2: receptor del cargador ----
    DisposableEffect(escuchandoCargador) {
        if (escuchandoCargador) {
            val receptor = CargadorReceiver { conectado -> vm.alCambiarCargador(conectado) }
            val filtro = IntentFilter().apply {
                addAction(Intent.ACTION_POWER_CONNECTED)
                addAction(Intent.ACTION_POWER_DISCONNECTED)
            }
            ContextCompat.registerReceiver(
                contexto, receptor, filtro, ContextCompat.RECEIVER_NOT_EXPORTED
            )
            onDispose { contexto.unregisterReceiver(receptor) }
        } else {
            onDispose { }
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize()) {
            // Fondo Canvas con formas orgánicas y degradados fluidos
            FondoOrganicoCanvas()

            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 18.dp, vertical = 14.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Header Expresivo Bento
                HeaderExpresivo()

                // Banner animado de error o permiso denegado
                AnimatedVisibility(
                    visible = mensaje != null || permiso == PermisoUi.Denegado,
                    enter = expandVertically() + fadeIn(),
                    exit = shrinkVertically() + fadeOut()
                ) {
                    TarjetaMensajeError(
                        mensaje = mensaje,
                        alAbrirAjustes = {
                            val intent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
                                    putExtra(Settings.EXTRA_APP_PACKAGE, contexto.packageName)
                                }
                            } else {
                                Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                                    data = android.net.Uri.fromParts("package", contexto.packageName, null)
                                }
                            }
                            contexto.startActivity(intent)
                        }
                    )
                }

                // Bento Grid Fila 1: Permiso + Generador Rápido
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    // Bento Box 1: Estado Permiso (50%)
                    BentoBoxPermiso(
                        modifier = Modifier.weight(1f),
                        permiso = permiso,
                        alSolicitarPermiso = {
                            if (Build.VERSION.SDK_INT >= 33) {
                                lanzador.launch(Manifest.permission.POST_NOTIFICATIONS)
                            } else {
                                vm.permisoRespondido(true)
                            }
                        }
                    )

                    // Bento Box 2: Lanzar Aviso Directo (50%)
                    BentoBoxAvisoExpress(
                        modifier = Modifier.weight(1f),
                        alAvisarAhora = { vm.avisarAhora() }
                    )
                }

                // Bento Box 3: Recordatorio con Canvas Timer Gauge
                BentoBoxRecordatorio(
                    faltan = faltan,
                    alRecordarEn = { vm.recordarEn(10) },
                    alCancelar = { vm.cancelarRecordatorio() }
                )

                // Bento Box 4: Sensores y Receptores del Sistema
                BentoBoxSensores(
                    escuchandoAvion = escuchando,
                    alCambiarAvion = { vm.cambiarEscucha(it) },
                    escuchandoCargador = escuchandoCargador,
                    alCambiarCargador = { vm.cambiarEscuchaCargador(it) }
                )

                // Bento Box 5: Historial Feed
                HistorialAvisos(avisos)

                Spacer(modifier = Modifier.height(60.dp))
            }
        }
    }
}

/** Header Bento con degradado orgánico y badge */
@Composable
private fun HeaderExpresivo() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(32.dp))
            .background(
                Brush.horizontalGradient(
                    listOf(IndigoPrimary, PurpleSecondary, CyanTertiary)
                )
            )
            .padding(24.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "AvisosCampus",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.ExtraBold
                    ),
                    color = Color.White
                )
                Text(
                    text = "Centro de Notificaciones & Sensores",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(alpha = 0.85f)
                )
            }

            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_notificacion),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

/** Bento Box 1: Estado del Permiso */
@Composable
private fun BentoBoxPermiso(
    modifier: Modifier = Modifier,
    permiso: PermisoUi,
    alSolicitarPermiso: () -> Unit
) {
    val (texto, color) = when (permiso) {
        PermisoUi.SinPedir -> "Sin pedir" to GrisSin
        PermisoUi.Concedido -> "Permitido" to EmeraldOk
        PermisoUi.Denegado -> "Denegado" to RoseNo
    }

    Card(
        modifier = modifier.height(180.dp),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "PERMISO",
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(6.dp))
                Surface(
                    shape = CircleShape,
                    color = color.copy(alpha = 0.15f)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(color)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = texto,
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            color = color
                        )
                    }
                }
            }

            Button(
                onClick = alSolicitarPermiso,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text(
                    text = if (permiso == PermisoUi.Concedido) "Concedido" else "Permitir",
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
                )
            }
        }
    }
}

/** Bento Box 2: Notificación Express */
@Composable
private fun BentoBoxAvisoExpress(
    modifier: Modifier = Modifier,
    alAvisarAhora: () -> Unit
) {
    val transition = rememberInfiniteTransition(label = "pulse")
    val alphaAnim by transition.animateFloat(
        initialValue = 0.4f,
        targetValue = 0.9f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "alpha"
    )

    Card(
        modifier = modifier.height(180.dp),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            IndigoPrimary.copy(alpha = 0.08f),
                            PurpleSecondary.copy(alpha = 0.12f)
                        )
                    )
                )
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "AVISOS",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = PurpleSecondary
                    )
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(IndigoPrimary.copy(alpha = alphaAnim))
                    )
                }

                Column {
                    Text(
                        text = "Lanzar Notificación",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Genera un aviso inmediato",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Button(
                    onClick = alAvisarAhora,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PurpleSecondary)
                ) {
                    Text(
                        text = "Mostrar aviso",
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
                    )
                }
            }
        }
    }
}

/** Bento Box 3: Recordatorio Inteligente con Canvas Timer Ring */
@Composable
private fun BentoBoxRecordatorio(
    faltan: Int?,
    alRecordarEn: () -> Unit,
    alCancelar: () -> Unit
) {
    val estaActivo = faltan != null
    val animProgress by animateFloatAsState(
        targetValue = if (faltan != null) (faltan.toFloat() / 10f) else 1f,
        animationSpec = tween(500),
        label = "progress"
    )

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "RECORDATORIO",
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                    color = PurpleSecondary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Cuenta Regresiva",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = if (estaActivo) "Programado en 10 s..." else "Notificación programada en 10 s",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(12.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Button(
                        onClick = alRecordarEn,
                        enabled = !estaActivo,
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = IndigoPrimary)
                    ) {
                        Text("Recordar 10 s", fontWeight = FontWeight.Bold)
                    }

                    OutlinedButton(
                        onClick = alCancelar,
                        enabled = estaActivo,
                        shape = RoundedCornerShape(14.dp),
                        border = androidx.compose.foundation.BorderStroke(
                            width = 1.dp,
                            brush = Brush.linearGradient(listOf(RoseNo, PurpleSecondary))
                        )
                    ) {
                        Text("Cancelar")
                    }
                }
            }

            // Gauge Canvas de cuenta regresiva
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .padding(start = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    drawCircle(
                        color = Color.LightGray.copy(alpha = 0.2f),
                        style = Stroke(width = 8.dp.toPx())
                    )
                    drawArc(
                        brush = Brush.sweepGradient(listOf(IndigoPrimary, PurpleSecondary, CyanTertiary)),
                        startAngle = -90f,
                        sweepAngle = animProgress * 360f,
                        useCenter = false,
                        style = Stroke(width = 8.dp.toPx(), cap = StrokeCap.Round)
                    )
                }

                if (estaActivo && faltan != null) {
                    Text(
                        text = "${faltan}s",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Black),
                        color = IndigoPrimary
                    )
                } else {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_notificacion),
                        contentDescription = null,
                        tint = PurpleSecondary.copy(alpha = 0.7f),
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}

/** Bento Box 4: Receptores de Anuncios del Sistema */
@Composable
private fun BentoBoxSensores(
    escuchandoAvion: Boolean,
    alCambiarAvion: (Boolean) -> Unit,
    escuchandoCargador: Boolean,
    alCambiarCargador: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Text(
                text = "SENSORES Y RECEPTORES",
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                color = CyanTertiary
            )

            // Item 1: Modo Avión
            ItemSensorPill(
                titulo = "Escuchar el modo avión",
                checked = escuchandoAvion,
                colorAccent = CyanTertiary,
                onCheckedChange = alCambiarAvion
            )

            // Item 2: Cargador
            ItemSensorPill(
                titulo = "Escuchar el cargador",
                checked = escuchandoCargador,
                colorAccent = EmeraldOk,
                onCheckedChange = alCambiarCargador
            )
        }
    }
}

@Composable
private fun ItemSensorPill(
    titulo: String,
    subtitulo: String? = null,
    checked: Boolean,
    colorAccent: Color,
    onCheckedChange: (Boolean) -> Unit
) {
    val bgColor by animateColorAsState(
        targetValue = if (checked) colorAccent.copy(alpha = 0.12f) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
        label = "bg"
    )

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        color = bgColor
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = titulo,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                if (!subtitulo.isNullOrBlank()) {
                    Text(
                        text = subtitulo,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = colorAccent
                )
            )
        }
    }
}

/** Tarjeta de Mensaje de Error / Ajustes */
@Composable
private fun TarjetaMensajeError(
    mensaje: String?,
    alAbrirAjustes: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        color = MaterialTheme.colorScheme.errorContainer
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            mensaje?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = MaterialTheme.colorScheme.onErrorContainer
                )
            }
            OutlinedButton(
                onClick = alAbrirAjustes,
                shape = RoundedCornerShape(14.dp)
            ) {
                Text("Abrir ajustes del sistema", color = MaterialTheme.colorScheme.onErrorContainer)
            }
        }
    }
}

/** Canvas Orgánico con esferas ambient en el fondo */
@Composable
private fun FondoOrganicoCanvas() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height

        // Orb superior izquierda (Indigo)
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(IndigoPrimary.copy(alpha = 0.15f), Color.Transparent),
                center = Offset(width * 0.2f, height * 0.1f),
                radius = width * 0.6f
            ),
            center = Offset(width * 0.2f, height * 0.1f),
            radius = width * 0.6f
        )

        // Orb central derecha (Purple)
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(PurpleSecondary.copy(alpha = 0.12f), Color.Transparent),
                center = Offset(width * 0.8f, height * 0.45f),
                radius = width * 0.7f
            ),
            center = Offset(width * 0.8f, height * 0.45f),
            radius = width * 0.7f
        )

        // Orb inferior (Cyan)
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(CyanTertiary.copy(alpha = 0.10f), Color.Transparent),
                center = Offset(width * 0.3f, height * 0.85f),
                radius = width * 0.65f
            ),
            center = Offset(width * 0.3f, height * 0.85f),
            radius = width * 0.65f
        )
    }
}
