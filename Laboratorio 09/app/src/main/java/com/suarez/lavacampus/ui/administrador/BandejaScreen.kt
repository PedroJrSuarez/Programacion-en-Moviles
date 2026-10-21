package com.suarez.lavacampus.ui.administrador

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.suarez.lavacampus.model.EstadoPedido
import com.suarez.lavacampus.model.estadoEnum
import com.suarez.lavacampus.model.soles
import com.suarez.lavacampus.ui.components.PantallaBase
import com.suarez.lavacampus.ui.components.TarjetaPedido
import com.suarez.lavacampus.viewmodel.BandejaViewModel
import com.suarez.lavacampus.viewmodel.FiltroBandeja

@Composable
fun BandejaScreen(vm: BandejaViewModel, onAbrir: (Int) -> Unit) {
    val pedidos by vm.pedidos.collectAsState()
    val filtro by vm.filtro.collectAsState()
    val busqueda by vm.busqueda.collectAsState()

    PantallaBase("Pedidos") { pad ->
        Column(Modifier.padding(pad).fillMaxSize()) {
            OutlinedTextField(
                value = busqueda, onValueChange = vm::buscar, singleLine = true,
                label = { Text("Buscar por estudiante o número") },
                modifier = Modifier.fillMaxWidth().padding(16.dp, 12.dp, 16.dp, 4.dp)
            )
            LazyRow(contentPadding = PaddingValues(horizontal = 16.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(FiltroBandeja.entries.toList()) { f ->
                    FilterChip(selected = filtro == f, onClick = { vm.elegirFiltro(f) }, label = { Text(f.texto) })
                }
            }
            if (pedidos.isEmpty()) {
                Text("No hay pedidos", Modifier.padding(24.dp), color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(pedidos, key = { it.pedido.id }) { p ->
                    val estado = p.estadoEnum()
                    TarjetaPedido(p, onClick = { onAbrir(p.pedido.id) }) {
                        Row(Modifier.fillMaxWidth().padding(top = 4.dp), verticalAlignment = Alignment.CenterVertically) {
                            Column(Modifier.weight(1f)) {
                                Text(p.usuario.nombre, style = MaterialTheme.typography.bodyMedium)
                                Text("${p.detalles.size} servicio(s) · ${p.pedido.total.soles()}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                            when (estado) {
                                EstadoPedido.RECIBIDO -> Button(onClick = { vm.iniciar(p) }) { Text("Iniciar") }
                                EstadoPedido.EN_PROCESO -> Button(onClick = { onAbrir(p.pedido.id) }) { Text("Marcar listo") }
                                EstadoPedido.LISTO -> Button(onClick = { onAbrir(p.pedido.id) }) { Text("Entregar") }
                                else -> {}
                            }
                        }
                    }
                }
            }
        }
    }
}
