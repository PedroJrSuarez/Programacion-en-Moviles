package com.suarez.lavacampus.ui.administrador

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.suarez.lavacampus.model.EstadoPedido
import com.suarez.lavacampus.model.soles
import com.suarez.lavacampus.ui.components.Barras
import com.suarez.lavacampus.ui.components.Dona
import com.suarez.lavacampus.ui.components.PantallaBase
import com.suarez.lavacampus.viewmodel.PanelViewModel

@Composable
fun PanelScreen(vm: PanelViewModel, onVerListos: () -> Unit) {
    val hoy by vm.pedidosHoy.collectAsState()
    val enProceso by vm.enProceso.collectAsState()
    val porRecoger by vm.porRecoger.collectAsState()
    val ingresos by vm.ingresosSemana.collectAsState()
    val porEstado by vm.porEstado.collectAsState()
    val porServicio by vm.porServicio.collectAsState()

    PantallaBase("Panel") { pad ->
        Column(
            Modifier.padding(pad).fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Contador("Hoy", hoy, EstadoPedido.RECIBIDO.color, Modifier.weight(1f))
                Contador("En proceso", enProceso, EstadoPedido.EN_PROCESO.color, Modifier.weight(1f))
                Contador("Por recoger", porRecoger, EstadoPedido.LISTO.color, Modifier.weight(1f))
            }

            Card(
                Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Column(Modifier.padding(18.dp)) {
                    Text("Ingresos de la semana")
                    Text(ingresos.soles(), style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                }
            }

            Card(Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp)) {
                    Text("Pedidos por estado", fontWeight = FontWeight.Bold)
                    if (porEstado.isEmpty()) Text("Sin datos") else Dona(porEstado, Modifier.fillMaxWidth().height(200.dp))
                }
            }

            Card(Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp)) {
                    Text("Ingresos por servicio", fontWeight = FontWeight.Bold)
                    if (porServicio.isEmpty()) Text("Sin ingresos esta semana")
                    else Barras(porServicio, Modifier.fillMaxWidth().height(200.dp))
                }
            }

            if (porRecoger > 0) {
                Card(
                    Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer)
                ) {
                    Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                        Text("Hay $porRecoger pedido(s) LISTO esperando recojo", Modifier.weight(1f), fontWeight = FontWeight.Bold)
                        Button(onClick = onVerListos) { Text("Ver") }
                    }
                }
            }
        }
    }
}

@Composable
private fun Contador(titulo: String, valor: Int, color: androidx.compose.ui.graphics.Color, modifier: Modifier) {
    Card(modifier, colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.15f))) {
        Column(Modifier.padding(12.dp).fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(valor.toString(), style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold, color = color)
            Text(titulo, style = MaterialTheme.typography.bodySmall)
        }
    }
}
