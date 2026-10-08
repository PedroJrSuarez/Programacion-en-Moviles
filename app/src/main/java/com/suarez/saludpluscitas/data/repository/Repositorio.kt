package com.suarez.saludpluscitas.data.repository

import com.suarez.saludpluscitas.data.model.*

object Repositorio {

    // ---- Colecciones ----
    private val usuarios = mutableListOf(
        Usuario(1, "Pedro Suarez", "pedro.suarez@tecsup.edu.pe", "123456", "987654321")
    )
    var usuarioActual: Usuario? = null
        private set

    private val especialidades = listOf(
        Especialidad(1, "Medicina General", "Atención integral"),
        Especialidad(2, "Pediatría", "Niños y adolescentes"),
        Especialidad(3, "Ginecología", "Salud de la mujer"),
        Especialidad(4, "Cardiología", "Corazón y vasos sanguíneos"),
        Especialidad(5, "Dermatología", "Piel, cabello y uñas"),
        Especialidad(6, "Traumatología", "Huesos y articulaciones"),
        Especialidad(7, "Oftalmología", "Salud visual"),
        Especialidad(8, "Odontología", "Salud bucal")
    )

    private val medicos = listOf(
        Medico(1, "Dra. Ana Torres", 3, 4.9, 124, "Disponible hoy"),
        Medico(2, "Dra. Claudia Rojas", 3, 4.8, 98, "Disponible mañana"),
        Medico(3, "Dr. Luis Ramírez", 3, 4.7, 85, "Disponible hoy"),
        Medico(4, "Dra. Mariana Soto", 3, 4.6, 72, "Disponible esta semana"),
        Medico(5, "Dr. Carlos Mendoza", 1, 4.8, 156, "Disponible hoy"),
        Medico(6, "Dra. Lucía Paredes", 1, 4.7, 110, "Disponible mañana"),
        Medico(7, "Dr. Jorge Salas", 1, 4.5, 64, "Disponible esta semana"),
        Medico(8, "Dra. Sofía Quispe", 2, 4.9, 142, "Disponible hoy"),
        Medico(9, "Dr. Andrés Ríos", 2, 4.6, 77, "Disponible mañana"),
        Medico(10, "Dr. Raúl Vega", 4, 4.9, 131, "Disponible hoy"),
        Medico(11, "Dra. Marta Campos", 4, 4.7, 90, "Disponible esta semana"),
        Medico(12, "Dra. Elena Campos", 5, 4.8, 105, "Disponible hoy"),
        Medico(13, "Dr. Pablo Lazo", 5, 4.5, 58, "Disponible mañana"),
        Medico(14, "Dr. Andrés Herrera", 6, 4.6, 69, "Disponible hoy"),
        Medico(15, "Dra. Patricia Lazo", 7, 4.8, 93, "Disponible mañana"),
        Medico(16, "Dr. Raúl Mendoza", 8, 4.6, 88, "Disponible hoy")
    )

    private val horariosBase = listOf(
        "08:00", "08:30", "09:00", "09:30", "10:00", "10:30", "11:00", "11:30", "12:00",
        "15:00", "15:30", "16:00", "16:30", "17:00", "17:30"
    )

    private val citas = mutableListOf<Cita>()

    // ---- Usuarios ----
    fun registrarUsuario(nombre: String, correo: String, clave: String, telefono: String): Boolean {
        val yaExiste = usuarios.any {
            it.telefono == telefono || (correo.isNotBlank() && it.correo.equals(correo, ignoreCase = true))
        }
        if (yaExiste) return false
        val nuevo = Usuario(usuarios.size + 1, nombre, correo, clave, telefono)
        usuarios.add(nuevo)
        usuarioActual = nuevo
        return true
    }

    /** [correo] acepta correo o teléfono. */
    fun iniciarSesion(correo: String, clave: String): Boolean {
        val id = correo.trim()
        val u = usuarios.find {
            (it.correo.equals(id, ignoreCase = true) || it.telefono == id) && it.clave == clave
        }
        usuarioActual = u
        return u != null
    }

    fun cerrarSesion() { usuarioActual = null }

    // ---- Especialidades ----
    fun buscarEspecialidades(texto: String): List<Especialidad> =
        especialidades.filter { it.nombre.contains(texto.trim(), ignoreCase = true) }

    fun especialidadesDestacadas(cantidad: Int = 3): List<Especialidad> =
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
