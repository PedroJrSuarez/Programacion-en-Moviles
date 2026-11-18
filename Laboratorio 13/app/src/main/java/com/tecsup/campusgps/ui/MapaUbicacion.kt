package com.tecsup.campusgps.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import com.tecsup.campusgps.model.LugarConDistancia
import com.tecsup.campusgps.model.UbicacionUi

@Composable
fun MapaUbicacion(
    estado: UbicacionUi,
    lugares: List<LugarConDistancia>,
    seleccionado: Int?,
    modifier: Modifier = Modifier,
    masCercanoId: Int? = null
) {
    val camara = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(-12.0464, -77.0428), 12f)
    }

    // Paso 3: al llegar la ubicación, la cámara viaja a tu posición
    LaunchedEffect(estado) {
        if (estado is UbicacionUi.Lista) {
            camara.animate(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(estado.posicion.latitud, estado.posicion.longitud), 16f
                )
            )
        }
    }

    // Paso 6: al seleccionar un lugar, la cámara viaja hasta él
    LaunchedEffect(seleccionado) {
        val item = lugares.firstOrNull { it.lugar.id == seleccionado } ?: return@LaunchedEffect
        camara.animate(
            CameraUpdateFactory.newLatLngZoom(LatLng(item.lugar.latitud, item.lugar.longitud), 17f)
        )
    }

    GoogleMap(modifier = modifier, cameraPositionState = camara) {
        // Paso 3: marcador propio (rojo por defecto)
        if (estado is UbicacionUi.Lista) {
            Marker(
                state = rememberMarkerState(
                    position = LatLng(estado.posicion.latitud, estado.posicion.longitud)
                ),
                title = "Estás aquí"
            )
        }
        // Pasos 4 y 7: un marcador por lugar; el más cercano en verde
        lugares.forEach { item ->
            val lugar = item.lugar
            key(lugar.id) {
                val markerState = rememberMarkerState(
                    position = LatLng(lugar.latitud, lugar.longitud)
                )
                LaunchedEffect(seleccionado) {
                    if (seleccionado == lugar.id) markerState.showInfoWindow()
                }
                val hue = if (lugar.id == masCercanoId) BitmapDescriptorFactory.HUE_GREEN
                else BitmapDescriptorFactory.HUE_AZURE
                Marker(
                    state = markerState,
                    title = lugar.nombre,
                    snippet = lugar.categoria,
                    icon = BitmapDescriptorFactory.defaultMarker(hue)
                )
            }
        }
    }
}
