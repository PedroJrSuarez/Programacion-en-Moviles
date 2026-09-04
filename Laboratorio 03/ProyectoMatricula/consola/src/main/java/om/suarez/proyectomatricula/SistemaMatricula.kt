package com.suarez.proyectomatricula

fun main() {

    print("Ingrese el nombre del estudiante: ")
    val nombreEstudiante = readln()

    println("\nSeleccione el turno:")
    println("1. Mañana (+10%)")
    println("2. Tarde (+15%)")
    println("3. Noche (+20%)")
    print("Opción: ")
    val opcionTurno = readln().toIntOrNull() ?: 1

    val (turno, recargoPorcentaje) = when (opcionTurno) {
        1 -> "Mañana" to 0.10
        2 -> "Tarde" to 0.15
        3 -> "Noche" to 0.20
        else -> "Mañana" to 0.10
    }

    println("\nSeleccione la categoría:")
    println("1. Ordinario")
    println("2. Becario")
    print("Opción: ")
    val opcionCategoria = readln().toIntOrNull() ?: 1

    val (categoria, costoMatricula) = if (opcionCategoria == 2) {
        "Becario" to 0.0
    } else {
        print("Ingrese el precio de la matrícula (S/): ")
        val precio = readln().toDoubleOrNull() ?: 0.0
        "Ordinario" to precio
    }

    print("\nIngrese la cantidad de cursos a matricular: ")
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
    val subtotalCreditos = totalCreditos * valorCredito
    val montoRecargo = subtotalCreditos * recargoPorcentaje
    val totalAPagar = subtotalCreditos + montoRecargo + costoMatricula

    val cargaAcademica = when {
        totalCreditos <= 12 -> "Malla regular"
        totalCreditos in 13..18 -> "Carga completa"
        else -> "Requiere autorización"
    }

    val numCuotas = if (totalAPagar > 2500) 3 else 2
    val montoPorCuota = totalAPagar / numCuotas
    val formaPago = String.format("%d cuotas de S/ %.2f cada una", numCuotas, montoPorCuota)

    val porcentajeTexto = (recargoPorcentaje * 100).toInt()

    println("\n========================================")
    println("RESULTADO FINAL")
    println("ESTUDIANTE: $nombreEstudiante")
    println("TURNO     : $turno (+$porcentajeTexto%)")
    println("CATEGORÍA : $categoria")
    println("========================================")
    println("%-20s | %-8s | %-10s".format("curso", "creditos", "costo"))
    println("----------------------------------------")

    for (curso in listaCursos) {
        println("%-20s | %-8d | S/ %-8.2f".format(curso.nombre, curso.creditos, curso.costo))
    }

    println("----------------------------------------")
    println("CURSO MATRICULADO : ${listaCursos.size}")
    println("TOTAL DE CREDITO  : $totalCreditos")
    println("SUBTOTAL CREDITOS : S/ %.2f".format(subtotalCreditos))
    println("RECARGO TURNO     : S/ %.2f".format(montoRecargo))
    println("MATRÍCULA         : S/ %.2f".format(costoMatricula))
    println("TOTAL A PAGAR     : S/ %.2f".format(totalAPagar))
    println("Carga Academica   : $cargaAcademica")
    println("Forma de pago     : $formaPago")
    println("========================================")
}