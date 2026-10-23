package com.suarez.lavacampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suarez.lavacampus.data.local.entity.Servicio
import com.suarez.lavacampus.data.local.entity.Usuario
import com.suarez.lavacampus.data.prefs.PreferenciasStore
import com.suarez.lavacampus.data.repository.LavaRepository
import com.suarez.lavacampus.model.LineaPedido
import com.suarez.lavacampus.model.calcularFechaEntrega
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

data class LineaResumen(val servicio: Servicio, val cantidad: Double, val subtotal: Double)

class NuevoPedidoViewModel(
    private val repo: LavaRepository,
    prefs: PreferenciasStore,
    private val usuario: Usuario
) : ViewModel() {

    companion object {
        const val MAX_ACTIVOS = 3
    }

    val servicios: StateFlow<List<Servicio>> = repo.observarServiciosActivos()
        .aEstado(viewModelScope, emptyList())

    private val _cantidades = MutableStateFlow<Map<Int, Double>>(emptyMap())
    val cantidades: StateFlow<Map<Int, Double>> = _cantidades.asStateFlow()

    private val _nota = MutableStateFlow("")
    val nota: StateFlow<String> = _nota.asStateFlow()

    private val _mensaje = MutableStateFlow<String?>(null)
    val mensaje: StateFlow<String?> = _mensaje.asStateFlow()

    val recibiendoPedidos: StateFlow<Boolean> = prefs.recibirPedidos.aEstado(viewModelScope, true)

    val resumen: StateFlow<List<LineaResumen>> = combine(servicios, cantidades) { lista, cant ->
        lista.mapNotNull { s ->
            val c = cant[s.id] ?: 0.0
            if (c > 0) LineaResumen(s, c, c * s.precio) else null
        }
    }.aEstado(viewModelScope, emptyList())

    val total: StateFlow<Double> = resumen.map { l -> l.sumOf { it.subtotal } }.aEstado(viewModelScope, 0.0)

    val fechaEntrega: StateFlow<String?> = resumen.map { l ->
        if (l.isEmpty()) null else calcularFechaEntrega(l.maxOf { it.servicio.horasEntrega }).toString()
    }.aEstado(viewModelScope, null)

    val puedeConfirmar: StateFlow<Boolean> = combine(resumen, recibiendoPedidos) { l, recibe ->
        l.isNotEmpty() && recibe
    }.aEstado(viewModelScope, false)

    fun cambiarNota(texto: String) { _nota.value = texto }

    /** delta > 0 suma un paso, delta < 0 resta un paso. Con 0 el servicio queda apagado. */
    fun cambiarCantidad(servicioId: Int, delta: Int) {
        val s = servicios.value.firstOrNull { it.id == servicioId } ?: return
        val actual = _cantidades.value[servicioId] ?: 0.0
        val nuevo = if (s.unidad == "KG") {
            when {
                delta > 0 && actual == 0.0 -> 1.0                       // mínimo 1.0 al activar
                delta > 0 -> minOf(actual + 0.5, 10.0)
                actual - 0.5 < 1.0 -> 0.0                               // por debajo de 1.0 se apaga
                else -> actual - 0.5
            }
        } else {
            if (delta > 0) minOf(actual + 1, 30.0) else maxOf(actual - 1, 0.0)
        }
        _cantidades.value = if (nuevo <= 0.0) _cantidades.value - servicioId
        else _cantidades.value + (servicioId to nuevo)
        _mensaje.value = null
    }

    fun confirmar(onCreado: () -> Unit) {
        val lineas = resumen.value
        val fecha = fechaEntrega.value
        if (lineas.isEmpty() || fecha == null) return
        viewModelScope.launch {
            if (!recibiendoPedidos.value) {
                _mensaje.value = "La lavandería no está recibiendo pedidos"
                return@launch
            }
            if (repo.contarActivos(usuario.id) >= MAX_ACTIVOS) {
                _mensaje.value = "Ya tienes 3 pedidos activos"
                return@launch
            }
            repo.crearPedido(
                usuarioId = usuario.id,
                lineas = lineas.map { LineaPedido(it.servicio.id, it.cantidad) },
                fechaEntrega = fecha,
                nota = _nota.value.trim()
            )
            onCreado()
        }
    }
}
