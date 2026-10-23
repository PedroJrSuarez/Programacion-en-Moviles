package com.suarez.lavacampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suarez.lavacampus.data.repository.LavaRepository
import com.suarez.lavacampus.model.ClienteResumen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine

class ClientesViewModel(repo: LavaRepository) : ViewModel() {

    private val _busqueda = MutableStateFlow("")
    val busqueda: StateFlow<String> = _busqueda.asStateFlow()

    val clientes: StateFlow<List<ClienteResumen>> =
        combine(repo.observarClientes(), _busqueda) { lista, texto ->
            if (texto.isBlank()) lista
            else lista.filter { it.nombre.contains(texto.trim(), true) || it.codigo.contains(texto.trim(), true) }
        }.aEstado(viewModelScope, emptyList())

    fun buscar(texto: String) { _busqueda.value = texto }
}
