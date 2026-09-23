package com.suarez.tecsupfit.data

data class ClaseGimnasio(
    val id: Int,
    val nombre: String,
    val horario: String,
    val sala: String,
    val duracion: String,
    val descripcion: String,
    val cuposDisponibles: Int,
    val cuposTotales: Int,
    val dia: String
)

data class ReservaClase(
    val clase: ClaseGimnasio,
    val cuando: String,
    val estado: String
)


val listaClases = listOf(
    ClaseGimnasio(
        id = 1,
        nombre = "Yoga funcional",
        horario = "7:00 am",
        sala = "Sala 2",
        duracion = "50 min",
        descripcion = "Sesión de movilidad y respiración para empezar el día con energía.",
        cuposDisponibles = 6,
        cuposTotales = 12,
        dia = "Hoy"
    ),
    ClaseGimnasio(
        id = 2,
        nombre = "Cross Training",
        horario = "6:00 pm",
        sala = "Sala 1",
        duracion = "45 min",
        descripcion = "Entrenamiento funcional de alta intensidad. Cupos limitados.",
        cuposDisponibles = 8,
        cuposTotales = 12,
        dia = "Hoy"
    ),
    ClaseGimnasio(
        id = 3,
        nombre = "Spinning",
        horario = "7:30 pm",
        sala = "Sala 3",
        duracion = "40 min",
        descripcion = "Rutina cardiovascular sobre bicicleta estática al ritmo de la música.",
        cuposDisponibles = 5,
        cuposTotales = 15,
        dia = "Hoy"
    ),
    ClaseGimnasio(
        id = 4,
        nombre = "Pilates",
        horario = "8:00 am",
        sala = "Sala 2",
        duracion = "50 min",
        descripcion = "Fortalecimiento del core y mejora de la postura.",
        cuposDisponibles = 4,
        cuposTotales = 10,
        dia = "Esta semana"
    ),
    ClaseGimnasio(
        id = 5,
        nombre = "Zumba",
        horario = "5:00 pm",
        sala = "Sala 1",
        duracion = "45 min",
        descripcion = "Clase de baile y cardio para todos los niveles.",
        cuposDisponibles = 10,
        cuposTotales = 20,
        dia = "Esta semana"
    )
)
