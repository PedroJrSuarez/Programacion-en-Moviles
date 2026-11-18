package com.tecsup.campusgps.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FiltrosLugares(
    categorias: List<String>,
    categoriaSeleccionada: String?,
    onCategoria: (String?) -> Unit,
    radios: List<Int>,
    radioSeleccionado: Int?,
    onRadio: (Int?) -> Unit,
    radioHabilitado: Boolean,
    modifier: Modifier = Modifier
) {
    Column(modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(2.dp)) {
        LazyRow(
            contentPadding = PaddingValues(horizontal = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                FilterChip(
                    selected = categoriaSeleccionada == null,
                    onClick = { onCategoria(null) },
                    label = { Text("Todos") }
                )
            }
            items(categorias) { categoria ->
                FilterChip(
                    selected = categoriaSeleccionada == categoria,
                    onClick = { onCategoria(categoria) },
                    label = { Text(categoria) }
                )
            }
        }
        LazyRow(
            contentPadding = PaddingValues(horizontal = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                FilterChip(
                    selected = radioSeleccionado == null,
                    onClick = { onRadio(null) },
                    enabled = radioHabilitado,
                    label = { Text("Sin radio") }
                )
            }
            items(radios) { radio ->
                FilterChip(
                    selected = radioSeleccionado == radio,
                    onClick = { onRadio(radio) },
                    enabled = radioHabilitado,
                    label = { Text("$radio m") }
                )
            }
        }
    }
}
