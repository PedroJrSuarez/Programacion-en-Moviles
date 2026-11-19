package com.tecsup.centrotareas.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.centrotareas.data.TareasRepository
import com.tecsup.centrotareas.model.CargaUi
import com.tecsup.centrotareas.model.Comparacion
import com.tecsup.centrotareas.model.PrimosUi
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

class TareasViewModel(private val repo: TareasRepository) : ViewModel() {

    // ----- Tarea con progreso
    private val _mensaje = MutableStateFlow("Listo")
    val mensaje: StateFlow<String> = _mensaje.asStateFlow()

    private val _progreso = MutableStateFlow(0f)
    val progreso: StateFlow<Float> = _progreso.asStateFlow()

    private val _corriendo = MutableStateFlow(false)
    val corriendo: StateFlow<Boolean> = _corriendo.asStateFlow()

    private var trabajo: Job? = null

    // Mejora 1: el progreso llega como Flow desde el Repository
    fun tareaLarga() {
        if (trabajo?.isActive == true) return
        trabajo = viewModelScope.launch {
            _corriendo.value = true
            try {
                _progreso.value = 0f
                _mensaje.value = "Trabajando..."
                Log.d("HILO", "Antes: ${Thread.currentThread().name}")
                repo.tareaConProgreso().collect { _progreso.value = it }
                Log.d("HILO", "Despues: ${Thread.currentThread().name}")
                _mensaje.value = "Terminó"
            } finally {
                _corriendo.value = false
            }
        }
    }

    fun cancelar() {
        if (!_corriendo.value) return
        trabajo?.cancel()
        _mensaje.value = "Cancelado"
    }

    // ----- Contar primos
    private val _primos = MutableStateFlow<PrimosUi>(PrimosUi.Inicial)
    val primos: StateFlow<PrimosUi> = _primos.asStateFlow()

    private var trabajoPrimos: Job? = null

    fun contar(hasta: Int) {
        if (trabajoPrimos?.isActive == true) return
        trabajoPrimos = viewModelScope.launch {
            _primos.value = PrimosUi.Calculando
            val inicio = System.currentTimeMillis()
            try {
                val cantidad = repo.contarPrimos(hasta)
                _primos.value = PrimosUi.Listo(cantidad, System.currentTimeMillis() - inicio)
            } catch (e: CancellationException) {
                _primos.value = PrimosUi.Cancelado
                throw e
            }
        }
    }

    fun cancelarPrimos() {
        trabajoPrimos?.cancel()
    }

    // ----- Dos cargas
    private val _carga = MutableStateFlow<CargaUi>(CargaUi.Inicial)
    val carga: StateFlow<CargaUi> = _carga.asStateFlow()

    private val _simularError = MutableStateFlow(false)
    val simularError: StateFlow<Boolean> = _simularError.asStateFlow()

    // Mejora 2: número de intento actual (1 = primer intento)
    private val _intento = MutableStateFlow(1)
    val intento: StateFlow<Int> = _intento.asStateFlow()

    // Mejora 3: tiempos acumulados de cada modo
    private val _comparacion = MutableStateFlow(Comparacion())
    val comparacion: StateFlow<Comparacion> = _comparacion.asStateFlow()

    fun cambiarSimularError(valor: Boolean) {
        _simularError.value = valor
    }

    fun cargarUnaPorUna() = cargar(MODO_UNA_POR_UNA) {
        val perfil = repo.cargarPerfil()
        val pedidos = repo.cargarPedidos(_simularError.value)
        Pair(perfil, pedidos)
    }

    fun cargarALaVez() = cargar(MODO_A_LA_VEZ) {
        coroutineScope {
            val a = async { repo.cargarPerfil() }
            val b = async { repo.cargarPedidos(_simularError.value) }
            Pair(a.await(), b.await())
        }
    }

    fun limpiarComparacion() {
        _comparacion.value = Comparacion()
    }

    private fun cargar(modo: String, bloque: suspend () -> Pair<String, List<String>>) {
        if (_carga.value is CargaUi.Cargando) return
        viewModelScope.launch {
            _carga.value = CargaUi.Cargando
            _intento.value = 1
            val inicio = System.currentTimeMillis()
            try {
                val (perfil, pedidos) = conReintentos(MAX_INTENTOS, bloque)
                val ms = System.currentTimeMillis() - inicio
                _carga.value = CargaUi.Exito(modo, perfil, pedidos.size, ms)
                registrarTiempo(modo, ms)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _carga.value = CargaUi.Error("No se pudo cargar: " + e.message)
            }
        }
    }

    // Mejora 2: reintenta solo ante IOException, con espera 1 s, 2 s, 4 s...
    // La CancellationException siempre se deja pasar.
    private suspend fun <T> conReintentos(intentos: Int, bloque: suspend () -> T): T {
        var espera = 1000L
        repeat(intentos - 1) { n ->
            try {
                return bloque()
            } catch (e: CancellationException) {
                throw e
            } catch (e: IOException) {
                _intento.value = n + 2
                delay(espera)
                espera *= 2
            }
        }
        return bloque() // último intento: si falla, el error sube al catch de cargar()
    }

    private fun registrarTiempo(modo: String, ms: Long) {
        _comparacion.update { actual ->
            if (modo == MODO_UNA_POR_UNA) actual.copy(unaPorUna = actual.unaPorUna + ms)
            else actual.copy(aLaVez = actual.aLaVez + ms)
        }
    }

    private companion object {
        const val MODO_UNA_POR_UNA = "Una por una"
        const val MODO_A_LA_VEZ = "A la vez"
        const val MAX_INTENTOS = 3
    }
}
