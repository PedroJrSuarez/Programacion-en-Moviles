package com.suarez.proyectomatricula

fun main() {

    print("Ingrese el nombre del estudiante: ")
    val nombreEstudiante = readln()

    print("Ingrese la cantidad de cursos a matricular: ")
    val cantidadCursos = readln().toIntOrNull() ?: 0

    print("Ingrese el valor de cada crédito (S/): ")
    val valorCredito = readln().toDoubleOrNull() ?: 0.0

    data class Curso(val nombre: String, val creditos: Int, val costo: Double)
    val listaCursos = mutableListOf<Curso>()

    for (i in 1..cantidadCursos) {
        println("\n--- Curso $i ---")
        print("Nombre del curso: ")
        val nombreCurso = readln()

        print("Cantidad de créditos: ")
        val creditos = readln().toIntOrNull() ?: 0

        val costoCurso = creditos * valorCredito
        listaCursos.add(Curso(nombreCurso, creditos, costoCurso))
    }

    val totalCreditos = listaCursos.sumOf { it.creditos }
    val totalAPagar = totalCreditos * valorCredito

    val cargaAcademica = when {
        totalCreditos <= 12 -> "Malla regular"
        totalCreditos in 13..18 -> "Carga completa"
        else -> "Requiere autorización"
    }

    val numCuotas = if (totalAPagar > 2500) 3 else 2
    val montoPorCuota = totalAPagar / numCuotas
    val formaPago = "$numCuotas cuotas de S/ %.2f cada una".format(montoPorCuota)

    println("\n========================================")
    println("RESULTADO FINAL")
    println("ESTUDIANTE: $nombreEstudiante")
    println("========================================")
    println("%-20s | %-8s | %-10s".format("curso", "creditos", "costo"))
    println("----------------------------------------")

    for (curso in listaCursos) {
        println("%-20s | %-8d | S/ %-8.2f".format(curso.nombre, curso.creditos, curso.costo))
    }

    println("----------------------------------------")
    println("CURSO MATRICULADO : ${listaCursos.size}")
    println("TOTAL DE CREDITO  : $totalCreditos")
    println("TOTAL A PAGAR     : S/ %.2f".format(totalAPagar))
    println("Carga Academica   : $cargaAcademica")
    println("Forma de pago     : $formaPago")
    println("=========================================")

}