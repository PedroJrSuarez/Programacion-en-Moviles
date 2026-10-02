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
}

@Composable
fun AppDrawer(destinoActual: String, onNavegar: (DestinoDrawer) -> Unit, modifier: Modifier = Modifier) {
    ModalDrawerSheet(modifier = modifier) {
        Spacer(modifier = Modifier.height(24.dp))
        NavigationDrawerItem(label = { Text("Inicio") }, icon = { Icon(Icons.Default.Home, null) }, selected = false, onClick = { onNavegar(DestinoDrawer.Inicio) })
        NavigationDrawerItem(label = { Text("Mis pedidos") }, icon = { Icon(Icons.Default.ShoppingBag, null) }, selected = false, onClick = { onNavegar(DestinoDrawer.MisPedidos) })
    }
}