package com.suarez.lavacampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suarez.lavacampus.data.local.entity.Usuario
import com.suarez.lavacampus.data.local.relation.PedidoConDetalle
import com.suarez.lavacampus.data.repository.LavaRepository
import com.suarez.lavacampus.model.esActivo
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map

class InicioViewModel(repo: LavaRepository, val usuario: Usuario) : ViewModel() {

    private val pedidos = repo.observarPedidosDeUsuario(usuario.id)   // ya viene del más reciente al más antiguo

    /** El más reciente en RECIBIDO, EN_PROCESO o LISTO */
    val pedidoActivo: StateFlow<PedidoConDetalle?> = pedidos
        .map { lista -> lista.firstOrNull { it.esActivo() } }
        .aEstado(viewModelScope, null)

    /** Los 2 últimos pedidos */
    val recientes: StateFlow<List<PedidoConDetalle>> = pedidos
        .map { it.take(2) }
        .aEstado(viewModelScope, emptyList())
}
