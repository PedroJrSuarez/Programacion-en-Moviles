package com.suarez.proyectomatricula

import java.util.Scanner

fun main() {
    val scanner = Scanner(System.`in`)

    print("Ingrese el nombre del estudiante: ")
    val nombreEstudiante = scanner.nextLine()

    print("Ingrese la cantidad de cursos a matricular: ")
    val cantidadCursos = scanner.nextInt()

    print("Ingrese el valor de cada crédito (S/): ")
    val valorCredito = scanner.nextDouble()

    data class Curso(val nombre: String, val creditos: Int, val costo: Double)
    val listaCursos = mutableListOf<Curso>()

    for (i in 1..cantidadCursos) {
        println("\n--- Curso $i ---")
        print("Nombre del curso: ")
        val nombreCurso = scanner.next()

        print("Cantidad de créditos: ")
        val creditos = scanner.nextInt()

        val costoCurso = creditos * valorCredito
        listaCursos.add(Curso(nombreCurso, creditos, costoCurso))
    }
}