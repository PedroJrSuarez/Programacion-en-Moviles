package com.suarez.tecsupfit.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EventAvailable
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Rutas(val ruta: String) {
    object Inicio : Rutas("inicio")
    object Detalle : Rutas("detalle/{claseId}") {
        fun crearRuta(claseId: Int) = "detalle/$claseId"
    }
    object Confirmacion : Rutas("confirmacion/{claseId}") {
        fun crearRuta(claseId: Int) = "confirmacion/$claseId"
    }
    object Reservas : Rutas("reservas")
    object Rutinas : Rutas("rutinas")
    object Perfil : Rutas("perfil")
}

data class ItemBottomBar(
    val ruta: String,
    val label: String,
    val icono: ImageVector
)

val itemsBottomBar = listOf(
    ItemBottomBar(Rutas.Inicio.ruta, "Inicio", Icons.Filled.Home),
    ItemBottomBar(Rutas.Reservas.ruta, "Reservas", Icons.Filled.EventAvailable),
    ItemBottomBar(Rutas.Rutinas.ruta, "Rutinas", Icons.Filled.FitnessCenter),
    ItemBottomBar(Rutas.Perfil.ruta, "Perfil", Icons.Filled.Person)
)

val rutasConBottomBar = listOf(
    Rutas.Inicio.ruta,
    Rutas.Reservas.ruta,
    Rutas.Rutinas.ruta,
    Rutas.Perfil.ruta
)
