package com.suarez.lavacampus.ui.estudiante

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.suarez.lavacampus.model.EstadoPedido
import com.suarez.lavacampus.model.estadoEnum
import com.suarez.lavacampus.ui.components.PantallaBase
import com.suarez.lavacampus.ui.components.TarjetaPedido
import com.suarez.lavacampus.viewmodel.MisPedidosViewModel

@Composable
fun MisPedidosScreen(vm: MisPedidosViewModel, onDetalle: (Int) -> Unit, onNuevoPedido: () -> Unit) {
    val activos by vm.activos.collectAsState()
    val historial by vm.historial.collectAsState()
    var tab by remember { mutableIntStateOf(0) }
    val lista = if (tab == 0) activos else historial

    PantallaBase(
        titulo = "Mis pedidos",
        fab = {
            FloatingActionButton(onClick = onNuevoPedido) { Icon(Icons.Filled.Add, contentDescription = "Nuevo pedido") }
        }
    ) { pad ->
        Column(Modifier.padding(pad).fillMaxSize()) {
            TabRow(selectedTabIndex = tab) {
                Tab(selected = tab == 0, onClick = { tab = 0 }, text = { Text("Activos (${activos.size})") })
                Tab(selected = tab == 1, onClick = { tab = 1 }, text = { Text("Historial") })
            }
            if (lista.isEmpty()) {
                Text(if (tab == 0) "No tienes pedidos activos" else "Aún no tienes historial",
                    Modifier.padding(24.dp), color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            LazyColumn(
                contentPadding = PaddingValues(16.dp, 16.dp, 16.dp, 96.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(lista, key = { it.pedido.id }) { p ->
                    TarjetaPedido(p, onClick = { onDetalle(p.pedido.id) }) {
                        if (p.estadoEnum() == EstadoPedido.LISTO && p.pedido.casillero != null) {
                            Card(
                                Modifier.fillMaxWidth().padding(top = 4.dp),
                                colors = CardDefaults.cardColors(containerColor = EstadoPedido.LISTO.color.copy(alpha = 0.15f))
                            ) {
                                Text("Recógelo en el casillero ${p.pedido.casillero}", Modifier.padding(12.dp),
                                    color = EstadoPedido.LISTO.color, fontWeight = FontWeight.Bold)
                            }
                        }
                        if (p.estadoEnum() == EstadoPedido.RECIBIDO) {
                            TextButton(onClick = { vm.cancelar(p) }) {
                                Text("Cancelar", color = MaterialTheme.colorScheme.error)
                            }
                        }
                    }
                }
            }
        }
    }
}
