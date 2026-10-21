package com.suarez.lavacampus

import android.app.Application
import com.suarez.lavacampus.data.local.DatosIniciales
import com.suarez.lavacampus.data.local.LavaDatabase
import com.suarez.lavacampus.data.prefs.PreferenciasStore
import com.suarez.lavacampus.data.repository.LavaRepository
import com.suarez.lavacampus.data.repository.LavaRepositoryRoom
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class LavaApp : Application() {
    val database: LavaDatabase by lazy { LavaDatabase.obtener(this) }
    val repository: LavaRepository by lazy { LavaRepositoryRoom(database) }
    val preferencias: PreferenciasStore by lazy { PreferenciasStore(this) }

    override fun onCreate() {
        super.onCreate()
        CoroutineScope(SupervisorJob() + Dispatchers.IO).launch { DatosIniciales.sembrar(database) }
    }
}
