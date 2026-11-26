package com.suarez.murocampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suarez.murocampus.data.PostRepository
import com.suarez.murocampus.model.DetalleUi
import com.suarez.murocampus.model.NuevoPost
import com.suarez.murocampus.model.Post
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

internal fun filtrarPorTitulo(posts: List<Post>, titulo: String): List<Post> {
    val consulta = titulo.trim()
    return if (consulta.isEmpty()) posts
    else posts.filter { it.title.contains(consulta, ignoreCase = true) }
}

class PostsViewModel(private val repo: PostRepository) : ViewModel() {

    private val _lista = MutableStateFlow<UiState<List<Post>>>(UiState.Cargando)
    private val _busqueda = MutableStateFlow("")
    val busqueda = _busqueda.asStateFlow()
    val lista = combine(_lista, _busqueda) { estado, consulta ->
        if (estado is UiState.Exito) {
            UiState.Exito(filtrarPorTitulo(estado.datos, consulta))
        } else {
            estado
        }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, UiState.Cargando)

    private val _detalle = MutableStateFlow<UiState<DetalleUi>>(UiState.Cargando)
    val detalle = _detalle.asStateFlow()

    private val _usuarioFiltro = MutableStateFlow<Int?>(null)
    val usuarioFiltro = _usuarioFiltro.asStateFlow()

    private val _crearEstado = MutableStateFlow<UiState<Post>?>(null)
    val crearEstado = _crearEstado.asStateFlow()

    init {
        cargar()
    }

    fun cargar() {
        viewModelScope.launch {
            _lista.value = UiState.Cargando
            val currentUserId = _usuarioFiltro.value
            val result = if (currentUserId == null) {
                repo.listar()
            } else {
                repo.porUsuario(currentUserId)
            }
            _lista.value = result.fold(
                onSuccess = { UiState.Exito(it) },
                onFailure = { UiState.Error(mensajeDe(it)) }
            )
        }
    }

    fun filtrar(userId: Int?) {
        _usuarioFiltro.value = userId
        cargar()
    }

    fun buscar(titulo: String) {
        _busqueda.value = titulo
    }

    fun abrir(id: Int) {
        viewModelScope.launch {
            _detalle.value = UiState.Cargando
            try {
                coroutineScope {
                    val postDeferred = async { repo.detalle(id) }
                    val comentariosDeferred = async { repo.comentarios(id) }

                    val postResult = postDeferred.await()
                    val comentariosResult = comentariosDeferred.await()

                    if (postResult.isSuccess && comentariosResult.isSuccess) {
                        _detalle.value = UiState.Exito(
                            DetalleUi(
                                post = postResult.getOrThrow(),
                                comentarios = comentariosResult.getOrThrow()
                            )
                        )
                    } else {
                        val errorThrowable = postResult.exceptionOrNull() ?: comentariosResult.exceptionOrNull() ?: Exception("Error desconocido")
                        _detalle.value = UiState.Error(mensajeDe(errorThrowable))
                    }
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _detalle.value = UiState.Error(mensajeDe(e))
            }
        }
    }

    fun publicar(titulo: String, texto: String) {
        viewModelScope.launch {
            _crearEstado.value = UiState.Cargando
            val nuevo = NuevoPost(userId = 1, title = titulo, body = texto)
            val result = repo.crear(nuevo)
            _crearEstado.value = result.fold(
                onSuccess = { UiState.Exito(it) },
                onFailure = { UiState.Error(mensajeDe(it)) }
            )
        }
    }

    fun limpiarCrearEstado() {
        _crearEstado.value = null
    }
}
