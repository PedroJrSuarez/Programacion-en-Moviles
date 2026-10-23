package com.suarez.lavacampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suarez.lavacampus.data.local.relation.PedidoConDetalle
import com.suarez.lavacampus.data.repository.LavaRepository
import com.suarez.lavacampus.model.EstadoPedido
import com.suarez.lavacampus.model.estadoEnum
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

enum class FiltroBandeja(val texto: String, val estado: EstadoPedido?) {
    TODOS("Todos", null),
    RECIBIDO("Recibido", EstadoPedido.RECIBIDO),
    EN_PROCESO("En proceso", EstadoPedido.EN_PROCESO),
    LISTO("Listo", EstadoPedido.LISTO)
}

class BandejaViewModel(private val repo: LavaRepository) : ViewModel() {

    private val _filtro = MutableStateFlow(FiltroBandeja.TODOS)
    val filtro: StateFlow<FiltroBandeja> = _filtro.asStateFlow()

    private val _busqueda = MutableStateFlow("")
    val busqueda: StateFlow<String> = _busqueda.asStateFlow()

    val pedidos: StateFlow<List<PedidoConDetalle>> =
        combine(repo.observarPedidos(), _filtro, _busqueda) { lista, filtro, texto ->
            lista.filter { p ->
                (filtro.estado == null || p.estadoEnum() == filtro.estado) &&
                    (texto.isBlank() ||
                        p.usuario.nombre.contains(texto.trim(), ignoreCase = true) ||
                        p.pedido.id.toString().contains(texto.trim()))
            }
        }.aEstado(viewModelScope, emptyList())

    fun elegirFiltro(f: FiltroBandeja) { _filtro.value = f }
    fun buscar(texto: String) { _busqueda.value = texto }

    fun iniciar(p: PedidoConDetalle) {
        if (p.estadoEnum() != EstadoPedido.RECIBIDO) return
        viewModelScope.launch { repo.cambiarEstado(p.pedido.id, EstadoPedido.EN_PROCESO, null) }
    }
}
