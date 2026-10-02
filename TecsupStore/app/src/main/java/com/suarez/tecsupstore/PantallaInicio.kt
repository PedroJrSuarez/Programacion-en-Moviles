package com.suarez.tecsupstore

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
@Composable
fun PantallaInicio() {
    val categorias = listOf("Más vendidos")
    val listaProductos = listOf(Producto(1, "Audífonos", "S/ 89.00"), Producto(2, "Smartwatch", "S/ 199.00"))
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(listaProductos) { p -> TarjetaProducto(nombre = p.nombre, precio = p.precio) }
        }
    }
}