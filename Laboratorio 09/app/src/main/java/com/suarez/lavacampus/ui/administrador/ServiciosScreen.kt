package com.suarez.lavacampus.ui.administrador

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.suarez.lavacampus.data.local.entity.Servicio
import com.suarez.lavacampus.model.textoPrecio
import com.suarez.lavacampus.ui.components.IconoServicio
import com.suarez.lavacampus.ui.components.PantallaBase
import com.suarez.lavacampus.viewmodel.ServiciosViewModel

@Composable
fun ServiciosScreen(vm: ServiciosViewModel, onEditar: (Int) -> Unit) {
    val servicios by vm.servicios.collectAsState()
    PantallaBase("Servicios") { pad ->
        LazyColumn(
            Modifier.padding(pad).fillMaxSize(),
            contentPadding = PaddingValues(16.dp, 16.dp, 16.dp, 96.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(servicios, key = { it.id }) { s ->
                ServicioItem(s, onToggle = { vm.alternar(s) }, onEditar = { onEditar(s.id) })
            }
        }
    }
}

@Composable
private fun ServicioItem(s: Servicio, onToggle: () -> Unit, onEditar: () -> Unit) {
    Card(Modifier.fillMaxWidth()) {
        Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            IconoServicio(s.icono, Modifier.size(48.dp))
            Column(Modifier.weight(1f)) {
                Text(s.nombre, fontWeight = FontWeight.Bold)
                Text(s.textoPrecio(), style = MaterialTheme.typography.bodySmall)
                Text("${s.horasEntrega} h · ${if (s.activo) "Activo" else "Inactivo"}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            IconButton(onClick = onEditar) { Icon(Icons.Filled.Edit, contentDescription = "Editar") }
            Switch(checked = s.activo, onCheckedChange = { onToggle() })
        }
    }
}
