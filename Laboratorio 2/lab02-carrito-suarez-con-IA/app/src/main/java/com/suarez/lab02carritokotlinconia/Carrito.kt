package com.suarez.lab02carritokotlinconia

data class Producto(
    val nombre: String,
    val precio: Double,
    var cantidad: Int
)

fun calcularSubtotal(productos: List<Producto>): Double {
    var subtotal = 0.0
    for (p in productos) {
        subtotal += p.precio * p.cantidad
    }
    return subtotal
}

fun calcularIGV(subtotal: Double): Double = subtotal * 0.18

fun calcularTotal(subtotal: Double, igv: Double): Double = subtotal + igv

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

    val subtotal = calcularSubtotal(carrito)
    val igv = calcularIGV(subtotal)
    val total = calcularTotal(subtotal, igv)

    println("\nSubtotal    : S/ $subtotal")
    println("IGV (18%)   : S/ $igv")
    println("TOTAL       : S/ $total")
}