package com.tecsup.centrotareas.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.tecsup.centrotareas.model.CargaUi
import com.tecsup.centrotareas.model.PrimosUi
import com.tecsup.centrotareas.viewmodel.TareasViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaTareas(vm: TareasViewModel) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("CentroTareas") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Prueba de vida: si deja de sumar, la pantalla está congelada
            var toques by rememberSaveable { mutableIntStateOf(0) }
            Button(
                onClick = { toques++ },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) { Text("Tócame: $toques") }

            TarjetaProgreso(vm)
            TarjetaPrimos(vm)
            TarjetaCargas(vm)
            TarjetaComparacion(vm)
        }
    }
}

@Composable
private fun Tarjeta(titulo: String, contenido: @Composable ColumnScope.() -> Unit) {
    Card(Modifier.fillMaxWidth()) {
        Column(
            Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                titulo,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
            contenido()
        }
    }
}

@Composable
private fun Recuadro(fondo: Color, contenido: @Composable ColumnScope.() -> Unit) {
    Column(
        Modifier
            .fillMaxWidth()
            .background(fondo, RoundedCornerShape(8.dp))
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        content = contenido
    )
}

private val Verde = Color(0xFFE3F4E1)

@Composable
private fun TarjetaProgreso(vm: TareasViewModel) {
    val progreso by vm.progreso.collectAsState()
    val mensaje by vm.mensaje.collectAsState()
    val corriendo by vm.corriendo.collectAsState()

    Tarjeta("Tarea con progreso") {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { vm.tareaLarga() }, enabled = !corriendo) { Text("Iniciar") }
            OutlinedButton(onClick = { vm.cancelar() }, enabled = corriendo) { Text("Cancelar") }
        }
        LinearProgressIndicator(progress = { progreso }, modifier = Modifier.fillMaxWidth())
        Text("${(progreso * 100).toInt()} %")
        Text(mensaje)
    }
}

@Composable
private fun TarjetaPrimos(vm: TareasViewModel) {
    val estado by vm.primos.collectAsState()
    var texto by rememberSaveable { mutableStateOf("500000") }
    val calculando = estado is PrimosUi.Calculando

    Tarjeta("Contar números primos") {
        OutlinedTextField(
            value = texto,
            onValueChange = { nuevo -> texto = nuevo.filter { it.isDigit() }.take(9) },
            label = { Text("Hasta el número") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(
                onClick = {
                    val n = texto.toIntOrNull()
                    if (n != null && n >= 2) vm.contar(n)
                },
                enabled = !calculando
            ) { Text("Contar") }
            OutlinedButton(onClick = { vm.cancelarPrimos() }, enabled = calculando) {
                Text("Cancelar")
            }
        }
        when (val e = estado) {
            PrimosUi.Inicial -> {}
            PrimosUi.Calculando -> CircularProgressIndicator()
            is PrimosUi.Listo -> Recuadro(Verde) {
                Text("Primos hasta ${texto}: ${e.cantidad}", fontWeight = FontWeight.Bold)
                Text("Tiempo: ${e.milisegundos} ms")
            }
            PrimosUi.Cancelado -> Text("Cancelado")
        }
    }
}

@Composable
private fun TarjetaCargas(vm: TareasViewModel) {
    val estado by vm.carga.collectAsState()
    val simularError by vm.simularError.collectAsState()
    val intento by vm.intento.collectAsState()
    val cargando = estado is CargaUi.Cargando

    Tarjeta("Dos cargas") {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Switch(checked = simularError, onCheckedChange = { vm.cambiarSimularError(it) })
            Spacer(Modifier.width(8.dp))
            Text("Simular error")
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedButton(onClick = { vm.cargarUnaPorUna() }, enabled = !cargando) {
                Text("Una por una")
            }
            Button(onClick = { vm.cargarALaVez() }, enabled = !cargando) { Text("A la vez") }
        }
        when (val e = estado) {
            CargaUi.Inicial -> {}
            CargaUi.Cargando -> {
                CircularProgressIndicator()
                if (intento > 1) Text("Reintentando (intento $intento de 3)...")
            }
            is CargaUi.Exito -> Recuadro(Verde) {
                Text("Modo: ${e.modo}", fontWeight = FontWeight.Bold)
                Text("Perfil: ${e.perfil}")
                Text("Pedidos: ${e.pedidos}")
                Text("Tiempo: ${e.milisegundos} ms")
            }
            is CargaUi.Error -> {
                Recuadro(MaterialTheme.colorScheme.errorContainer) {
                    Text(
                        e.mensaje,
                        color = MaterialTheme.colorScheme.onErrorContainer,
                        textAlign = TextAlign.Center
                    )
                }
                OutlinedButton(onClick = { vm.cargarALaVez() }) { Text("Reintentar") }
            }
        }
    }
}

@Composable
private fun TarjetaComparacion(vm: TareasViewModel) {
    val c by vm.comparacion.collectAsState()
    if (c.unaPorUna.isEmpty() && c.aLaVez.isEmpty()) return

    Tarjeta("Comparación de tiempos") {
        FilaTiempos("Una por una", c.unaPorUna)
        FilaTiempos("A la vez", c.aLaVez)
        if (c.unaPorUna.isNotEmpty() && c.aLaVez.isNotEmpty()) {
            val dif = c.unaPorUna.average() - c.aLaVez.average()
            val pct = (dif / c.unaPorUna.average() * 100).toInt()
            Recuadro(Verde) {
                Text(
                    "A la vez es ${dif.toLong()} ms más rápido ($pct %)",
                    fontWeight = FontWeight.Bold
                )
            }
        }
        OutlinedButton(onClick = { vm.limpiarComparacion() }) { Text("Limpiar") }
    }
}

@Composable
private fun FilaTiempos(modo: String, tiempos: List<Long>) {
    if (tiempos.isEmpty()) {
        Text("$modo: sin datos")
    } else {
        Text("$modo: ${tiempos.size}x · último ${tiempos.last()} ms · promedio ${tiempos.average().toLong()} ms")
    }
}
