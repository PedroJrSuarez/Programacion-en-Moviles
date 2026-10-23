package com.suarez.lavacampus.data.local

import androidx.room.withTransaction
import com.suarez.lavacampus.data.local.entity.DetallePedido
import com.suarez.lavacampus.data.local.entity.Pedido
import com.suarez.lavacampus.data.local.entity.Servicio
import com.suarez.lavacampus.data.local.entity.Usuario
import com.suarez.lavacampus.model.EstadoPedido
import java.time.DayOfWeek
import java.time.LocalDate

/** Datos ficticios (Ley 29733). Se cargan solo la primera vez. */
object DatosIniciales {

    suspend fun sembrar(db: LavaDatabase) {
        if (db.usuarioDao().contar() > 0) return

        db.withTransaction {
            val lavado = Servicio(id = 1, nombre = "Lavado y secado", descripcion = "Lavado y secado por kilo",
                unidad = "KG", precio = 8.0, horasEntrega = 24, activo = true, icono = "wash")
            val planchado = Servicio(id = 2, nombre = "Planchado", descripcion = "Planchado por prenda",
                unidad = "PRENDA", precio = 2.5, horasEntrega = 48, activo = true, icono = "iron")
            val seco = Servicio(id = 3, nombre = "Lavado en seco", descripcion = "Para prendas delicadas",
                unidad = "PRENDA", precio = 12.0, horasEntrega = 48, activo = true, icono = "shirt")
            val edredon = Servicio(id = 4, nombre = "Edredón / frazada", descripcion = "Edredones y frazadas",
                unidad = "UNIDAD", precio = 20.0, horasEntrega = 48, activo = false, icono = "wash")
            db.servicioDao().insertarTodos(listOf(lavado, planchado, seco, edredon))

            db.usuarioDao().insertarTodos(
                listOf(
                    Usuario(1, "Ana Torres", "U202310234", "ana@correo.test", "1234", "CLIENTE"),
                    Usuario(2, "Luis Rojas", "U202310456", "luis@correo.test", "1234", "CLIENTE"),
                    Usuario(3, "Marta Quispe", "U202310789", "marta@correo.test", "1234", "CLIENTE"),
                    Usuario(4, "Administrador", "", "admin@lavacampus.test", "admin", "ADMIN")
                )
            )

            // Ana (1)
            pedido(db, 1, 3, 1, EstadoPedido.ENTREGADO, "C-02", true, listOf(lavado to 4.0, planchado to 6.0))
            pedido(db, 1, 1, 1, EstadoPedido.EN_PROCESO, null, false, listOf(lavado to 3.5, planchado to 5.0))
            pedido(db, 1, 0, 2, EstadoPedido.RECIBIDO, null, false, listOf(seco to 2.0))
            // Luis (2)
            pedido(db, 2, 2, 1, EstadoPedido.LISTO, "C-05", false, listOf(lavado to 5.0))
            pedido(db, 2, 4, 2, EstadoPedido.ENTREGADO, "C-09", true, listOf(lavado to 2.5, seco to 1.0))
            pedido(db, 2, 0, 2, EstadoPedido.RECIBIDO, null, false, listOf(planchado to 8.0))
            // Marta (3)
            pedido(db, 3, 2, 2, EstadoPedido.LISTO, "C-07", false, listOf(lavado to 6.0, planchado to 4.0))
            pedido(db, 3, 1, 2, EstadoPedido.EN_PROCESO, null, false, listOf(seco to 3.0))
            pedido(db, 3, 2, 2, EstadoPedido.ENTREGADO, "C-11", true, listOf(planchado to 10.0))
            pedido(db, 3, 5, 1, EstadoPedido.CANCELADO, null, false, listOf(lavado to 2.0))
        }
    }

    private suspend fun pedido(
        db: LavaDatabase, usuarioId: Int, diasAtras: Long, diasEntrega: Long,
        estado: EstadoPedido, casillero: String?, pagado: Boolean,
        lineas: List<Pair<Servicio, Double>>
    ) {
        val hoy = LocalDate.now()
        // Los pagados se registran dentro de esta semana para que cuenten en "ingresos de la semana"
        val diasDesdeLunes = (hoy.dayOfWeek.value - DayOfWeek.MONDAY.value).toLong()
        val atras = if (pagado) minOf(diasAtras, diasDesdeLunes) else diasAtras
        val registro = hoy.minusDays(atras)
        var entrega = registro.plusDays(diasEntrega)
        if (entrega.dayOfWeek == DayOfWeek.SUNDAY) entrega = entrega.plusDays(1)

        val total = lineas.sumOf { it.first.precio * it.second }
        val id = db.pedidoDao().insertar(
            Pedido(
                usuarioId = usuarioId, fechaRegistro = registro.toString(), fechaEntrega = entrega.toString(),
                estado = estado.name, total = total, casillero = casillero, pagado = pagado
            )
        ).toInt()
        db.detalleDao().insertarTodos(
            lineas.map { (s, c) ->
                DetallePedido(pedidoId = id, servicioId = s.id, cantidad = c,
                    precioUnitario = s.precio, subtotal = s.precio * c)
            }
        )
    }
}
