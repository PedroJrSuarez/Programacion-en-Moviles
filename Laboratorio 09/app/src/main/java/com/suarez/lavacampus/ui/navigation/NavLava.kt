package com.suarez.lavacampus.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Inbox
import androidx.compose.material.icons.filled.LocalLaundryService
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.suarez.lavacampus.LavaApp
import com.suarez.lavacampus.model.Rol
import com.suarez.lavacampus.ui.administrador.AjustesScreen
import com.suarez.lavacampus.ui.administrador.BandejaScreen
import com.suarez.lavacampus.ui.administrador.ClientesScreen
import com.suarez.lavacampus.ui.administrador.FormularioServicioScreen
import com.suarez.lavacampus.ui.administrador.GestionPedidoScreen
import com.suarez.lavacampus.ui.administrador.PanelScreen
import com.suarez.lavacampus.ui.administrador.ServiciosScreen
import com.suarez.lavacampus.ui.components.PantallaSplash
import com.suarez.lavacampus.ui.components.TemaLava
import com.suarez.lavacampus.ui.estudiante.DetallePedidoScreen
import com.suarez.lavacampus.ui.estudiante.InicioScreen
import com.suarez.lavacampus.ui.estudiante.LoginScreen
import com.suarez.lavacampus.ui.estudiante.MisPedidosScreen
import com.suarez.lavacampus.ui.estudiante.NuevoPedidoScreen
import com.suarez.lavacampus.ui.estudiante.PerfilScreen
import com.suarez.lavacampus.ui.estudiante.TarifasScreen
import com.suarez.lavacampus.viewmodel.BandejaViewModel
import com.suarez.lavacampus.viewmodel.ClientesViewModel
import com.suarez.lavacampus.viewmodel.DetallePedidoViewModel
import com.suarez.lavacampus.viewmodel.GestionPedidoViewModel
import com.suarez.lavacampus.viewmodel.InicioViewModel
import com.suarez.lavacampus.viewmodel.MisPedidosViewModel
import com.suarez.lavacampus.viewmodel.NuevoPedidoViewModel
import com.suarez.lavacampus.viewmodel.PanelViewModel
import com.suarez.lavacampus.viewmodel.ServiciosViewModel
import com.suarez.lavacampus.viewmodel.SesionViewModel

object Destinos {
    const val SPLASH = "splash"
    const val LOGIN = "login"
    const val G_EST = "grafo_estudiante"
    const val G_ADM = "grafo_admin"
    const val INICIO = "inicio"
    const val PEDIDOS = "pedidos"
    const val TARIFAS = "tarifas"
    const val PERFIL = "perfil"
    const val NUEVO = "nuevo_pedido"
    const val DETALLE = "detalle/{id}"
    fun detalle(id: Int) = "detalle/$id"
    const val PANEL = "panel"
    const val BANDEJA = "bandeja"
    const val SERVICIOS = "servicios"
    const val CLIENTES = "clientes"
    const val AJUSTES = "ajustes"
    const val GESTION = "gestion/{id}"
    fun gestion(id: Int) = "gestion/$id"
    const val FORM = "servicio_form/{id}"
    fun form(id: Int) = "servicio_form/$id"
}

private data class Tab(val ruta: String, val texto: String, val icono: ImageVector)

private val tabsEstudiante = listOf(
    Tab(Destinos.INICIO, "Inicio", Icons.Filled.Home),
    Tab(Destinos.PEDIDOS, "Pedidos", Icons.Filled.Receipt),
    Tab(Destinos.TARIFAS, "Tarifas", Icons.Filled.LocalOffer),
    Tab(Destinos.PERFIL, "Perfil", Icons.Filled.Person)
)
private val tabsAdmin = listOf(
    Tab(Destinos.PANEL, "Panel", Icons.Filled.Dashboard),
    Tab(Destinos.BANDEJA, "Pedidos", Icons.Filled.Inbox),
    Tab(Destinos.SERVICIOS, "Servicios", Icons.Filled.LocalLaundryService),
    Tab(Destinos.CLIENTES, "Clientes", Icons.Filled.Group),
    Tab(Destinos.AJUSTES, "Ajustes", Icons.Filled.Settings)
)

