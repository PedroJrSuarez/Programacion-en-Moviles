package com.suarez.lavacampus.ui.administrador

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.suarez.lavacampus.model.Casilleros
import com.suarez.lavacampus.model.EstadoPedido
import com.suarez.lavacampus.model.estadoEnum
import com.suarez.lavacampus.model.proximo
import com.suarez.lavacampus.model.soles
import com.suarez.lavacampus.model.texto
import com.suarez.lavacampus.ui.components.ChipEstado
import com.suarez.lavacampus.ui.components.FilaSwitch
import com.suarez.lavacampus.ui.components.PantallaBase
import com.suarez.lavacampus.ui.components.Seguimiento
import com.suarez.lavacampus.viewmodel.GestionPedidoViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun GestionPedidoScreen(vm: GestionPedidoViewModel, onVolver: () -> Unit) {
    val pcd by vm.pedido.collectAsState()
    val ocupados by vm.casillerosOcupados.collectAsState()
    val elegido by vm.casilleroElegido.collectAsState()
    val mensaje by vm.mensaje.collectAsState()
    var confirmarCancelar by remember { mutableStateOf(false) }

    PantallaBase("Gestionar pedido", onVolver) { pad ->
        val datos = pcd
        if (datos == null) {
            Box(Modifier.padding(pad).fillMaxSize(), contentAlignment = Alignment.Center) { CircularProgressIndicator() }
            return@PantallaBase
        }
        val p = datos.pedido
        val estado = datos.estadoEnum()
        val siguiente = estado.proximo()
        val puedeCancelar = estado == EstadoPedido.RECIBIDO || estado == EstadoPedido.EN_PROCESO

        Column(
            Modifier.padding(pad).fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Card(Modifier.fillMaxWidth()) {
                Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Column(Modifier.weight(1f)) {
                        Text("Pedido #${p.id}", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                        Text(datos.usuario.nombre)
                        Text(datos.usuario.codigo, style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text("Entrega: ${p.fechaEntrega}", style = MaterialTheme.typography.bodySmall)
                    }
                    ChipEstado(estado)
                }
            }

            if (estado != EstadoPedido.CANCELADO) Seguimiento(estado, vertical = true, modifier = Modifier.fillMaxWidth())

            if (estado == EstadoPedido.EN_PROCESO || estado == EstadoPedido.LISTO) {
                Card(Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("Casillero", fontWeight = FontWeight.Bold)
                        if (estado == EstadoPedido.LISTO) {
                            Text("Asignado: ${p.casillero ?: "-"}")
                        } else {
                            FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Casilleros.todos.forEach { c ->
                                    FilterChip(
                                        selected = elegido == c,
                                        enabled = c !in ocupados,
                                        onClick = { vm.elegirCasillero(c) },
                                        label = { Text(c) }
                                    )
                                }
                            }
                            Text("Los casilleros en gris están ocupados", style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                }
            }

            Card(Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp)) {
                    Row(Modifier.fillMaxWidth()) {
                        Text("Total", Modifier.weight(1f), fontWeight = FontWeight.Bold)
                        Text(p.total.soles(), fontWeight = FontWeight.Bold)
                    }
                    if (estado != EstadoPedido.CANCELADO) FilaSwitch("Cobrado", p.pagado, vm::marcarPagado)
                    Text("Recuerda: para pasar a Listo elige un casillero libre; para Entregar el pedido debe estar cobrado.",
                        style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }

            mensaje?.let { Text(it, color = MaterialTheme.colorScheme.error, fontWeight = FontWeight.Bold) }

            if (siguiente != null) {
                Button(onClick = vm::avanzar, Modifier.fillMaxWidth()) { Text("Marcar como ${siguiente.texto()}") }
            }
            if (puedeCancelar) {
                OutlinedButton(onClick = { confirmarCancelar = true }, Modifier.fillMaxWidth()) {
                    Text("Cancelar pedido", color = MaterialTheme.colorScheme.error)
                }
            }
        }

        if (confirmarCancelar) {
            AlertDialog(
                onDismissRequest = { confirmarCancelar = false },
                title = { Text("Cancelar pedido") },
                text = { Text("¿Cancelar el pedido #${p.id}?") },
                confirmButton = { TextButton(onClick = { vm.cancelar(); confirmarCancelar = false }) { Text("Sí, cancelar") } },
                dismissButton = { TextButton(onClick = { confirmarCancelar = false }) { Text("No") } }
            )
        }
    }
}
