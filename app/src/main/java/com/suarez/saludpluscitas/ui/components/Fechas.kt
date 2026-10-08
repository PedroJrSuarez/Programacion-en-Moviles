package com.suarez.saludpluscitas.ui.components

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters

/** Utilidades de calendario con java.time (textos en español, "setiembre" como pide el laboratorio). */
object Fechas {
    private val DIAS = mapOf(
        DayOfWeek.MONDAY to "Lunes", DayOfWeek.TUESDAY to "Martes", DayOfWeek.WEDNESDAY to "Miércoles",
        DayOfWeek.THURSDAY to "Jueves", DayOfWeek.FRIDAY to "Viernes",
        DayOfWeek.SATURDAY to "Sábado", DayOfWeek.SUNDAY to "Domingo"
    )
    private val MESES = listOf(
        "enero", "febrero", "marzo", "abril", "mayo", "junio", "julio", "agosto",
        "setiembre", "octubre", "noviembre", "diciembre"
    )

    fun esHabil(d: LocalDate) = d.dayOfWeek != DayOfWeek.SATURDAY && d.dayOfWeek != DayOfWeek.SUNDAY

    /** Si [d] cae sábado o domingo, devuelve el lunes siguiente. */
    fun primerDiaHabil(d: LocalDate): LocalDate {
        var x = d
        while (!esHabil(x)) x = x.plusDays(1)
        return x
    }

    /**
     * Semana 0: los próximos 5 días hábiles desde [hoy] (inclusive).
     * Semana n > 0: lunes a viernes de n semanas después. Nunca devuelve días pasados.
     */
    fun semana(offset: Int, hoy: LocalDate = LocalDate.now()): List<LocalDate> {
        val base = primerDiaHabil(hoy)
        val inicio = if (offset <= 0) base
        else base.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).plusWeeks(offset.toLong())
        return generateSequence(inicio) { it.plusDays(1) }.filter { esHabil(it) }.take(5).toList()
    }

    /** "Octubre 2026" (o "Octubre – Noviembre 2026" si la semana cruza de mes). */
    fun tituloMes(dias: List<LocalDate>): String {
        val a = dias.first()
        val b = dias.last()
        val mesA = MESES[a.monthValue - 1].replaceFirstChar { it.uppercase() }
        if (a.month == b.month) return "$mesA ${a.year}"
        val mesB = MESES[b.monthValue - 1].replaceFirstChar { it.uppercase() }
        return if (a.year == b.year) "$mesA – $mesB ${a.year}" else "$mesA ${a.year} – $mesB ${b.year}"
    }

    fun diaCorto(d: LocalDate) = DIAS.getValue(d.dayOfWeek).take(3)
    fun mesCorto(d: LocalDate) = MESES[d.monthValue - 1].take(3).replaceFirstChar { it.uppercase() }

    /** "Martes 16 de setiembre 2026" */
    fun fechaLarga(d: LocalDate) =
        "${DIAS.getValue(d.dayOfWeek)} ${d.dayOfMonth} de ${MESES[d.monthValue - 1]} ${d.year}"
}
