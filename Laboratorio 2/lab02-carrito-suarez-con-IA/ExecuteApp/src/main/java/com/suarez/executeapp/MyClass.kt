package com.suarez.executeapp

data class Producto(
    val nombre: String,
    val precio: Double,
        var cantidad: Int
    )

fun main() {
    println("=========================================")
    println("   CARRITO DE COMPRAS - TIENDA TECSUP    ")
    println("=========================================")

    val nombreCliente = "Juan Leon"
    val carrito = mutableListOf<Producto>()

    println("Cliente: $nombreCliente\n")

    val lista = listOf(
        Producto("Laptop HP", 2500.0, 1),
        Producto("Mouse Logitech", 45.5, 2),
        Producto("Audifonos Sony", 120.0, 1),
        Producto("USB Kingston 64GB", 25.0, 3)
    )

    for (prod in lista) {
        carrito.add(prod)
        println("Producto agregado: ${prod.nombre}")
    }
}