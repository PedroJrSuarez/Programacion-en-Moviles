package com.suarez.lavacampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suarez.lavacampus.data.local.relation.PedidoConDetalle
import com.suarez.lavacampus.data.repository.LavaRepository
import com.suarez.lavacampus.model.EstadoPedido
import com.suarez.lavacampus.model.estadoEnum
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class DetallePedidoViewModel(private val repo: LavaRepository, private val pedidoId: Int) : ViewModel() {

    val pedido: StateFlow<PedidoConDetalle?> = repo.observarPedido(pedidoId).aEstado(viewModelScope, null)

    /** Solo RECIBIDO (aún no empezó el lavado) */
    val puedeCancelar: StateFlow<Boolean> = pedido
        .map { it != null && it.estadoEnum() == EstadoPedido.RECIBIDO }
        .aEstado(viewModelScope, false)

    fun cancelar() {
        viewModelScope.launch {
            val p = pedido.value ?: return@launch
            if (p.estadoEnum() == EstadoPedido.RECIBIDO) {
                repo.cambiarEstado(pedidoId, EstadoPedido.CANCELADO, null)
            }
        }
    }
}
