package com.suarez.tecsupfit.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.suarez.tecsupfit.ui.theme.TealLight
import com.suarez.tecsupfit.ui.theme.TealPrimary

@Composable
fun IconoTeal(
    icono: ImageVector,
    modifier: Modifier = Modifier,
    tamano: Dp = 44.dp,
    tamanoIcono: Dp = 22.dp,
    forma: Shape = RoundedCornerShape(10.dp),
    fondo: Color = TealLight,
    colorIcono: Color = TealPrimary
) {
    Box(
        modifier = modifier
            .size(tamano)
            .background(fondo, forma),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icono,
            contentDescription = null,
            tint = colorIcono,
            modifier = Modifier.size(tamanoIcono)
        )
    }
}
