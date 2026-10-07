package com.suarez.saludplus.data.repository

import com.suarez.saludplus.data.model.*

/** Datos en memoria (sin base de datos). */
object Repositorio {

    // ---- Colecciones ----
    private val usuarios = mutableListOf(
        Usuario(1, "Paciente Demo", "demo@saludplus.com", "123456", "999888777")
    )
    var usuarioActual: Usuario? = null
        private set

    private val especialidades = listOf(
        Especialidad(1, "Medicina General", "Consulta y control general", "🩺"),
        Especialidad(2, "Cardiología", "Salud del corazón", "❤️"),
        Especialidad(3, "Pediatría", "Atención para niños", "🧸"),
        Especialidad(4, "Dermatología", "Cuidado de la piel", "🧴"),
        Especialidad(5, "Odontología", "Salud bucal", "🦷"),
        Especialidad(6, "Oftalmología", "Salud visual", "👁️"),
        Especialidad(7, "Ginecología", "Salud de la mujer", "🌸"),
        Especialidad(8, "Traumatología", "Huesos y articulaciones", "🦴")
    )

    private val medicos = listOf(
        Medico(1, "Dra. Ana Torres", 1, 4.8, 12, 90.0),
        Medico(2, "Dr. Luis Paredes", 1, 4.5, 8, 80.0),
        Medico(3, "Dr. Carlos Rivera", 2, 4.9, 15, 150.0),
        Medico(4, "Dra. Marta Vega", 2, 4.6, 10, 140.0),
        Medico(5, "Dra. Sofía Quispe", 3, 4.9, 11, 100.0),
        Medico(6, "Dr. Jorge Salas", 3, 4.4, 6, 95.0),
        Medico(7, "Dra. Elena Campos", 4, 4.7, 9, 120.0),
        Medico(8, "Dr. Raúl Mendoza", 5, 4.6, 14, 110.0),
        Medico(9, "Dra. Patricia Lazo", 6, 4.8, 13, 130.0),
        Medico(10, "Dra. Lucía Herrera", 7, 4.9, 16, 140.0),
        Medico(11, "Dr. Andrés Ríos", 8, 4.5, 10, 135.0)
    )

    private val horariosBase = listOf(
        "08:00", "09:00", "10:00", "11:00", "12:00", "15:00", "16:00", "17:00", "18:00"
    )

    private val citas = mutableListOf<Cita>()

    // ---- Usuarios ----
    fun registrarUsuario(nombre: String, correo: String, clave: String, telefono: String): Boolean {
        if (usuarios.any { it.correo.equals(correo, ignoreCase = true) }) return false
        val nuevo = Usuario(usuarios.size + 1, nombre, correo, clave, telefono)
        usuarios.add(nuevo)
        usuarioActual = nuevo
        return true
    }

    fun iniciarSesion(correo: String, clave: String): Boolean {
        val u = usuarios.find { it.correo.equals(correo.trim(), ignoreCase = true) && it.clave == clave }
        usuarioActual = u
        return u != null
    }

    fun cerrarSesion() { usuarioActual = null }

    // ---- Especialidades ----
    fun buscarEspecialidades(texto: String): List<Especialidad> =
        especialidades.filter { it.nombre.contains(texto.trim(), ignoreCase = true) }

    fun especialidadesDestacadas(cantidad: Int = 5): List<Especialidad> =
        especialidades.take(cantidad)

    fun obtenerEspecialidad(id: Int): Especialidad? = especialidades.find { it.id == id }

    // ---- Médicos ----
    fun obtenerMedico(id: Int): Medico? = medicos.find { it.id == id }

    fun medicosPorEspecialidad(especialidadId: Int): List<Medico> =
        medicos.filter { it.especialidadId == especialidadId }
            .sortedByDescending { it.calificacion }

    fun buscarMedicos(texto: String): List<Medico> =
        medicos.filter { it.nombre.contains(texto.trim(), ignoreCase = true) }
            .sortedByDescending { it.calificacion }

    // ---- Citas ----
    fun horariosDisponibles(medicoId: Int, fecha: String): List<String> {
        val ocupados = citas.filter { it.medicoId == medicoId && it.fecha == fecha }.map { it.hora }
        return horariosBase.filter { it !in ocupados }
    }

    /** Devuelve la cita creada, o null si no hay sesión o el horario ya fue tomado. */
    fun agendarCita(medicoId: Int, fecha: String, hora: String): Cita? {
        val u = usuarioActual ?: return null
        if (citas.any { it.medicoId == medicoId && it.fecha == fecha && it.hora == hora }) return null
        val nueva = Cita((citas.maxOfOrNull { it.id } ?: 0) + 1, u.id, medicoId, fecha, hora)
        citas.add(nueva)
        return nueva
    }

    fun obtenerCita(id: Int): Cita? = citas.find { it.id == id }

    fun citasDelUsuario(): List<Cita> {
        val u = usuarioActual ?: return emptyList()
        return citas.filter { it.usuarioId == u.id }
            .sortedWith(compareBy({ it.fecha }, { it.hora }))
    }

    fun cancelarCita(id: Int): Boolean = citas.removeAll { it.id == id }
}
