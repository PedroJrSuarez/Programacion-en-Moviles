package com.suarez.murocampus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.suarez.murocampus.data.PostRepositoryRemote
import com.suarez.murocampus.data.remote.RetrofitClient
import com.suarez.murocampus.ui.PantallaCrear
import com.suarez.murocampus.ui.PantallaDetalle
import com.suarez.murocampus.ui.PantallaLista
import com.suarez.murocampus.ui.theme.MyApplicationTheme
import com.suarez.murocampus.viewmodel.PostsViewModel
import com.suarez.murocampus.viewmodel.PostsViewModelFactory

sealed interface Pantalla {
    data object Lista : Pantalla
    data class Detalle(val postId: Int) : Pantalla
    data object Crear : Pantalla
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                val repository = remember { PostRepositoryRemote(RetrofitClient.api) }
                val viewModel: PostsViewModel = viewModel(
                    factory = PostsViewModelFactory(repository)
                )

                val estadoLista by viewModel.lista.collectAsStateWithLifecycle()
                val busqueda by viewModel.busqueda.collectAsStateWithLifecycle()
                val usuarioFiltro by viewModel.usuarioFiltro.collectAsStateWithLifecycle()
                val estadoDetalle by viewModel.detalle.collectAsStateWithLifecycle()
                val crearEstado by viewModel.crearEstado.collectAsStateWithLifecycle()
                val eliminarEstado by viewModel.eliminarEstado.collectAsStateWithLifecycle()

                var pantallaActual by remember { mutableStateOf<Pantalla>(Pantalla.Lista) }

                BackHandler(enabled = pantallaActual !is Pantalla.Lista) {
                    pantallaActual = Pantalla.Lista
                }

                when (val pantalla = pantallaActual) {
                    is Pantalla.Lista -> {
                        PantallaLista(
                            estadoLista = estadoLista,
                            busqueda = busqueda,
                            usuarioFiltro = usuarioFiltro,
                            onBuscar = viewModel::buscar,
                            onFiltrar = { userId ->
                                viewModel.filtrar(userId)
                            },
                            onReintentar = {
                                viewModel.cargar()
                            },
                            onPostClick = { postId ->
                                viewModel.abrir(postId)
                                pantallaActual = Pantalla.Detalle(postId)
                            },
                            onCreateClick = {
                                viewModel.limpiarCrearEstado()
                                pantallaActual = Pantalla.Crear
                            }
                        )
                    }
                    is Pantalla.Detalle -> {
                        PantallaDetalle(
                            estadoDetalle = estadoDetalle,
                            eliminarEstado = eliminarEstado,
                            onBackClick = {
                                pantallaActual = Pantalla.Lista
                            },
                            onReintentar = {
                                viewModel.abrir(pantalla.postId)
                            },
                            onEliminar = {
                                viewModel.eliminar(pantalla.postId)
                            }
                        )
                    }
                    is Pantalla.Crear -> {
                        PantallaCrear(
                            crearEstado = crearEstado,
                            onPublicar = { titulo, texto ->
                                viewModel.publicar(titulo, texto)
                            },
                            onBackClick = {
                                pantallaActual = Pantalla.Lista
                            },
                            onResetEstado = {
                                viewModel.limpiarCrearEstado()
                            }
                        )
                    }
                }
            }
        }
    }
}
