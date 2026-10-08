package com.suarez.saludpluscitas.data.model

data class Usuario(
    val id: Int,
    val nombre: String,
    val correo: String,
    val clave: String,
    val telefono: String = ""
)
