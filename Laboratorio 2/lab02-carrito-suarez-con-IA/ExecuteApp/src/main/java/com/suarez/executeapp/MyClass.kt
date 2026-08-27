package com.suarez.executeapp

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

class CarritoCompras(val cliente: String) {
    private val _items = mutableListOf<ItemCarrito>()
    val items: List<ItemCarrito> get() = _items.toList()

    fun agregarProducto(item: ItemCarrito) {
        _items.add(item)
        println("Producto agregado: ${item.nombre}")
    }

    fun calcularSubtotal(): Double = _items.sumOf { it.calcularImporte() }
    fun calcularIGV(): Double = calcularSubtotal() * 0.18
    fun calcularTotal(): Double = calcularSubtotal() + calcularIGV()

    fun calcularDescuento(): Double {
        val total = calcularTotal()
        return when {
            total > 5000 -> total * 0.10
            total > 3000 -> total * 0.05
            else -> 0.0
        }
    }

    fun obtenerProductoMasCaro(): ItemCarrito? = _items.maxByOrNull { it.precioBase }

    fun mostrarReporte() {
        println("=========================================")
        println("   CARRITO DE COMPRAS - TIENDA TECSUP    ")
        println("=========================================")
        println("Cliente: $cliente\n")

        println("DETALLE DEL CARRITO")
        _items.forEachIndexed { index, item ->
            println(String.format("%d. %-20s x%d  S/ %8.2f", index + 1, item.nombre, item.cantidad, item.calcularImporte()))
        }
        println("-----------------------------------------")

        val subtotal = calcularSubtotal()
        val igv = calcularIGV()
        val total = calcularTotal()
        val descuento = calcularDescuento()

        println(String.format("Cantidad de productos : %d", _items.sumOf { it.cantidad }))
        println(String.format("Subtotal              : S/%8.2f", subtotal))
        println(String.format("IGV (18%%)             : S/%8.2f", igv))
        println(String.format("TOTAL A PAGAR         : S/%8.2f", total))

        obtenerProductoMasCaro()?.let {
            println(String.format("\nProducto mas caro: %s (S/%.2f)", it.nombre, it.precioBase))
        }

        if (descuento > 0) {
            println("Descuento aplicado: 5% por compra mayor a S/ 3000")
            println(String.format("TOTAL CON DESCUENTO   : S/%8.2f", total - descuento))
        }

        println("\nGracias por su compra, $cliente!")
    }
}

fun main() {
    val carrito = CarritoCompras("Juan Leon")
    carrito.agregarProducto(ProductoFisico("Laptop HP", 2500.0, 1))
    carrito.agregarProducto(ProductoFisico("Mouse Logitech", 45.5, 2))
    carrito.agregarProducto(ProductoFisico("Audifonos Sony", 120.0, 1))
    carrito.agregarProducto(ProductoDigital("USB Kingston 64GB", 25.0, 3))

    println()
    carrito.mostrarReporte()
}