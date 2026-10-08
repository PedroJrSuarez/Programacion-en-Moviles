package com.suarez.saludpluscitas.data.model

data class Medico(
    val id: Int,
    val nombre: String,
    val especialidadId: Int,
    val calificacion: Double,
    val resenas: Int,
    val disponibilidad: String,
    val cmp: String = (12345 + (id - 1) * 873).toString()
)
