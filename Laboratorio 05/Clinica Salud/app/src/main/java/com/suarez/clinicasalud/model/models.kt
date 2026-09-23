package com.suarez.clinicasalud.model

data class Medico(
    val id: Int,
    val nombre: String,
    val especialidad: String,
    val calificacion: Double,
    val resenas: Int,
    val aniosExp: Int,
    val descripcion: String
)

enum class EstadoCita { CONFIRMADA, COMPLETADA }

data class Cita(
    val medico: Medico,
    val fecha: String,
    val hora: String,
    val estado: EstadoCita
)

val especialidades = listOf("Cardiología", "Pediatría")

val medicosDisponibles = listOf(
    Medico(
        id = 1,
        nombre = "Dra. Ana Torres",
        especialidad = "Cardióloga",
        calificacion = 4.9,
        resenas = 128,
        aniosExp = 12,
        descripcion = "Especialista en arritmias e hipertensión, formación en la Clínica Mayo."
    ),
    Medico(
        id = 2,
        nombre = "Dr. Luis Vega",
        especialidad = "Pediatra",
        calificacion = 4.7,
        resenas = 86,
        aniosExp = 8,
        descripcion = "Especialista en pediatría general y control de niño sano."
    ),
    Medico(
        id = 3,
        nombre = "Dra. Rosa Díaz",
        especialidad = "Dermatóloga",
        calificacion = 4.8,
        resenas = 101,
        aniosExp = 10,
        descripcion = "Especialista en dermatología clínica y estética."
    )
)

val fechasDisponibles = listOf(
    Pair("Jue", "26"),
    Pair("Vie", "27"),
    Pair("Sáb", "28")
)

val horasDisponibles = listOf("9:00", "10:30", "3:00")

val citasIniciales = listOf(
    Cita(medicosDisponibles[0], "Viernes 27", "10:30 am", EstadoCita.CONFIRMADA),
    Cita(medicosDisponibles[1], "Miércoles 15", "3:00 pm", EstadoCita.COMPLETADA)
)
