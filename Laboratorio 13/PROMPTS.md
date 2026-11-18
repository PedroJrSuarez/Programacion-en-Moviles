# PROMPTS.md

## Fase 2: mejoras asistidas por IA

### 1. Estado del filtro por categoria

**Prompt**

> En `MapaViewModel`, agrega un estado para la categoria seleccionada y expone una lista de lugares
> visibles derivada con `combine`. Cuando cambie la categoria, limpia el lugar seleccionado. No
> filtres dentro de un Composable.

**Resultado**

El ViewModel expone las categorias, el filtro actual y `lugaresVisibles`. Al cambiar el filtro,
limpia la seleccion para evitar que el mapa apunte a un lugar oculto.

### 2. Chips para filtrar lugares

**Prompt**

> Crea chips de Jetpack Compose para mostrar todas las categorias y la opcion "Todos". Al tocar un
> chip, notifica el cambio al ViewModel y usa la misma lista filtrada en el mapa y en la lista.

**Resultado**

La pantalla presenta los chips y tanto el mapa como la lista consumen `lugaresVisibles` del
ViewModel.

### 3. Calculo de lugares dentro del radio

**Prompt**

> En el ViewModel, agrega las opciones de radio de 100, 200 y 500 metros. Expone los IDs de los
> lugares visibles que estan dentro del radio seleccionado; considera que la distancia puede ser
> nula antes de obtener la ubicacion.

**Resultado**

El ViewModel expone el radio seleccionado y calcula `idsEnRadio` usando las distancias disponibles.
Los lugares sin distancia calculada no se consideran dentro del radio.

### 4. Selector y dibujo del radio

**Prompt**

> Agrega controles para seleccionar 100, 200 o 500 metros y dibuja el radio alrededor de la
> ubicacion actual en el mapa. Marca en la lista los lugares dentro del radio y deshabilita el
> selector hasta que se obtenga la ubicacion. Mantén la interfaz separada de la logica de estado.

**Resultado**

La pantalla presenta los controles y la lista muestra la marca de cercania. El mapa dibuja el
circulo; los controles se habilitan cuando el estado contiene una ubicacion.

### 5. Punto azul nativo con permiso

**Prompt**

> Activa el punto azul nativo y el boton de ubicacion de Google Maps solo cuando se haya concedido
> un permiso de ubicacion. Comprueba el permiso en la pantalla y pasa el resultado al mapa; no pases
> `Context` al ViewModel ni habilites la capa de ubicacion sin permiso.

**Resultado**

La pantalla comprueba los permisos y el mapa configura `isMyLocationEnabled` y
`myLocationButtonEnabled` con ese resultado. Sin permiso, estas funciones nativas permanecen
deshabilitadas.
