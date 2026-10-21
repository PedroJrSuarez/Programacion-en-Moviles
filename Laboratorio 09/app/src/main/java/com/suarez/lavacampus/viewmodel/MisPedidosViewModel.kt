package com.suarez.lavacampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suarez.lavacampus.data.local.relation.PedidoConDetalle
import com.suarez.lavacampus.data.repository.LavaRepository
import com.suarez.lavacampus.model.EstadoPedido
import com.suarez.lavacampus.model.estadoEnum
import com.suarez.lavacampus.model.esActivo
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MisPedidosViewModel(private val repo: LavaRepository, usuarioId: Int) : ViewModel() {

    private val pedidos = repo.observarPedidosDeUsuario(usuarioId)

    val activos: StateFlow<List<PedidoConDetalle>> = pedidos
        .map { l -> l.filter { it.esActivo() } }.aEstado(viewModelScope, emptyList())

    val historial: StateFlow<List<PedidoConDetalle>> = pedidos
        .map { l -> l.filter { !it.esActivo() } }.aEstado(viewModelScope, emptyList())

    val kilosLavados: StateFlow<Double> = pedidos.map { l ->
        l.filter { it.estadoEnum() == EstadoPedido.ENTREGADO }
            .flatMap { it.detalles }
            .filter { it.servicio.unidad == "KG" }
            .sumOf { it.detalle.cantidad }
    }.aEstado(viewModelScope, 0.0)

    fun cancelar(pedido: PedidoConDetalle) {
        if (pedido.estadoEnum() != EstadoPedido.RECIBIDO) return
        viewModelScope.launch { repo.cambiarEstado(pedido.pedido.id, EstadoPedido.CANCELADO, null) }
    }
}
