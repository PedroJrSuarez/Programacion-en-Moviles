package com.tecsup.campusgps.data

import com.tecsup.campusgps.model.Lugar

class LugaresRepositoryLocal : LugaresRepository {
    override fun obtenerLugares(): List<Lugar> = listOf(
        Lugar(1, "Pabellón A", "Aulas y Laboratorios", -12.0460, -77.0425),
        Lugar(2, "Pabellón B", "Aula Magna", -12.0468, -77.0430),
        Lugar(3, "Biblioteca Central", "Estudio y Recursos", -12.0455, -77.0432),
        Lugar(4, "Cafetería Principal", "Servicios", -12.0470, -77.0420),
        Lugar(5, "Auditorio Tecsup", "Eventos", -12.0462, -77.0415)
    )
}
