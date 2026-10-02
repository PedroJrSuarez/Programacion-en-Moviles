package com.suarez.tecsupstore

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

sealed class DestinoDrawer(val ruta: String, val titulo: String, val icono: ImageVector) {
    object Inicio : DestinoDrawer("inicio", "Inicio", Icons.Default.Home)
    object MisPedidos : DestinoDrawer("pedidos", "Mis pedidos", Icons.Default.ShoppingBag)
    object Favoritos : DestinoDrawer("favoritos", "Favoritos", Icons.Default.Favorite)
    object Perfil : DestinoDrawer("perfil", "Perfil", Icons.Default.Person)
}

@Composable
fun AppDrawer(destinoActual: String, onNavegar: (DestinoDrawer) -> Unit, modifier: Modifier = Modifier) {
    val opciones = listOf(DestinoDrawer.Inicio, DestinoDrawer.MisPedidos, DestinoDrawer.Favoritos, DestinoDrawer.Perfil)
    ModalDrawerSheet(modifier = modifier) {
        Spacer(modifier = Modifier.height(24.dp))
        opciones.forEach { destino ->
            NavigationDrawerItem(label = { Text(destino.titulo) }, icon = { Icon(destino.icono, null) }, selected = false, onClick = { onNavegar(destino) })
        }
    }
}