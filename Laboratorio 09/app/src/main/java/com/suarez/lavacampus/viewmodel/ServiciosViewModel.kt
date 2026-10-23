package com.suarez.lavacampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suarez.lavacampus.data.local.entity.Servicio
import com.suarez.lavacampus.data.repository.LavaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ServiciosViewModel(private val repo: LavaRepository) : ViewModel() {

    val servicios: StateFlow<List<Servicio>> = repo.observarServicios()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _mensaje = MutableStateFlow<String?>(null)
    val mensaje: StateFlow<String?> = _mensaje.asStateFlow()

    fun alternar(s: Servicio) {
        viewModelScope.launch { repo.cambiarActivo(s.id, !s.activo) }
    }

    /** Nuevo (id = 0) o editado */
    fun guardar(servicio: Servicio, onGuardado: () -> Unit = {}) {
        viewModelScope.launch {
            repo.guardarServicio(servicio)
            onGuardado()
        }
    }

    /** Un servicio que ya aparece en algún pedido no se elimina: solo se desactiva */
    fun eliminar(servicio: Servicio, onEliminado: () -> Unit = {}) {
        viewModelScope.launch {
            if (repo.servicioTieneDetalles(servicio.id)) {
                _mensaje.value = "No se puede eliminar: el servicio ya aparece en pedidos. Desactívalo en su lugar."
            } else {
                repo.eliminarServicio(servicio)
                _mensaje.value = null
                onEliminado()
            }
        }
    }

    fun limpiarMensaje() { _mensaje.value = null }
}
