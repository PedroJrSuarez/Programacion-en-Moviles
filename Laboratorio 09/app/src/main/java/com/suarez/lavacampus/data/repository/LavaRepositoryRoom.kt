package com.suarez.lavacampus.data.repository

import androidx.room.withTransaction
import com.suarez.lavacampus.data.local.LavaDatabase
import com.suarez.lavacampus.data.local.entity.DetallePedido
import com.suarez.lavacampus.data.local.entity.Pedido
import com.suarez.lavacampus.data.local.entity.Servicio
import com.suarez.lavacampus.data.local.entity.Usuario
import com.suarez.lavacampus.data.local.relation.PedidoConDetalle
import com.suarez.lavacampus.model.ClienteResumen
import com.suarez.lavacampus.model.EstadoPedido
import com.suarez.lavacampus.model.IngresoServicio
import com.suarez.lavacampus.model.LineaPedido
import com.suarez.lavacampus.model.Rol
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class LavaRepositoryRoom(private val db: LavaDatabase) : LavaRepository {

    private val servicioDao = db.servicioDao()
    private val usuarioDao = db.usuarioDao()
    private val pedidoDao = db.pedidoDao()
    private val detalleDao = db.detalleDao()

    // ---------- Usuarios ----------
    override suspend fun login(correo: String, clave: String): Usuario? = usuarioDao.login(correo, clave)
    override fun observarClientes(): Flow<List<ClienteResumen>> = usuarioDao.observarClientes()
    override suspend fun obtenerUsuario(id: Int): Usuario? = usuarioDao.obtener(id)
    override suspend fun usuarioPorRol(rol: Rol): Usuario? = usuarioDao.primeroPorRol(rol.name)

    // ---------- Servicios ----------
    override fun observarServicios(): Flow<List<Servicio>> = servicioDao.observarTodos()
    override fun observarServiciosActivos(): Flow<List<Servicio>> = servicioDao.observarActivos()

    override suspend fun guardarServicio(s: Servicio) {
        // Nuevo (id = 0) -> insertar; existente -> actualizar (REPLACE rompería las FK de los detalles)
        if (s.id == 0) servicioDao.insertar(s) else servicioDao.actualizar(s)
    }

    override suspend fun eliminarServicio(s: Servicio) = servicioDao.eliminar(s)
    override suspend fun cambiarActivo(id: Int, activo: Boolean) = servicioDao.cambiarActivo(id, activo)
    override suspend fun servicioTieneDetalles(id: Int): Boolean = detalleDao.contarPorServicio(id) > 0

    // ---------- Pedidos ----------
    override suspend fun crearPedido(
        usuarioId: Int, lineas: List<LineaPedido>, fechaEntrega: String, nota: String
    ): Long = db.withTransaction {
        // 1) Copiamos el precio vigente de cada servicio (precio congelado)
        val items = lineas.map { l ->
            val s = servicioDao.obtener(l.servicioId)
                ?: throw IllegalStateException("El servicio ${l.servicioId} no existe")
            Triple(s, l.cantidad, s.precio * l.cantidad)
        }
        val total = items.sumOf { it.third }

        // 2) Pedido
        val pedidoId = pedidoDao.insertar(
            Pedido(
                usuarioId = usuarioId,
                fechaRegistro = LocalDate.now().toString(),
                fechaEntrega = fechaEntrega,
                estado = EstadoPedido.RECIBIDO.name,
                total = total,
                pagado = false,
                nota = nota
            )
        ).toInt()

        // 3) Detalles (si algo falla, se deshace también el pedido)
        detalleDao.insertarTodos(
            items.map { (s, cantidad, subtotal) ->
                DetallePedido(
                    pedidoId = pedidoId, servicioId = s.id, cantidad = cantidad,
                    precioUnitario = s.precio, subtotal = subtotal
                )
            }
        )
        pedidoId.toLong()
    }

    override suspend fun contarActivos(usuarioId: Int): Int = pedidoDao.contarActivos(usuarioId)
    override fun observarPedidosDeUsuario(usuarioId: Int): Flow<List<PedidoConDetalle>> = pedidoDao.observarPorUsuario(usuarioId)
    override fun observarPedidos(): Flow<List<PedidoConDetalle>> = pedidoDao.observarTodos()
    override fun observarPedido(id: Int): Flow<PedidoConDetalle?> = pedidoDao.observarUno(id)

    // No valida reglas: viven en el ViewModel
    override suspend fun cambiarEstado(id: Int, estado: EstadoPedido, casillero: String?) =
        pedidoDao.actualizarEstado(id, estado.name, casillero)

    override suspend fun marcarPagado(id: Int, pagado: Boolean) = pedidoDao.actualizarPagado(id, pagado)
    override fun observarCasillerosOcupados(): Flow<List<String>> = pedidoDao.observarCasillerosOcupados()

    // ---------- Panel ----------
    override fun observarPedidosDeFecha(fecha: String): Flow<Int> = pedidoDao.contarPorFecha(fecha)
    override fun observarCantidadPorEstado(estado: EstadoPedido): Flow<Int> = pedidoDao.contarPorEstado(estado.name)
    override fun observarIngresosDesde(fechaInicio: String): Flow<Double> = pedidoDao.ingresosDesde(fechaInicio)
    override fun observarIngresosPorServicio(fechaInicio: String): Flow<List<IngresoServicio>> =
        detalleDao.observarIngresosPorServicio(fechaInicio)

    override fun observarIdsAtrasados(hoy: String): Flow<List<Int>> = pedidoDao.observarIdsAtrasados(hoy)
}
