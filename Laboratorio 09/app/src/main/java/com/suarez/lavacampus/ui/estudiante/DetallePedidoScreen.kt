package com.suarez.lavacampus.ui.estudiante

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.suarez.lavacampus.model.EstadoPedido
import com.suarez.lavacampus.model.cantidadTexto
import com.suarez.lavacampus.model.estadoEnum
import com.suarez.lavacampus.model.soles
import com.suarez.lavacampus.ui.components.ChipEstado
import com.suarez.lavacampus.ui.components.PantallaBase
import com.suarez.lavacampus.ui.components.Seguimiento
import com.suarez.lavacampus.viewmodel.DetallePedidoViewModel

@Composable
fun DetallePedidoScreen(vm: DetallePedidoViewModel, onVolver: () -> Unit) {
    val pedidoConDetalle by vm.pedido.collectAsState()
    val puedeCancelar by vm.puedeCancelar.collectAsState()
    var confirmar by remember { mutableStateOf(false) }

    PantallaBase("Detalle del pedido", onVolver) { pad ->
        val pcd = pedidoConDetalle
        if (pcd == null) {
            Box(Modifier.padding(pad).fillMaxSize(), contentAlignment = Alignment.Center) { CircularProgressIndicator() }
            return@PantallaBase
        }
        val p = pcd.pedido
        val estado = pcd.estadoEnum()

        LazyColumn(
            Modifier.padding(pad).fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Column(Modifier.weight(1f)) {
                        Text("Pedido #${p.id}", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                        Text("Registrado: ${p.fechaRegistro}")
                        Text("Entrega estimada: ${p.fechaEntrega}")
                    }
                    ChipEstado(estado)
                }
            }

            if (estado != EstadoPedido.CANCELADO) {
                item { Seguimiento(estado, vertical = true, modifier = Modifier.fillMaxWidth()) }
            }

            item {
                Card(Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("Servicios", fontWeight = FontWeight.Bold)
                        pcd.detalles.forEach { d ->
                            Row(Modifier.fillMaxWidth()) {
                                Column(Modifier.weight(1f)) {
                                    Text(d.servicio.nombre)
                                    Text("${d.detalle.cantidad.cantidadTexto(d.servicio.unidad)} × ${d.detalle.precioUnitario.soles()}",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant)
                                }
                                Text(d.detalle.subtotal.soles())
                            }
                        }
                        HorizontalDivider()
                        if (p.descuento > 0.0) {
                            Row(Modifier.fillMaxWidth()) {
                                Text("Subtotal", Modifier.weight(1f))
                                Text((p.total + p.descuento).soles())
                            }
                            Row(Modifier.fillMaxWidth()) {
                                Text("Descuento por volumen", Modifier.weight(1f),
                                    color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                                Text("-" + p.descuento.soles(), color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                            }
                        }
                        Row(Modifier.fillMaxWidth()) {
                            Text("Total", Modifier.weight(1f), fontWeight = FontWeight.Bold)
                            Text(p.total.soles(), fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            item {
                Card(Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(
                    containerColor = if (p.pagado) MaterialTheme.colorScheme.primaryContainer
                    else MaterialTheme.colorScheme.surfaceVariant)) {
                    Text(
                        if (p.pagado) "Pagado" else "Pago pendiente, se paga al recoger",
                        Modifier.padding(16.dp), fontWeight = FontWeight.Bold
                    )
                }
            }

            item {
                Card(Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(
                    containerColor = if (estado == EstadoPedido.LISTO) MaterialTheme.colorScheme.primaryContainer
                    else MaterialTheme.colorScheme.surfaceVariant)) {
                    Text(
                        if (estado == EstadoPedido.LISTO && p.casillero != null) "Casillero asignado: ${p.casillero}"
                        else "Se asignará al estar listo",
                        Modifier.padding(16.dp), fontWeight = FontWeight.Bold
                    )
                }
            }

            if (estado != EstadoPedido.CANCELADO && estado != EstadoPedido.ENTREGADO) {
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Button(
                            onClick = { confirmar = true }, enabled = puedeCancelar, modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                        ) { Text("Cancelar pedido") }
                        if (!puedeCancelar) {
                            Text("Solo se cancela antes de empezar el lavado",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                }
            }
        }

        if (confirmar) {
            AlertDialog(
                onDismissRequest = { confirmar = false },
                title = { Text("Cancelar pedido") },
                text = { Text("¿Seguro que quieres cancelar el pedido #${p.id}?") },
                confirmButton = { TextButton(onClick = { vm.cancelar(); confirmar = false }) { Text("Sí, cancelar") } },
                dismissButton = { TextButton(onClick = { confirmar = false }) { Text("No") } }
            )
        }
    }
}
