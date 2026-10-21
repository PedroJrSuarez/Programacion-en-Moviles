package com.suarez.lavacampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suarez.lavacampus.data.local.relation.PedidoConDetalle
import com.suarez.lavacampus.data.repository.LavaRepository
import com.suarez.lavacampus.model.EstadoPedido
import com.suarez.lavacampus.model.estadoEnum
import com.suarez.lavacampus.model.proximo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class GestionPedidoViewModel(private val repo: LavaRepository, private val pedidoId: Int) : ViewModel() {

    private val formatoCasillero = Regex("^C-(0[1-9]|[12][0-9]|30)$")

    val pedido: StateFlow<PedidoConDetalle?> = repo.observarPedido(pedidoId).aEstado(viewModelScope, null)

    val casillerosOcupados: StateFlow<Set<String>> =
        combine(repo.observarCasillerosOcupados(), pedido) { ocupados, p ->
            ocupados.toSet() - setOfNotNull(p?.pedido?.casillero)
        }.aEstado(viewModelScope, emptySet())

    private val _casilleroElegido = MutableStateFlow<String?>(null)
    val casilleroElegido: StateFlow<String?> = _casilleroElegido.asStateFlow()

    private val _mensaje = MutableStateFlow<String?>(null)
    val mensaje: StateFlow<String?> = _mensaje.asStateFlow()

    fun elegirCasillero(c: String) {
        if (c in casillerosOcupados.value) {
            _mensaje.value = "El casillero $c ya está ocupado"
            return
        }
        _casilleroElegido.value = c
        _mensaje.value = null
    }

    fun marcarPagado(b: Boolean) {
        viewModelScope.launch { repo.marcarPagado(pedidoId, b) }
    }

    fun avanzar() {
        val p = pedido.value ?: return
        val siguiente = p.estadoEnum().proximo()
        if (siguiente == null) {
            _mensaje.value = "Este pedido ya está finalizado"
            return
        }
        viewModelScope.launch {
            when (siguiente) {
                EstadoPedido.LISTO -> {
                    val c = _casilleroElegido.value
                    when {
                        c == null -> _mensaje.value = "Elige un casillero para marcar el pedido como listo"
                        !formatoCasillero.matches(c) -> _mensaje.value = "Casillero inválido (C-01 a C-30)"
                        c in casillerosOcupados.value -> _mensaje.value = "El casillero $c ya está ocupado"
                        else -> {
                            repo.cambiarEstado(pedidoId, EstadoPedido.LISTO, c)
                            _mensaje.value = null
                        }
                    }
                }
                EstadoPedido.ENTREGADO -> {
                    if (!p.pedido.pagado) _mensaje.value = "Marca el pedido como Cobrado antes de entregarlo"
                    else {
                        repo.cambiarEstado(pedidoId, EstadoPedido.ENTREGADO, p.pedido.casillero)
                        _mensaje.value = null
                    }
                }
                else -> {
                    repo.cambiarEstado(pedidoId, siguiente, p.pedido.casillero)
                    _mensaje.value = null
                }
            }
        }
    }

    fun cancelar() {
        val p = pedido.value ?: return
        val estado = p.estadoEnum()
        if (estado != EstadoPedido.RECIBIDO && estado != EstadoPedido.EN_PROCESO) {
            _mensaje.value = "No se puede cancelar un pedido ${if (estado == EstadoPedido.LISTO) "listo" else "finalizado"}"
            return
        }
        viewModelScope.launch {
            repo.cambiarEstado(pedidoId, EstadoPedido.CANCELADO, null)
            _mensaje.value = null
        }
    }
}
