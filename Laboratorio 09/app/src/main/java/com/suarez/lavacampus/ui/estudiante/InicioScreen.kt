package com.suarez.lavacampus.ui.estudiante

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.suarez.lavacampus.data.local.entity.Usuario
import com.suarez.lavacampus.model.EstadoPedido
import com.suarez.lavacampus.model.estadoEnum
import com.suarez.lavacampus.ui.components.BotonLava
import com.suarez.lavacampus.ui.components.PantallaBase
import com.suarez.lavacampus.ui.components.Seguimiento
import com.suarez.lavacampus.ui.components.TarjetaPedido
import com.suarez.lavacampus.viewmodel.InicioViewModel

@Composable
fun InicioScreen(
    vm: InicioViewModel,
    usuario: Usuario,
    onNuevoPedido: () -> Unit,
    onMisPedidos: () -> Unit,
    onTarifas: () -> Unit,
    onDetalle: (Int) -> Unit
) {
    val activo by vm.pedidoActivo.collectAsState()
    val recientes by vm.recientes.collectAsState()

    PantallaBase("LavaCampus") { pad ->
        LazyColumn(
            Modifier.padding(pad).fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                Text("Hola, ${usuario.nombre}", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                Text(usuario.codigo, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }

            item {
                Card(
                    Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        val p = activo
                        if (p == null) {
                            Text("No tienes pedidos activos", style = MaterialTheme.typography.titleMedium)
                        } else {
                            Text("Pedido #${p.pedido.id}", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                            Seguimiento(p.estadoEnum(), vertical = false, modifier = Modifier.fillMaxWidth(), sobrePrimario = true)
                            Text("Entrega estimada: ${p.pedido.fechaEntrega}")
                            if (p.estadoEnum() == EstadoPedido.LISTO && p.pedido.casillero != null) {
                                Text("Recógelo en el casillero ${p.pedido.casillero}", fontWeight = FontWeight.Bold)
                            }
                            TextButton(onClick = { onDetalle(p.pedido.id) }) {
                                Text("Ver detalle", color = MaterialTheme.colorScheme.onPrimary)
                            }
                        }
                    }
                }
            }

            item { BotonLava("Nuevo pedido", onNuevoPedido, Modifier.fillMaxWidth()) }

            item {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedButton(onClick = onMisPedidos, Modifier.weight(1f)) { Text("Mis pedidos") }
                    OutlinedButton(onClick = onTarifas, Modifier.weight(1f)) { Text("Tarifas") }
                }
            }

            item { Text("Últimos pedidos", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold) }
            if (recientes.isEmpty()) {
                item { Text("Aún no has hecho pedidos", color = MaterialTheme.colorScheme.onSurfaceVariant) }
            }
            items(recientes, key = { it.pedido.id }) { p ->
                TarjetaPedido(p, onClick = { onDetalle(p.pedido.id) }, modifier = Modifier.fillMaxWidth())
            }
        }
    }
}
