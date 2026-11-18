# Commits de la Fase 1 (uno por paso, en este orden)
1  importa proyecto base CampusGPS
2  configura permisos y clave de Maps con local.properties   -> AndroidManifest.xml, build.gradle.kts
3  pide permiso de ubicacion en tiempo de ejecucion -> PantallaMapa (lanzador + alPulsar)
4  muestra mapa con marcador de la ubicacion actual     -> MapaUbicacion (camara + "Estás aquí")
5  agrega marcadores de los lugares del campus          -> MapaUbicacion (forEach + key)
6  calcula y ordena lugares por distancia          -> Distancia.kt, Formato.kt, ViewModel
7  agrega lista de lugares y seleccion en el mapa         -> ListaLugares, seleccionar(), PantallaMapa
8  resalta y navega al lugar mas cercano                  -> masCercano, HUE_GREEN, botón
9  maneja permiso denegado, GPS apagado y sin Play Services -> Modelos, Repository, MensajeError
10 agrega README con estructura y capturas
