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
import com.suarez.lavacampus.model.textoPrecio
import com.suarez.lavacampus.ui.components.IconoDeServicio
import com.suarez.lavacampus.ui.components.PantallaBase
import com.suarez.lavacampus.viewmodel.ServiciosViewModel

@Composable
fun TarifasScreen(vm: ServiciosViewModel) {
    val servicios by vm.servicios.collectAsState()
    val activos = servicios.filter { it.activo }

    PantallaBase("Tarifas") { pad ->
        LazyColumn(
            Modifier.padding(pad).fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(activos, key = { it.id }) { s ->
                Card(Modifier.fillMaxWidth()) {
                    Row(Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                        IconoDeServicio(s.icono, Modifier.size(52.dp))
                        Column(Modifier.weight(1f)) {
                            Text(s.nombre, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                            Text(s.textoPrecio(), color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                            Text("Entrega en ${s.horasEntrega} h", style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                }
            }
            item {
                Card(Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)) {
                    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text("Horario y reglas", fontWeight = FontWeight.Bold)
                        Text("Atención de lunes a sábado. No atendemos los domingos.")
                        Text("• Mínimo 1 kg en servicios por kilo")
                        Text("• Máximo 3 pedidos activos a la vez")
                        Text("• El pago se realiza al recoger")
                    }
                }
            }
        }
    }
}
