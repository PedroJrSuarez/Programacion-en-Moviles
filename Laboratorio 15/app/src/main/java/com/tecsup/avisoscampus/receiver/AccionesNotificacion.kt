package com.tecsup.avisoscampus.receiver

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

/**
 * Puente sin Context entre los botones de la notificación y el ViewModel.
 * El receptor emite aquí; el ViewModel escucha.
 */
object AccionesNotificacion {
    private val _posponer = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    val posponer: SharedFlow<Unit> = _posponer.asSharedFlow()

    fun pedirPosponer() {
        _posponer.tryEmit(Unit)
    }
}
