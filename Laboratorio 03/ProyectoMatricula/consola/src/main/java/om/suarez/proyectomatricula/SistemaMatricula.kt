package com.suarez.proyectomatricula

data class Curso(val nombre: String, val creditos: Int, val costo: Double)

fun leerTextoObligatorio(mensaje: String): String {
    var entrada: String
    do {
        print(mensaje)
        entrada = readln().trim()
        if (entrada.isEmpty()) {
            println("[ERROR]: Este campo es obligatorio. No puede estar vacío.")
        }
    } while (entrada.isEmpty())
    return entrada
}

fun leerEnteroValido(mensaje: String, minimo: Int = 1): Int {
    var valor: Int? = null
    do {
        print(mensaje)
        valor = readln().toIntOrNull()
        if (valor == null || valor < minimo) {
            println("[ERROR]: Debe ingresar un número entero mayor o igual a $minimo.")
        }
    } while (valor == null || valor < minimo)
    return valor
}

fun leerDecimalValido(mensaje: String, minimo: Double = 0.0): Double {
    var valor: Double? = null
    do {
        print(mensaje)
        valor = readln().toDoubleOrNull()
        if (valor == null || valor < minimo) {
            println("[ERROR]: Debe ingresar un monto numérico válido mayor o igual a $minimo.")
        }
    } while (valor == null || valor < minimo)
    return valor
}

fun leerOpcionRango(mensaje: String, min: Int, max: Int): Int {
    var opcion: Int? = null
    do {
        print(mensaje)
        opcion = readln().toIntOrNull()
        if (opcion == null || opcion !in min..max) {
            println("[ERROR]: Selección inválida. Ingrese una opción entre $min y $max.")
        }
    } while (opcion == null || opcion !in min..max)
    return opcion
}

fun main() {
    val aforoMaximo = leerEnteroValido("Ingrese la capacidad máxima del aforo: ", minimo = 1)

    var estudiantesRegistrados = 0
    var continuar = true

    while (continuar && estudiantesRegistrados < aforoMaximo) {
        estudiantesRegistrados++

        println("\n========================================")
        println("REGISTRO DE ESTUDIANTE $estudiantesRegistrados DE $aforoMaximo")
        println("========================================")

        val nombreEstudiante = leerTextoObligatorio("Ingrese el nombre del estudiante: ")

        println("\nSeleccione el turno:")
        println("1. Mañana (+10%)")
        println("2. Tarde (+15%)")
        println("3. Noche (+20%)")
        val opcionTurno = leerOpcionRango("Opción (1-3): ", 1, 3)

        val (turno, recargoPorcentaje) = when (opcionTurno) {
            1 -> "Mañana" to 0.10
            2 -> "Tarde" to 0.15
            3 -> "Noche" to 0.20
            else -> "Mañana" to 0.10
        }

        println("\nSeleccione la categoría:")
        println("1. Ordinario")
        println("2. Becario")
        val opcionCategoria = leerOpcionRango("Opción (1-2): ", 1, 2)

        val (categoria, costoMatricula) = if (opcionCategoria == 2) {
            "Becario" to 0.0
        } else {
            val precio = leerDecimalValido("Ingrese el precio de la matrícula (S/): ", minimo = 0.0)
            "Ordinario" to precio
        }

        println()
        val cantidadCursos = leerEnteroValido("Ingrese la cantidad de cursos a matricular: ", minimo = 1)
        val valorCredito = leerDecimalValido("Ingrese el valor de cada crédito (S/): ", minimo = 0.01)

        val listaCursos = mutableListOf<Curso>()

        for (i in 1..cantidadCursos) {
            println("\n--- Curso $i ---")
            val nombreCurso = leerTextoObligatorio("Nombre del curso: ")
            val creditos = leerEnteroValido("Cantidad de créditos: ", minimo = 1)

            val costoCurso = creditos * valorCredito
            listaCursos.add(Curso(nombreCurso, creditos, costoCurso))
        }

        val totalCreditos = listaCursos.sumOf { it.creditos }
        val subtotalCreditos = totalCreditos * valorCredito
        val montoRecargo = subtotalCreditos * recargoPorcentaje
        val subtotalBruto = subtotalCreditos + montoRecargo + costoMatricula

        val igv = subtotalBruto * 0.18
        val totalAPagar = subtotalBruto + igv

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
        println("AFORO: REGISTRO $estudiantesRegistrados (CAPACIDAD MÁX: $aforoMaximo)")
        println("========================================")
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
        println("SUBTOTAL NETO     : S/ %.2f".format(subtotalBruto))
        println("IGV (18%%)        : S/ %.2f".format(igv))
        println("TOTAL A PAGAR     : S/ %.2f".format(totalAPagar))
        println("Carga Academica   : $cargaAcademica")
        println("Forma de pago     : $formaPago")
        println("========================================")

        if (estudiantesRegistrados < aforoMaximo) {
            print("\n¿Desea registrar a otro estudiante? (S/N): ")
            val respuesta = readln().trim()
            continuar = respuesta.equals("S", ignoreCase = true) || respuesta.equals("SI", ignoreCase = true)
        } else {
            println("\n[AVISO]: Se ha alcanzado la capacidad máxima del aforo ($aforoMaximo estudiantes).")
        }
    }

    println("\nProceso de matrícula finalizado. Total registrados: $estudiantesRegistrados.")
}