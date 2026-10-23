package com.suarez.lavacampus.data.repository

import com.suarez.lavacampus.data.local.entity.Servicio
import com.suarez.lavacampus.data.local.entity.Usuario
import com.suarez.lavacampus.data.local.relation.PedidoConDetalle
import com.suarez.lavacampus.model.ClienteResumen
import com.suarez.lavacampus.model.EstadoPedido
import com.suarez.lavacampus.model.IngresoServicio
import com.suarez.lavacampus.model.LineaPedido
import com.suarez.lavacampus.model.Rol
import kotlinx.coroutines.flow.Flow

interface LavaRepository {
    // Usuarios
    suspend fun login(correo: String, clave: String): Usuario?
    fun observarClientes(): Flow<List<ClienteResumen>>
    // [AGREGADAS] necesarias para restaurar la sesión guardada y para "Cambiar de perfil"
    suspend fun obtenerUsuario(id: Int): Usuario?
    suspend fun usuarioPorRol(rol: Rol): Usuario?

    // Servicios (ejemplo resuelto)
    fun observarServicios(): Flow<List<Servicio>>
    fun observarServiciosActivos(): Flow<List<Servicio>>
    suspend fun guardarServicio(s: Servicio)
    suspend fun eliminarServicio(s: Servicio)
    suspend fun cambiarActivo(id: Int, activo: Boolean)
    suspend fun servicioTieneDetalles(id: Int): Boolean

    // Pedidos
    suspend fun crearPedido(usuarioId: Int, lineas: List<LineaPedido>,
                            fechaEntrega: String, nota: String): Long
    suspend fun contarActivos(usuarioId: Int): Int
    fun observarPedidosDeUsuario(usuarioId: Int): Flow<List<PedidoConDetalle>>
    fun observarPedidos(): Flow<List<PedidoConDetalle>>
    fun observarPedido(id: Int): Flow<PedidoConDetalle?>
    suspend fun cambiarEstado(id: Int, estado: EstadoPedido, casillero: String?)
    suspend fun marcarPagado(id: Int, pagado: Boolean)
    fun observarCasillerosOcupados(): Flow<List<String>>

    // Panel
    fun observarPedidosDeFecha(fecha: String): Flow<Int>
    fun observarCantidadPorEstado(estado: EstadoPedido): Flow<Int>
    fun observarIngresosDesde(fechaInicio: String): Flow<Double>
    fun observarIngresosPorServicio(fechaInicio: String): Flow<List<IngresoServicio>>
}
