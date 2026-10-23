package com.suarez.lavacampus.ui.estudiante

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.suarez.lavacampus.model.cantidadTexto
import com.suarez.lavacampus.model.soles
import com.suarez.lavacampus.model.textoPrecio
import com.suarez.lavacampus.ui.components.BotonLava
import com.suarez.lavacampus.ui.components.IconoDeServicio
import com.suarez.lavacampus.ui.components.PantallaBase
import com.suarez.lavacampus.ui.components.SelectorDeCantidad
import com.suarez.lavacampus.viewmodel.NuevoPedidoViewModel

@Composable
fun NuevoPedidoScreen(vm: NuevoPedidoViewModel, onVolver: () -> Unit, onCreado: () -> Unit) {
    val servicios by vm.servicios.collectAsState()
    val cantidades by vm.cantidades.collectAsState()
    val resumen by vm.resumen.collectAsState()
    val total by vm.total.collectAsState()
    val subtotal by vm.subtotal.collectAsState()
    val descuento by vm.descuento.collectAsState()
    val fecha by vm.fechaEntrega.collectAsState()
    val nota by vm.nota.collectAsState()
    val mensaje by vm.mensaje.collectAsState()
    val recibiendo by vm.recibiendoPedidos.collectAsState()
    val puedeConfirmar by vm.puedeConfirmar.collectAsState()

    PantallaBase("Nuevo pedido", onVolver) { pad ->
        LazyColumn(
            Modifier.padding(pad).fillMaxSize().imePadding(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (!recibiendo) {
                item {
                    Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)) {
                        Text("La lavandería no está recibiendo pedidos", Modifier.padding(16.dp),
                            color = MaterialTheme.colorScheme.onErrorContainer, fontWeight = FontWeight.Bold)
                    }
                }
            }

            items(servicios, key = { it.id }) { s ->
                val kg = s.unidad == "KG"
                val valor = cantidades[s.id] ?: 0.0
                Card(Modifier.fillMaxWidth()) {
                    Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        IconoDeServicio(s.icono, Modifier.size(48.dp))
                        Column(Modifier.weight(1f)) {
                            Text(s.nombre, fontWeight = FontWeight.Bold)
                            Text(s.textoPrecio(), style = MaterialTheme.typography.bodySmall)
                            Text("Entrega en ${s.horasEntrega} h", style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        SelectorDeCantidad(
                            valor = valor,
                            paso = if (kg) 0.5 else 1.0,
                            minimo = 0.0,
                            maximo = if (kg) 10.0 else 30.0,
                            onDelta = { d -> vm.cambiarCantidad(s.id, d) }
                        )
                    }
                }
            }

            item {
                OutlinedTextField(
                    value = nota, onValueChange = vm::cambiarNota,
                    label = { Text("Nota (opcional)") }, modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                Card(Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)) {
                    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text("Resumen", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        if (resumen.isEmpty()) Text("Elige al menos un servicio")
                        resumen.forEach { l ->
                            Row(Modifier.fillMaxWidth()) {
                                Text("${l.servicio.nombre} · ${l.cantidad.cantidadTexto(l.servicio.unidad)}", Modifier.weight(1f))
                                Text(l.subtotal.soles())
                            }
                        }
                        HorizontalDivider()
                        if (descuento > 0.0) {
                            Row(Modifier.fillMaxWidth()) {
                                Text("Subtotal", Modifier.weight(1f))
                                Text(subtotal.soles())
                            }
                            Row(Modifier.fillMaxWidth()) {
                                Text(
                                    "Descuento por volumen (${(NuevoPedidoViewModel.PORC_DESCUENTO * 100).toInt()} %)",
                                    Modifier.weight(1f), color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold
                                )
                                Text("-" + descuento.soles(), color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                            }
                        }
                        Row(Modifier.fillMaxWidth()) {
                            Text("Total", Modifier.weight(1f), fontWeight = FontWeight.Bold)
                            Text(total.soles(), fontWeight = FontWeight.Bold)
                        }
                        fecha?.let { Text("Entrega estimada: $it") }
                    }
                }
            }

            item {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    mensaje?.let { Text(it, color = MaterialTheme.colorScheme.error, fontWeight = FontWeight.Bold) }
                    BotonLava("Confirmar pedido", { vm.confirmar(onCreado) }, Modifier.fillMaxWidth(), puedeConfirmar)
                }
            }
        }
    }
}
