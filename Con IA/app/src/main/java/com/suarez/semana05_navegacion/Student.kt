package com.suarez.semana05_navegacion

data class Student(
    val id: Int,
    val name: String,
    val career: String,
    val email: String,
    val studentCode: String,
    val faculty: String,
    val biography: String
)

object StudentRepository {
    val students = listOf(
        Student(
            id = 1,
            name = "Juan León",
            career = "Ingeniería de Sistemas",
            email = "juan.leon@example.com",
            studentCode = "2024-0001",
            faculty = "Ingeniería y Tecnología",
            biography = "Estudiante destacado con interés en desarrollo Android."
        ),
        Student(
            id = 2,
            name = "María García",
            career = "Arquitectura",
            email = "maria.garcia@example.com",
            studentCode = "2024-0002",
            faculty = "Arquitectura y Urbanismo",
            biography = "Apasionada por el diseño sostenible y la arquitectura moderna."
        ),
        Student(
            id = 3,
            name = "Carlos Perez",
            career = "Medicina",
            email = "carlos.perez@example.com",
            studentCode = "2024-0003",
            faculty = "Ciencias de la Salud",
            biography = "Interesado en investigación clínica y atención primaria."
        ),
        Student(
            id = 4,
            name = "Ana Lopez",
            career = "Derecho",
            email = "ana.lopez@example.com",
            studentCode = "2024-0004",
            faculty = "Derecho y Ciencias Políticas",
            biography = "Enfocada en derecho corporativo y resolución de conflictos."
        ),
        Student(
            id = 5,
            name = "Luis Ramírez",
            career = "Administración",
            email = "luis.ramirez@example.com",
            studentCode = "2024-0005",
            faculty = "Ciencias Empresariales",
            biography = "Interesado en gestión de proyectos y liderazgo de equipos."
        )
    )

    fun getStudentById(id: Int): Student? {
        return students.find { it.id == id }
    }
}
