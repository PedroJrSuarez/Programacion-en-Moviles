package com.suarez.lavacampus.data.prefs

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.suarez.lavacampus.model.Rol
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Declarada una sola vez, a nivel de archivo
val Context.dataStore by preferencesDataStore("ajustes")

class PreferenciasStore(private val context: Context) {

    private object Claves {
        val perfilActivo = stringPreferencesKey("perfilActivo")
        val usuarioId = intPreferencesKey("usuarioId")
        val modoOscuro = booleanPreferencesKey("modoOscuro")
        val notificaciones = booleanPreferencesKey("notificaciones")
        val recibirPedidos = booleanPreferencesKey("recibirPedidos")
    }

    val perfilActivo: Flow<String> = context.dataStore.data.map { it[Claves.perfilActivo] ?: "" }
    val usuarioId: Flow<Int> = context.dataStore.data.map { it[Claves.usuarioId] ?: 0 }
    val modoOscuro: Flow<Boolean> = context.dataStore.data.map { it[Claves.modoOscuro] ?: false }
    val notificaciones: Flow<Boolean> = context.dataStore.data.map { it[Claves.notificaciones] ?: true }
    val recibirPedidos: Flow<Boolean> = context.dataStore.data.map { it[Claves.recibirPedidos] ?: true }

    suspend fun guardarSesion(rol: Rol, usuarioId: Int) {
        context.dataStore.edit {
            it[Claves.perfilActivo] = rol.name
            it[Claves.usuarioId] = usuarioId
        }
    }

    suspend fun guardarModoOscuro(b: Boolean) { context.dataStore.edit { it[Claves.modoOscuro] = b } }
    suspend fun guardarNotificaciones(b: Boolean) { context.dataStore.edit { it[Claves.notificaciones] = b } }
    suspend fun guardarRecibirPedidos(b: Boolean) { context.dataStore.edit { it[Claves.recibirPedidos] = b } }

    suspend fun cerrarSesion() {
        context.dataStore.edit {
            it.remove(Claves.perfilActivo)
            it.remove(Claves.usuarioId)
        }
    }
}
