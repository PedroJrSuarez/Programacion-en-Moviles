package com.suarez.tecsupfit.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.suarez.tecsupfit.ui.theme.GrayChipInactive
import com.suarez.tecsupfit.ui.theme.TealPrimary
import com.suarez.tecsupfit.ui.theme.TextDark
import com.suarez.tecsupfit.ui.theme.White

@Composable
fun ChipFiltro(texto: String, seleccionado: Boolean, onClick: () -> Unit) {
    val fondo = if (seleccionado) TealPrimary else GrayChipInactive
    val color = if (seleccionado) White else TextDark

    Text(
        text = texto,
        color = color,
        fontWeight = if (seleccionado) FontWeight.Bold else FontWeight.Normal,
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(fondo)
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 8.dp)
    )
}