private fun NavHostController.irATab(ruta: String, inicioGrafo: String) {
    navigate(ruta) {
        popUpTo(inicioGrafo) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}

@Composable
fun NavLava() {
    val app = LocalContext.current.applicationContext as LavaApp
    val repo = app.repository
    val sesion: SesionViewModel = vmDe { SesionViewModel(repo, app.preferencias) }

    val usuario by sesion.usuarioActual.collectAsState()
    val restaurado by sesion.restaurado.collectAsState()
    val oscuro by sesion.modoOscuro.collectAsState()
    var splashListo by rememberSaveable { mutableStateOf(false) }

    val nav = rememberNavController()
    val rol = if (usuario?.rol == Rol.ADMIN.name) Rol.ADMIN else Rol.CLIENTE

    TemaLava(rol, oscuro) {
        val entrada by nav.currentBackStackEntryAsState()
        val rutaActual = entrada?.destination?.route
        val tabs = when (usuario?.rol) {
            Rol.CLIENTE.name -> tabsEstudiante
            Rol.ADMIN.name -> tabsAdmin
            else -> emptyList()
        }

        LaunchedEffect(usuario?.id, usuario?.rol, restaurado, splashListo) {
            if (!restaurado || !splashListo) return@LaunchedEffect
            val destino = when (usuario?.rol) {
                Rol.CLIENTE.name -> Destinos.G_EST
                Rol.ADMIN.name -> Destinos.G_ADM
                else -> Destinos.LOGIN
            }
            nav.navigate(destino) {
                popUpTo(nav.graph.id) { inclusive = true }   // limpia la pila
                launchSingleTop = true
            }
        }

        Scaffold(
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            bottomBar = {
                if (tabs.any { it.ruta == rutaActual }) {
                    NavigationBar {
                        tabs.forEach { t ->
                            NavigationBarItem(
                                selected = rutaActual == t.ruta,
                                onClick = { nav.irATab(t.ruta, tabs.first().ruta) },
                                icon = { Icon(t.icono, contentDescription = t.texto) },
                                label = { Text(t.texto) }
                            )
                        }
                    }
                }
            }
        ) { padding ->
            NavHost(nav, startDestination = Destinos.SPLASH, modifier = Modifier.padding(padding)) {

                composable(Destinos.SPLASH) { PantallaSplash { splashListo = true } }
                composable(Destinos.LOGIN) { LoginScreen(sesion) }

                // ------------------------- Grafo del ESTUDIANTE -------------------------
                navigation(startDestination = Destinos.INICIO, route = Destinos.G_EST) {
                    composable(Destinos.INICIO) {
                        val u = usuario ?: return@composable
                        val vm = vmDe("inicio${u.id}") { InicioViewModel(repo, u) }
                        InicioScreen(
                            vm, u,
                            onNuevoPedido = { nav.navigate(Destinos.NUEVO) },
                            onMisPedidos = { nav.irATab(Destinos.PEDIDOS, Destinos.INICIO) },
                            onTarifas = { nav.irATab(Destinos.TARIFAS, Destinos.INICIO) },
                            onDetalle = { nav.navigate(Destinos.detalle(it)) }
                        )
                    }
                    composable(Destinos.PEDIDOS) {
                        val u = usuario ?: return@composable
                        val vm = vmDe("mis${u.id}") { MisPedidosViewModel(repo, u.id) }
                        MisPedidosScreen(vm, onDetalle = { nav.navigate(Destinos.detalle(it)) },
                            onNuevoPedido = { nav.navigate(Destinos.NUEVO) })
                    }
                    composable(Destinos.TARIFAS) {
                        val vm = vmDe("tarifas") { ServiciosViewModel(repo) }
                        TarifasScreen(vm)
                    }
                    composable(Destinos.PERFIL) {
                        val u = usuario ?: return@composable
                        val vm = vmDe("mis${u.id}") { MisPedidosViewModel(repo, u.id) }
                        PerfilScreen(sesion, vm, u)
                    }
                    composable(Destinos.NUEVO) {
                        val u = usuario ?: return@composable
                        val vm = vmDe("nuevo${u.id}") { NuevoPedidoViewModel(repo, app.preferencias, u) }
                        NuevoPedidoScreen(
                            vm, onVolver = { nav.popBackStack() },
                            onCreado = {
                                nav.navigate(Destinos.PEDIDOS) {
                                    popUpTo(Destinos.INICIO)
                                    launchSingleTop = true
                                }
                            }
                        )
                    }
                    composable(Destinos.DETALLE, arguments = listOf(navArgument("id") { type = NavType.IntType })) { e ->
                        val id = e.arguments?.getInt("id") ?: return@composable
                        val vm = vmDe("detalle$id") { DetallePedidoViewModel(repo, id) }
                        DetallePedidoScreen(vm, onVolver = { nav.popBackStack() })
                    }
                }

                // ----------------------- Grafo del ADMINISTRADOR -----------------------
                navigation(startDestination = Destinos.PANEL, route = Destinos.G_ADM) {
                    composable(Destinos.PANEL) {
                        val vm = vmDe("panel") { PanelViewModel(repo) }
                        PanelScreen(vm, onVerListos = { nav.irATab(Destinos.BANDEJA, Destinos.PANEL) })
                    }
                    composable(Destinos.BANDEJA) {
                        val vm = vmDe("bandeja") { BandejaViewModel(repo) }
                        BandejaScreen(vm, onAbrir = { nav.navigate(Destinos.gestion(it)) })
                    }
                    composable(Destinos.SERVICIOS) {
                        val vm = vmDe("servicios") { ServiciosViewModel(repo) }
                        Box(Modifier.fillMaxSize()) {
                            ServiciosScreen(vm, onEditar = { nav.navigate(Destinos.form(it)) })
                            FloatingActionButton(
                                onClick = { nav.navigate(Destinos.form(0)) },
                                modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp)
                            ) { Icon(Icons.Filled.Add, contentDescription = "Nuevo servicio") }
                        }
                    }
                    composable(Destinos.CLIENTES) {
                        val vm = vmDe("clientes") { ClientesViewModel(repo) }
                        ClientesScreen(vm)
                    }
                    composable(Destinos.AJUSTES) {
                        val u = usuario ?: return@composable
                        AjustesScreen(sesion, u)
                    }
                    composable(Destinos.GESTION, arguments = listOf(navArgument("id") { type = NavType.IntType })) { e ->
                        val id = e.arguments?.getInt("id") ?: return@composable
                        val vm = vmDe("gestion$id") { GestionPedidoViewModel(repo, id) }
                        GestionPedidoScreen(vm, onVolver = { nav.popBackStack() })
                    }
                    composable(Destinos.FORM, arguments = listOf(navArgument("id") { type = NavType.IntType })) { e ->
                        val id = e.arguments?.getInt("id") ?: 0
                        val vm = vmDe("form$id") { ServiciosViewModel(repo) }
                        FormularioServicioScreen(vm, id, onVolver = { nav.popBackStack() })
                    }
                }
            }
        }
    }
}
