package com.suarez.lavacampus.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.suarez.lavacampus.data.local.dao.DetalleDao
import com.suarez.lavacampus.data.local.dao.PedidoDao
import com.suarez.lavacampus.data.local.dao.ServicioDao
import com.suarez.lavacampus.data.local.dao.UsuarioDao
import com.suarez.lavacampus.data.local.entity.DetallePedido
import com.suarez.lavacampus.data.local.entity.Pedido
import com.suarez.lavacampus.data.local.entity.Servicio
import com.suarez.lavacampus.data.local.entity.Usuario

@Database(
    entities = [Servicio::class, Usuario::class, Pedido::class, DetallePedido::class],
    version = 2,
    exportSchema = false
)
abstract class LavaDatabase : RoomDatabase() {
    abstract fun servicioDao(): ServicioDao
    abstract fun usuarioDao(): UsuarioDao
    abstract fun pedidoDao(): PedidoDao
    abstract fun detalleDao(): DetalleDao

    companion object {
        @Volatile private var INSTANCE: LavaDatabase? = null

        fun obtener(context: Context): LavaDatabase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                LavaDatabase::class.java,
                "lavacampus.db"
            ).addMigrations(MIGRACION_1_2).build().also { INSTANCE = it }
        }
    }
}

/** v1 -> v2: la tabla pedidos recibe la columna descuento (los pedidos antiguos quedan con 0.0) */
val MIGRACION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE pedidos ADD COLUMN descuento REAL NOT NULL DEFAULT 0.0")
    }
}
