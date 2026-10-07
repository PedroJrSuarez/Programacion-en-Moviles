package com.saludplus.citas.data.model

data class Cita(
    val id: Int,
    val usuarioId: Int,
    val medicoId: Int,
    val fecha: String, // yyyy-MM-dd
    val hora: String   // HH:mm
)
