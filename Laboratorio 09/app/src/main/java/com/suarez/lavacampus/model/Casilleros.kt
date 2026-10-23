package com.suarez.lavacampus.model

import java.util.Locale

object Casilleros {
    /** C-01 ... C-30 */
    val todos: List<String> = (1..30).map { String.format(Locale.US, "C-%02d", it) }
}
