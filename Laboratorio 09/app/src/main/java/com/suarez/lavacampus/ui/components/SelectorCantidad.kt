package com.suarez.lavacampus.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import java.util.Locale

@Composable
fun SelectorCantidad(
    valor: Double, paso: Double, minimo: Double, maximo: Double,
    onCambio: (Double) -> Unit, modifier: Modifier = Modifier
) {
    Row(modifier, verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = { onCambio(valor - paso) }, enabled = valor > minimo) {
            Icon(Icons.Filled.Remove, contentDescription = "Menos")
        }
        Text(
            if (paso < 1.0) String.format(Locale.US, "%.1f", valor) else valor.toInt().toString(),
            Modifier.widthIn(min = 36.dp), textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold
        )
        IconButton(onClick = { onCambio(valor + paso) }, enabled = valor < maximo) {
            Icon(Icons.Filled.Add, contentDescription = "Más")
        }
    }
}
