package com.tecsup.campusgps.ui

import androidx.compose.foundation.layout.Arrangement
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
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
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
}
