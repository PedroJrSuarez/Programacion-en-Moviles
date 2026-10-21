package com.suarez.lavacampus.ui.administrador

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.suarez.lavacampus.model.soles
import com.suarez.lavacampus.ui.components.PantallaBase
import com.suarez.lavacampus.viewmodel.ClientesViewModel

@Composable
fun ClientesScreen(vm: ClientesViewModel) {
    val clientes by vm.clientes.collectAsState()
    val busqueda by vm.busqueda.collectAsState()

    PantallaBase("Clientes") { pad ->
        Column(Modifier.padding(pad).fillMaxSize()) {
            OutlinedTextField(
                value = busqueda, onValueChange = vm::buscar, singleLine = true,
                label = { Text("Buscar por nombre o código") },
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            )
            LazyColumn(contentPadding = PaddingValues(16.dp, 0.dp, 16.dp, 16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                items(clientes, key = { it.id }) { c ->
                    Card(Modifier.fillMaxWidth()) {
                        Row(Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                            val iniciales = c.nombre.split(" ").filter { it.isNotBlank() }.take(2)
                                .joinToString("") { it.first().uppercase() }
                            Box(
                                Modifier.size(46.dp).clip(CircleShape).background(MaterialTheme.colorScheme.primaryContainer),
                                contentAlignment = Alignment.Center
                            ) { Text(iniciales, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary) }
                            Column(Modifier.weight(1f)) {
                                Text(c.nombre, fontWeight = FontWeight.Bold)
                                Text(c.codigo, style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                            Column(horizontalAlignment = Alignment.End) {
                                Text("${c.totalPedidos} pedidos", style = MaterialTheme.typography.bodySmall)
                                Text(c.totalGastado.soles(), fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }
    }
}
