package com.suarez.lavacampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suarez.lavacampus.data.local.entity.Usuario
import com.suarez.lavacampus.data.prefs.PreferenciasStore
import com.suarez.lavacampus.data.repository.LavaRepository
import com.suarez.lavacampus.model.Rol
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SesionViewModel(
    private val repo: LavaRepository,
    private val prefs: PreferenciasStore
) : ViewModel() {

    private val _usuarioActual = MutableStateFlow<Usuario?>(null)
    val usuarioActual: StateFlow<Usuario?> = _usuarioActual.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    /** true cuando ya se leyó el perfil guardado (para que el Splash decida a dónde ir) */
    private val _restaurado = MutableStateFlow(false)
    val restaurado: StateFlow<Boolean> = _restaurado.asStateFlow()

    val modoOscuro = prefs.modoOscuro.stateIn(viewModelScope, SharingStarted.Eagerly, false)
    val notificaciones = prefs.notificaciones.stateIn(viewModelScope, SharingStarted.Eagerly, true)
    val recibirPedidos = prefs.recibirPedidos.stateIn(viewModelScope, SharingStarted.Eagerly, true)

    init {
        viewModelScope.launch {
            val perfil = prefs.perfilActivo.first()
            val id = prefs.usuarioId.first()
            if (perfil.isNotEmpty() && id > 0) {
                _usuarioActual.value = repo.obtenerUsuario(id)?.takeIf { it.rol == perfil }
            }
            _restaurado.value = true
        }
    }

    fun login(correo: String, clave: String, rolSeleccionado: Rol) {
        if (correo.isBlank() || clave.isBlank()) {
            _error.value = "Completa el correo y la contraseña"
            return
        }
        viewModelScope.launch {
            val u = repo.login(correo.trim(), clave)
            when {
                u == null -> _error.value = "Correo o contraseña incorrectos"
                u.rol != rolSeleccionado.name -> _error.value =
                    if (rolSeleccionado == Rol.ADMIN) "Este usuario no es administrador. Cambia a la pestaña Estudiante"
                    else "Este usuario es administrador. Cambia a la pestaña Administrador"
                else -> {
                    prefs.guardarSesion(rolSeleccionado, u.id)
                    _error.value = null
                    _usuarioActual.value = u   // NavLava navega al verlo
                }
            }
        }
    }

    fun limpiarError() { _error.value = null }

    fun cerrarSesion() {
        viewModelScope.launch {
            prefs.cerrarSesion()
            _usuarioActual.value = null
        }
    }

    /** Pasa del perfil actual al otro (estudiante <-> administrador) en el mismo dispositivo */
    fun cambiarPerfil() {
        val actual = _usuarioActual.value ?: return
        val destino = if (actual.rol == Rol.ADMIN.name) Rol.CLIENTE else Rol.ADMIN
        viewModelScope.launch {
            val u = repo.usuarioPorRol(destino) ?: return@launch
            prefs.guardarSesion(destino, u.id)
            _usuarioActual.value = u
        }
    }

    fun guardarModoOscuro(b: Boolean) { viewModelScope.launch { prefs.guardarModoOscuro(b) } }
    fun guardarNotificaciones(b: Boolean) { viewModelScope.launch { prefs.guardarNotificaciones(b) } }
    fun guardarRecibirPedidos(b: Boolean) { viewModelScope.launch { prefs.guardarRecibirPedidos(b) } }
}
