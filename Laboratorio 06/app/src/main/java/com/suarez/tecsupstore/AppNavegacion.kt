package com.suarez.tecsupstore

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavegacion() {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var rutaActual by remember { mutableStateOf(DestinoDrawer.Inicio.ruta) }

    ModalNavigationDrawer(drawerState = drawerState, drawerContent = {
        AppDrawer(destinoActual = rutaActual, onNavegar = { destino -> rutaActual = destino.ruta; scope.launch { drawerState.close() } })
    }) {
        Scaffold(topBar = {
            TopAppBar(title = { Text("TECSUP...Store") }, navigationIcon = {
                IconButton(onClick = { scope.launch { if (drawerState.isClosed) drawerState.open() else drawerState.close() } }) { Icon(Icons.Default.Menu, null) }
            })
        }) { padding -> Surface(modifier = Modifier.padding(padding)) { PantallaInicio() } }
    }
}