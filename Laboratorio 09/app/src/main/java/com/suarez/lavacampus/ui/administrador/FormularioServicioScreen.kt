package com.suarez.lavacampus.ui.administrador

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.suarez.lavacampus.data.local.entity.Servicio
import com.suarez.lavacampus.ui.components.BotonLava
import com.suarez.lavacampus.ui.components.FilaSwitch
import com.suarez.lavacampus.ui.components.IconoDeServicio
import com.suarez.lavacampus.ui.components.PantallaBase
import com.suarez.lavacampus.viewmodel.ServiciosViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FormularioServicioScreen(vm: ServiciosViewModel, servicioId: Int, onVolver: () -> Unit) {
    val lista by vm.servicios.collectAsState()
    val mensajeVm by vm.mensaje.collectAsState()
    val existente = lista.firstOrNull { it.id == servicioId }

    var nombre by remember(existente?.id) { mutableStateOf(existente?.nombre ?: "") }
    var unidad by remember(existente?.id) { mutableStateOf(existente?.unidad ?: "KG") }
    var precio by remember(existente?.id) { mutableStateOf(existente?.precio?.toString() ?: "") }
    var horas by remember(existente?.id) { mutableIntStateOf(existente?.horasEntrega ?: 24) }
    var descripcion by remember(existente?.id) { mutableStateOf(existente?.descripcion ?: "") }
    var icono by remember(existente?.id) { mutableStateOf(existente?.icono ?: "wash") }
    var activo by remember(existente?.id) { mutableStateOf(existente?.activo ?: true) }
    var error by remember { mutableStateOf<String?>(null) }

    DisposableEffect(Unit) { onDispose { vm.limpiarMensaje() } }

    PantallaBase(if (servicioId == 0) "Nuevo servicio" else "Editar servicio", onVolver) { pad ->
        Column(
            Modifier.padding(pad).fillMaxSize().verticalScroll(rememberScrollState()).imePadding().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(nombre, { nombre = it }, label = { Text("Nombre *") }, singleLine = true, modifier = Modifier.fillMaxWidth())

            Text("Se cobra por", fontWeight = FontWeight.Bold)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf("KG" to "Kilo", "PRENDA" to "Prenda", "UNIDAD" to "Unidad").forEach { (v, t) ->
                    FilterChip(selected = unidad == v, onClick = { unidad = v }, label = { Text(t) })
                }
            }

            OutlinedTextField(
                precio, { precio = it }, label = { Text("Precio (S/) *") }, singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal), modifier = Modifier.fillMaxWidth()
            )

            Text("Tiempo de entrega", fontWeight = FontWeight.Bold)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf(24, 48, 72).forEach { h ->
                    FilterChip(selected = horas == h, onClick = { horas = h }, label = { Text("$h h") })
                }
            }

            OutlinedTextField(descripcion, { descripcion = it }, label = { Text("Descripción") }, modifier = Modifier.fillMaxWidth())

            Text("Ícono", fontWeight = FontWeight.Bold)
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
                listOf("wash", "iron", "shirt").forEach { i ->
                    FilterChip(
                        selected = icono == i, onClick = { icono = i },
                        label = { IconoDeServicio(i, Modifier.size(32.dp)) }
                    )
                }
            }

            FilaSwitch("Servicio activo", activo) { activo = it }

            (error ?: mensajeVm)?.let { Text(it, color = MaterialTheme.colorScheme.error, fontWeight = FontWeight.Bold) }

            BotonLava("Guardar", {
                val p = precio.replace(',', '.').toDoubleOrNull()
                when {
                    nombre.isBlank() -> error = "El nombre es obligatorio"
                    p == null || p <= 0.0 -> error = "El precio debe ser mayor que 0"
                    else -> {
                        error = null
                        vm.guardar(
                            Servicio(
                                id = existente?.id ?: 0, nombre = nombre.trim(), descripcion = descripcion.trim(),
                                unidad = unidad, precio = p, horasEntrega = horas, activo = activo, icono = icono
                            ),
                            onGuardado = onVolver
                        )
                    }
                }
            }, Modifier.fillMaxWidth())

            if (existente != null) {
                OutlinedButton(onClick = { vm.eliminar(existente, onEliminado = onVolver) }, Modifier.fillMaxWidth()) {
                    Text("Eliminar servicio", color = MaterialTheme.colorScheme.error)
                }
            }
        }
    }
}
