package com.suarez.lavacampus.model

data class ClienteResumen(
    val id: Int,
    val nombre: String,
    val codigo: String,
    val totalPedidos: Int,
    val totalGastado: Double
)
