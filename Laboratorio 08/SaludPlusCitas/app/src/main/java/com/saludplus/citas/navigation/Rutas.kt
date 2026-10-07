package com.saludplus.citas.navigation

object Rutas {
    const val SPLASH = "splash"
    const val REGISTRO = "registro"
    const val LOGIN = "login"
    const val TERMINOS = "terminos"
    const val HOME = "home"
    const val ESPECIALIDADES = "especialidades"
    const val MEDICOS = "medicos/{especialidadId}"
    const val FECHA_HORA = "fechaHora/{medicoId}"
    const val CONFIRMAR = "confirmar/{medicoId}/{fecha}/{hora}"
    const val EXITO = "exito/{citaId}"
    const val MIS_CITAS = "misCitas"
    const val DETALLE = "detalle/{citaId}"
    const val PERFIL = "perfil"
    const val RESULTADOS = "resultados"
    const val NOTIFICACIONES = "notificaciones"

    fun medicos(especialidadId: Int) = "medicos/$especialidadId"
    fun fechaHora(medicoId: Int) = "fechaHora/$medicoId"
    fun confirmar(medicoId: Int, fecha: String, hora: String) = "confirmar/$medicoId/$fecha/$hora"
    fun exito(citaId: Int) = "exito/$citaId"
    fun detalle(citaId: Int) = "detalle/$citaId"
}
