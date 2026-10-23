package com.suarez.lavacampus.viewmodel

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suarez.lavacampus.data.repository.LavaRepository
import com.suarez.lavacampus.model.EstadoPedido
import com.suarez.lavacampus.model.texto
import com.suarez.lavacampus.ui.components.graficos.DatoGrafico
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters

class PanelViewModel(repo: LavaRepository) : ViewModel() {

    private val hoy = LocalDate.now().toString()
    // Lunes de esta semana (si hoy es domingo, el lunes anterior)
    private val inicioSemana = LocalDate.now()
        .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).toString()

    val pedidosHoy: StateFlow<Int> = repo.observarPedidosDeFecha(hoy).aEstado(viewModelScope, 0)
    val enProceso: StateFlow<Int> = repo.observarCantidadPorEstado(EstadoPedido.EN_PROCESO).aEstado(viewModelScope, 0)
    val porRecoger: StateFlow<Int> = repo.observarCantidadPorEstado(EstadoPedido.LISTO).aEstado(viewModelScope, 0)
    val ingresosSemana: StateFlow<Double> = repo.observarIngresosDesde(inicioSemana).aEstado(viewModelScope, 0.0)

    /** Cantidad de pedidos atrasados (entrega vencida y no finalizados) */
    val atrasados: StateFlow<Int> = repo.observarIdsAtrasados(hoy).map { it.size }.aEstado(viewModelScope, 0)

    // ADAPTAR: si DatoGrafico del proyecto base tiene otros nombres/tipos, ajústalo aquí
    val porEstado: StateFlow<List<DatoGrafico>> = combine(
        repo.observarCantidadPorEstado(EstadoPedido.RECIBIDO),
        repo.observarCantidadPorEstado(EstadoPedido.EN_PROCESO),
        repo.observarCantidadPorEstado(EstadoPedido.LISTO),
        repo.observarCantidadPorEstado(EstadoPedido.ENTREGADO)
    ) { r, p, l, e ->
        listOf(
            EstadoPedido.RECIBIDO to r, EstadoPedido.EN_PROCESO to p,
            EstadoPedido.LISTO to l, EstadoPedido.ENTREGADO to e
        ).filter { it.second > 0 }
            .map { (estado, n) -> DatoGrafico(estado.texto(), n.toFloat(), estado.color) }
    }.aEstado(viewModelScope, emptyList())

    private val paleta = listOf(Color(0xFF6D28D9), Color(0xFF8B5CF6), Color(0xFFA78BFA), Color(0xFF7C3AED))

    val porServicio: StateFlow<List<DatoGrafico>> = repo.observarIngresosPorServicio(inicioSemana)
        .map { lista ->
            lista.mapIndexed { i, s -> DatoGrafico(s.nombre, s.total.toFloat(), paleta[i % paleta.size]) }
        }.aEstado(viewModelScope, emptyList())
}
