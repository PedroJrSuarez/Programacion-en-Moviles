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

fun calcularDescuento(total: Double): Double {
    return when {
        total > 5000 -> total * 0.10
        total > 3000 -> total * 0.05
        else -> 0.0
    }
}

fun mostrarDetalle(productos: List<Producto>) {
    println("\nDETALLE DEL CARRITO")
    var i = 1
    for (p in productos) {
        val importe = p.precio * p.cantidad
        println(String.format("%d. %-20s x%d  S/ %8.2f", i, p.nombre, p.cantidad, importe))
        i++
    }
}

fun main() {
    println("==========================================")
    println("   CARRITO DE COMPRAS - TIENDA TECSUP    ")
    println("==========================================")

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

    mostrarDetalle(carrito)

    val subtotal = calcularSubtotal(carrito)
    val igv = calcularIGV(subtotal)
    val total = calcularTotal(subtotal, igv)
    val descuento = calcularDescuento(total)

    val totalItems = carrito.sumOf { it.cantidad }

    println(String.format("Cantidad de productos : %d", totalItems))
    println(String.format("Subtotal              : S/%8.2f", subtotal))
    println(String.format("IGV (18%%)             : S/%8.2f", igv))
    println(String.format("TOTAL A PAGAR         : S/%8.2f", total))

    val masCaro = carrito.maxByOrNull { it.precio }
    if (masCaro != null) {
        println(String.format("\nProducto mas caro: %s (S/%.2f)", masCaro.nombre, masCaro.precio))
    }

    if (descuento > 0) {
        println("Descuento aplicado: 5% por compra mayor a S/ 3000")
        println(String.format("TOTAL CON DESCUENTO   : S/%8.2f", total - descuento))
    }

    println("\nGracias por su compra, $nombreCliente!")
}