package com.suarez.lavacampus.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "pedidos",
    foreignKeys = [
        ForeignKey(
            entity = Usuario::class,
            parentColumns = ["id"],
            childColumns = ["usuarioId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("usuarioId"), Index("estado")]
)
data class Pedido(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val usuarioId: Int,
    val fechaRegistro: String,          // aaaa-MM-dd
    val fechaEntrega: String,           // aaaa-MM-dd
    val estado: String,                 // EstadoPedido.name
    val total: Double,                  // total FINAL (ya con descuento)
    @ColumnInfo(defaultValue = "0.0") val descuento: Double = 0.0,   // monto descontado por volumen
    val casillero: String? = null,      // nulo hasta estar LISTO
    val pagado: Boolean = false,
    val nota: String = "",
    val fechaCreacion: Long = System.currentTimeMillis()
)
