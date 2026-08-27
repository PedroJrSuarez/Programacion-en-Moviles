package com.suarez.lab02carritokotlinconia

abstract class ItemCarrito(
    val nombre: String,
    val precioBase: Double,
    var cantidad: Int
) {
    abstract fun calcularImporte(): Double
}

class ProductoFisico(
    nombre: String,
    precioBase: Double,
    cantidad: Int,
    val costoEnvio: Double = 0.0
) : ItemCarrito(nombre, precioBase, cantidad) {
    override fun calcularImporte(): Double = (precioBase + costoEnvio) * cantidad
}

class ProductoDigital(
    nombre: String,
    precioBase: Double,
    cantidad: Int
) : ItemCarrito(nombre, precioBase, cantidad) {
    override fun calcularImporte(): Double = precioBase * cantidad
}

fun main() {
    println("=========================================")
    println("   CARRITO DE COMPRAS - TIENDA TECSUP    ")
    println("=========================================")

    val items = listOf<ItemCarrito>(
        ProductoFisico("Laptop HP", 2500.0, 1),
        ProductoFisico("Mouse Logitech", 45.5, 2),
        ProductoFisico("Audifonos Sony", 120.0, 1),
        ProductoDigital("USB Kingston 64GB", 25.0, 3)
    )

    for (item in items) {
        println("Producto agregado: ${item.nombre} -> Importe: S/ ${item.calcularImporte()}")
    }
}