package com.tecsup.centrotareas.data

import kotlinx.coroutines.flow.Flow

interface TareasRepository {
    suspend fun contarPrimos(hasta: Int): Int
    suspend fun cargarPerfil(): String
    suspend fun cargarPedidos(simularError: Boolean): List<String>

    /** Emite el progreso de 0.01f a 1f. Corre en segundo plano (flowOn Default). */
    fun tareaConProgreso(): Flow<Float>
}
