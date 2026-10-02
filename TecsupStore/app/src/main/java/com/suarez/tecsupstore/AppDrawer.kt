package com.suarez.tecsupstore

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

sealed class DestinoDrawer(val ruta: String, val titulo: String, val icono: ImageVector) {
    object Inicio : DestinoDrawer("inicio", "Inicio", Icons.Default.Home)
    object MisPedidos : DestinoDrawer("pedidos", "Mis pedidos", Icons.Default.ShoppingBag)
    object Favoritos : DestinoDrawer("favoritos", "Favoritos", Icons.Default.Favorite)
    object Perfil : DestinoDrawer("perfil", "Perfil", Icons.Default.Person)
    object CerrarSesion : DestinoDrawer("login", "Cerrar sesión", Icons.Default.ExitToApp)
}

@Composable
fun AppDrawer(destinoActual: String, onNavegar: (DestinoDrawer) -> Unit, modifier: Modifier = Modifier) {
    val opciones = listOf(DestinoDrawer.Inicio, DestinoDrawer.MisPedidos, DestinoDrawer.Favoritos, DestinoDrawer.Perfil, DestinoDrawer.CerrarSesion)
    ModalDrawerSheet(modifier = modifier) {
        Column(modifier = Modifier.fillMaxWidth().padding(24.dp)) {
            Box(modifier = Modifier.size(56.dp).clip(CircleShape).background(MaterialTheme.colorScheme.primaryContainer), contentAlignment = Alignment.Center) {
                Text(text = "MR", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onPrimaryContainer)
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = "María Rojas", style = MaterialTheme.typography.titleLarge)
            Text(text = "maria@tecsup.edu.pe", style = MaterialTheme.typography.bodyMedium)
        }
        HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
        Spacer(modifier = Modifier.height(12.dp))
        opciones.forEach { destino ->
            NavigationDrawerItem(
                label = { Text(destino.titulo) },
                icon = { Icon(destino.icono, null) },
                selected = destinoActual == destino.ruta,
                onClick = { onNavegar(destino) },
                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
            )
        }
    }
}