package com.suarez.tecsupfit.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.suarez.tecsupfit.data.ReservaClase
import com.suarez.tecsupfit.ui.theme.GrayCard
import com.suarez.tecsupfit.ui.theme.TealPrimary
import com.suarez.tecsupfit.ui.theme.TextDark
import com.suarez.tecsupfit.ui.theme.TextGray

@Composable
fun TarjetaReserva(reserva: ReservaClase) {
    val confirmada = reserva.estado == "Confirmada"
    val colorFranja = if (confirmada) TealPrimary else TextGray.copy(alpha = 0.4f)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .clip(RoundedCornerShape(12.dp))
            .background(GrayCard)
    ) {
        androidx.compose.foundation.layout.Box(
            modifier = Modifier
                .width(4.dp)
                .fillMaxHeight()
                .background(colorFranja)
        )

        Column(modifier = Modifier.padding(14.dp)) {
            Text(
                text = reserva.clase.nombre,
                fontWeight = FontWeight.Bold,
                color = TextDark,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = reserva.cuando,
                color = TextGray,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = reserva.estado,
                color = if (confirmada) TealPrimary else TextGray,
                fontWeight = FontWeight.Medium,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}
