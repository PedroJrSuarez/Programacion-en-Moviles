# CampusGPS

App Android (Kotlin + Jetpack Compose + Material 3) que obtiene tu ubicación, la muestra en un mapa
y te dice qué punto del campus tienes más cerca, con su distancia, ordenados de menor a mayor.

## Estructura de paquetes
```
com.tecsup.campusgps/
├── MainActivity.kt
├── model/      Modelos.kt (Posicion, Lugar, LugarConDistancia, TipoError, UbicacionUi)
├── data/       UbicacionRepository(+Google), LugaresRepository(+Local)
├── util/       Distancia.kt, Formato.kt
├── viewmodel/  MapaViewModel.kt, CampusViewModelFactory.kt
└── ui/         PantallaMapa, MapaUbicacion, ListaLugares, MensajeError, theme/
```

## Arquitectura MVVM + Repository
```
View (PantallaMapa) --evento--> ViewModel (MapaViewModel) --StateFlow--> View (collectAsState)
ViewModel --> UbicacionRepository ---> UbicacionRepositoryGoogle (FusedLocationProviderClient)
ViewModel --> LugaresRepository   ---> LugaresRepositoryLocal (lugares del campus)
```

## Mapa con osmdroid

El mapa usa osmdroid con teselas de OpenStreetMap y se centra inicialmente en Tecsup Lima.
No requiere una clave de Google Maps. Se necesita conexion a Internet para descargar las teselas.

La ubicacion obtenida con **Obtener mi ubicacion** reemplaza el centro inicial. En el emulador,
configura una ubicacion de Lima desde **Extended controls > Location** para probarla; el emulador
puede reportar por defecto una ubicacion fuera del Peru. Los lugares de ejemplo son ficticios;
ajusta sus coordenadas a las ubicaciones reales del campus.

## Mejoras de Fase 2 con IA

- Chips para filtrar los lugares por categoria; el filtro se aplica en el ViewModel y se refleja
  tanto en el mapa como en la lista.
- Selector de radios de 100, 200 o 500 m alrededor de la ubicacion, con circulo en el mapa y marcas
  para los lugares que quedan dentro.
- Al seleccionar un lugar en la lista, el mapa se centra en el marcador y acerca el zoom.

## Capturas
<img width="422" height="922" alt="image" src="https://github.com/user-attachments/assets/6e23299b-768d-47fc-8039-4d9b8a120b2b" />


## Para pensar
1. **¿Por qué FusedLocationProviderClient está en el Repository?** Porque es una dependencia de Android/Google Play Services que necesita Context. El ViewModel solo conoce la interface, así que no depende de Android, se puede probar con un repositorio falso y se puede cambiar la fuente de ubicación sin tocarlo.
2. **Permiso de ubicacion.** El permiso lo concede el usuario para que la app lea su ubicacion; si falta, no se puede mostrar su posicion.
3. **Teselas del mapa.** osmdroid descarga las teselas de OpenStreetMap por Internet; respeta sus politicas de uso y atribucion.
4. **¿Dónde se calcula la distancia?** En el ViewModel (`actualizarDistancias`), usando la función pura `distanciaMetros` de `util/`. Un Composable se redibuja muchas veces y debe limitarse a dibujar el estado; calcular ahí rompe MVVM.
5. **Permiso negado dos veces.** Android deja de mostrar el cuadro (negación permanente) y el botón parece no hacer nada. La app muestra el mensaje de error con el botón **Abrir ajustes**, que lleva a la pantalla de la app para activar el permiso a mano.
