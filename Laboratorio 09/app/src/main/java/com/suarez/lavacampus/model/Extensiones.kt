package com.suarez.lavacampus.model

import com.suarez.lavacampus.data.local.entity.Servicio
import com.suarez.lavacampus.data.local.relation.PedidoConDetalle
import java.time.DayOfWeek
import java.time.LocalDate
import java.util.Locale
import kotlin.math.ceil

fun Double.soles(): String = String.format(Locale.US, "S/ %.2f", this)

fun Double.cantidadTexto(unidad: String): String =
    if (unidad == "KG") String.format(Locale.US, "%.1f kg", this)
    else "${this.toInt()} ${if (unidad == "PRENDA") "prendas" else "uds"}"

fun Servicio.textoPrecio(): String {
    val u = when (unidad) { "KG" -> "por kg"; "PRENDA" -> "por prenda"; else -> "por unidad" }
    return "${precio.soles()} $u"
}

fun PedidoConDetalle.estadoEnum(): EstadoPedido = EstadoPedido.valueOf(pedido.estado)

val ESTADOS_ACTIVOS = setOf(EstadoPedido.RECIBIDO, EstadoPedido.EN_PROCESO, EstadoPedido.LISTO)
fun PedidoConDetalle.esActivo(): Boolean = estadoEnum() in ESTADOS_ACTIVOS

fun EstadoPedido.proximo(): EstadoPedido? = when (this) {
    EstadoPedido.RECIBIDO -> EstadoPedido.EN_PROCESO
    EstadoPedido.EN_PROCESO -> EstadoPedido.LISTO
    EstadoPedido.LISTO -> EstadoPedido.ENTREGADO
    else -> null
}

fun EstadoPedido.texto(): String = when (this) {
    EstadoPedido.RECIBIDO -> "Recibido"
    EstadoPedido.EN_PROCESO -> "En proceso"
    EstadoPedido.LISTO -> "Listo"
    EstadoPedido.ENTREGADO -> "Entregado"
    EstadoPedido.CANCELADO -> "Cancelado"
}

fun calcularFechaEntrega(horas: Int): LocalDate {
    val dias = ceil(horas / 24.0).toLong()
    var f = LocalDate.now().plusDays(dias)
    if (f.dayOfWeek == DayOfWeek.SUNDAY) f = f.plusDays(1)
    return f
}
