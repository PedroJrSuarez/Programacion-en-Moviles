package com.suarez.lavacampus.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.suarez.lavacampus.data.local.entity.DetallePedido
import com.suarez.lavacampus.model.IngresoServicio
import kotlinx.coroutines.flow.Flow

@Dao
interface DetalleDao {
    @Insert
    suspend fun insertarTodos(lista: List<DetallePedido>)

    @Query("SELECT COUNT(*) FROM detalles_pedido WHERE servicioId = :servicioId")
    suspend fun contarPorServicio(servicioId: Int): Int

    // Ingreso NETO por servicio: el subtotal se prorratea con el descuento del pedido (total / (total + descuento))
    // OJO: alias deben coincidir con model/IngresoServicio (nombre, total)
    @Query(
        """
        SELECT s.nombre AS nombre, SUM(d.subtotal * p.total / (p.total + p.descuento)) AS total
        FROM detalles_pedido d
        INNER JOIN servicios s ON d.servicioId = s.id
        INNER JOIN pedidos p ON d.pedidoId = p.id
        WHERE p.pagado = 1 AND p.fechaRegistro >= :fechaInicio
        GROUP BY s.id
        """
    )
    fun observarIngresosPorServicio(fechaInicio: String): Flow<List<IngresoServicio>>
}
