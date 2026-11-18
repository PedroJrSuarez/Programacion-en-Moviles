package com.tecsup.campusgps.ui

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.tecsup.campusgps.model.UbicacionUi
import com.tecsup.campusgps.util.formatearDistancia
import com.tecsup.campusgps.viewmodel.MapaViewModel
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaMapa(vm: MapaViewModel) {
    val estado by vm.estado.collectAsState()
    val lugares by vm.lugaresVisibles.collectAsState()
    val seleccionado by vm.seleccionado.collectAsState()
    val masCercano by vm.masCercano.collectAsState()
    val categoria by vm.categoria.collectAsState()
    val radio by vm.radio.collectAsState()
    val idsEnRadio by vm.idsEnRadio.collectAsState()

    val contexto = LocalContext.current
    val fine = Manifest.permission.ACCESS_FINE_LOCATION
    val coarse = Manifest.permission.ACCESS_COARSE_LOCATION

    val lanzador = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { resultado ->
        if (resultado.values.any { it }) vm.buscar() else vm.permisoDenegado()
    }

    fun alPulsar() {
        android.util.Log.d("CampusGPS", "alPulsar() fue presionado")
        val concedido = listOf(fine, coarse).any {
            ContextCompat.checkSelfPermission(contexto, it) == PackageManager.PERMISSION_GRANTED
        }
        android.util.Log.d("CampusGPS", "Permisos concedidos: $concedido")
        if (concedido) vm.buscar() else lanzador.launch(arrayOf(fine, coarse))
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("CampusGPS") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier.padding(padding).fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val botonPrincipal: @Composable () -> Unit = {
                Button(
                    onClick = { alPulsar() },
                    enabled = estado !is UbicacionUi.Buscando
                ) { Text("Obtener mi ubicación") }
            }

            val actual = estado
            if (actual is UbicacionUi.Error) {
                Spacer(Modifier.height(12.dp))
                botonPrincipal()
                MensajeError(error = actual, onReintentar = { alPulsar() })
            } else {
                MapaUbicacion(
                    estado = actual,
                    lugares = lugares,
                    seleccionado = seleccionado,
                    masCercanoId = masCercano?.id,
                    radioMetros = radio,
                    modifier = Modifier.weight(1f).fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))
                FiltrosLugares(
                    categorias = vm.categorias,
                    categoriaSeleccionada = categoria,
                    onCategoria = { vm.seleccionarCategoria(it) },
                    radios = vm.radiosDisponibles,
                    radioSeleccionado = radio,
                    onRadio = { vm.seleccionarRadio(it) },
                    radioHabilitado = actual is UbicacionUi.Lista
                )
                botonPrincipal()

                when (actual) {
                    is UbicacionUi.Lista -> {
                        Text(String.format(Locale.US, "Latitud: %.4f", actual.posicion.latitud),
                            style = MaterialTheme.typography.bodySmall)
                        Text(String.format(Locale.US, "Longitud: %.4f", actual.posicion.longitud),
                            style = MaterialTheme.typography.bodySmall)
                    }
                    UbicacionUi.Inicial -> Text("Pulsa el botón para empezar",
                        style = MaterialTheme.typography.bodySmall)
                    else -> Unit
                }

                // Paso 7
                OutlinedButton(
                    onClick = { masCercano?.let { vm.seleccionar(it.id) } },
                    enabled = masCercano != null
                ) { Text("Ir al más cercano") }

                val cercano = masCercano
                val metros = lugares.firstOrNull { it.lugar.id == cercano?.id }?.metros
                if (cercano != null && metros != null) {
                    Text(
                        buildAnnotatedString {
                            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("Más cercano: ") }
                            append("${cercano.nombre} (${formatearDistancia(metros)})")
                        },
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                // Paso 6: lista con altura fija
                ListaLugares(
                    lugares = lugares,
                    seleccionado = seleccionado,
                    onClick = { vm.seleccionar(it) },
                    masCercanoId = masCercano?.id,
                    enRadio = idsEnRadio,
                    modifier = Modifier.fillMaxWidth().height(220.dp)
                )
            }
        }
    }
}
