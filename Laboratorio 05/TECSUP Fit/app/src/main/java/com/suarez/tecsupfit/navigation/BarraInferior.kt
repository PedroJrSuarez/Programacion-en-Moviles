package com.suarez.tecsupfit.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.suarez.tecsupfit.ui.theme.TealPrimary
import com.suarez.tecsupfit.ui.theme.TextGray

@Composable
fun BarraInferior(navController: NavController, rutaActual: String?) {
    NavigationBar(containerColor = Color.White) {
        itemsBottomBar.forEach { item ->
            val seleccionado = rutaActual == item.ruta
            NavigationBarItem(
                selected = seleccionado,
                onClick = {
                    if (item.ruta == Rutas.Inicio.ruta) {
                        navController.popBackStack(Rutas.Inicio.ruta, inclusive = false)
                    } else {
                        navController.navigate(item.ruta) {
                            popUpTo(Rutas.Inicio.ruta) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = { Icon(item.icono, contentDescription = item.label) },
                label = { Text(item.label) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = TealPrimary,
                    selectedTextColor = TealPrimary,
                    indicatorColor = Color.Transparent,
                    unselectedIconColor = TextGray,
                    unselectedTextColor = TextGray
                )
            )
        }
    }
}
