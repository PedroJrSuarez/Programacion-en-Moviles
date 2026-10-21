package com.suarez.lavacampus.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.suarez.lavacampus.model.EstadoPedido
import com.suarez.lavacampus.model.texto

@Composable
fun EstadoPedidoChip(estado: EstadoPedido, modifier: Modifier = Modifier) {
    Surface(modifier = modifier, shape = RoundedCornerShape(50), color = estado.color.copy(alpha = 0.15f)) {
        Text(
            estado.texto(), Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
            color = estado.color, style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold
        )
    }
}
