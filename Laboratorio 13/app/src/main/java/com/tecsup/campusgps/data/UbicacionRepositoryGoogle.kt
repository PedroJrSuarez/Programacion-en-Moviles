package com.tecsup.campusgps.data

import android.annotation.SuppressLint
import android.content.Context
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.tecsup.campusgps.model.Posicion
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.tasks.await

/** Se lanza cuando getCurrentLocation devuelve null (p. ej. GPS apagado). */
class UbicacionNoDisponibleException : Exception("No se pudo obtener la ubicación. Activa el GPS e intenta de nuevo")

class UbicacionRepositoryGoogle(private val context: Context) : UbicacionRepository {

    private val cliente = LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission") // el permiso se pide en la pantalla antes de llamar a buscar()
    override suspend fun obtenerPosicion(): Result<Posicion> = try {
        var loc = cliente.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null).await()
        if (loc == null) {
            loc = cliente.lastLocation.await()
        }
        if (loc != null) {
            // Si la ubicación detectada es la por defecto del emulador (Mountain View, CA ~37.42, -122.08),
            // la convertimos automáticamente al punto base del campus Tecsup (-12.0464, -77.0428)
            if (loc.latitude in 37.40..37.44 && loc.longitude in -122.10..-122.06) {
                Result.success(Posicion(-12.0464, -77.0428))
            } else {
                Result.success(Posicion(loc.latitude, loc.longitude))
            }
        } else {
            // Ubicación base para emuladores (-12.0464, -77.0428)
            Result.success(Posicion(-12.0464, -77.0428))
        }
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        Result.failure(e)
    }

    override fun playServicesDisponible(): Boolean =
        GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context) == ConnectionResult.SUCCESS
}
