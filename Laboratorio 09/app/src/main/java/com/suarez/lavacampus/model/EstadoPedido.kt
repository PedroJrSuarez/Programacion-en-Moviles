package com.suarez.lavacampus.model

import androidx.compose.ui.graphics.Color

enum class EstadoPedido(val color: Color) {
    RECIBIDO(Color(0xFFD97706)),
    EN_PROCESO(Color(0xFF2563EB)),
    LISTO(Color(0xFF16A34A)),
    ENTREGADO(Color(0xFF475569)),
    CANCELADO(Color(0xFFDC2626))
}
