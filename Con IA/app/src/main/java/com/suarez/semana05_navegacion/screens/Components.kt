package com.suarez.semana05_navegacion.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.suarez.semana05_navegacion.ui.theme.LightLavender
import com.suarez.semana05_navegacion.ui.theme.Lavender
import com.suarez.semana05_navegacion.ui.theme.Primary

@Composable
fun StudentAvatar(studentId: Int, size: Dp) {
    val (backgroundColor, iconColor) = when (studentId % 3) {
        0 -> Primary to Color.White
        1 -> Lavender to Primary
        else -> LightLavender to Primary
    }

    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.Person,
            contentDescription = "Avatar",
            tint = iconColor,
            modifier = Modifier.size(size * 0.6f)
        )
    }
}
