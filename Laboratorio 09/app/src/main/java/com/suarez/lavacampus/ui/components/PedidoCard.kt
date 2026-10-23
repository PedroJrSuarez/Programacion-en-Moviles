package com.suarez.lavacampus.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.suarez.lavacampus.data.local.relation.PedidoConDetalle
import com.suarez.lavacampus.model.estadoEnum
import com.suarez.lavacampus.model.soles

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PedidoCard(pedido: PedidoConDetalle, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Card(onClick = onClick, modifier = modifier) {
        Row(Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text("Pedido #${pedido.pedido.id}", fontWeight = FontWeight.Bold)
                Text(pedido.detalles.joinToString(", ") { it.servicio.nombre },
                    style = MaterialTheme.typography.bodySmall, maxLines = 2)
                Text("Entrega: ${pedido.pedido.fechaEntrega}", style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Column(horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.spacedBy(6.dp)) {
                EstadoPedidoChip(pedido.estadoEnum())
                Text(pedido.pedido.total.soles(), fontWeight = FontWeight.Bold)
            }
        }
    }
}
