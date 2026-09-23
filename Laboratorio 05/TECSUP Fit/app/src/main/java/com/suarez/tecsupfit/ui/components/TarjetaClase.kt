package com.suarez.tecsupfit.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.suarez.tecsupfit.data.ClaseGimnasio
import com.suarez.tecsupfit.ui.theme.GrayCard
import com.suarez.tecsupfit.ui.theme.TextDark
import com.suarez.tecsupfit.ui.theme.TextGray

@Composable
fun TarjetaClase(clase: ClaseGimnasio, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(GrayCard)
            .clickable { onClick() }
            .padding(12.dp)
    ) {
        IconoTeal(icono = Icons.Filled.FitnessCenter)
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 12.dp)
        ) {
            Text(
                text = clase.nombre,
                fontWeight = FontWeight.Bold,
                color = TextDark,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "${clase.horario} · ${clase.sala}",
                color = TextGray,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
