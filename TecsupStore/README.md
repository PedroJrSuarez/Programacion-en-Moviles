# 🛒 Tecsup Store

**Tecsup Store** es una aplicación móvil desarrollada para la plataforma Android utilizando tecnologías modernas de desarrollo nativo como **Jetpack Compose** y **Material Design 3 (M3)**. Este proyecto forma parte de los trabajos de programación en móviles.

---

## ✨ Características Principales

- **Interfaz de Usuario Declarativa (Jetpack Compose):** Construida íntegramente con componentes composables modernos y eficientes.
- **Diseño Material 3 (M3):** Implementación de la paleta de colores, tipografía y componentes oficiales de Material 3.
- **Navegación Lateral (Modal Navigation Drawer):** Menú lateral deslizante con opciones de navegación estructuradas mediante `sealed class` (`Inicio`, `Mis pedidos`, `Favoritos`, `Perfil`, `Cerrar sesión`).
- **Catálogo de Productos:** Visualización de productos destacados (como Audífonos, Smartwatch) en tarjetas interactivas (`Card`).
- **Menús Contextuales y Acciones:** Cada tarjeta de producto incluye un menú desplegable (`DropdownMenu`) con opciones para ver detalles, compartir o reportar.

---

## 🛠️ Tecnologías y Librerías

- **Lenguaje:** [Kotlin](https://kotlinlang.org/)
- **UI Toolkit:** [Jetpack Compose](https://developer.android.com/jetpack/compose)
- **Design System:** [Material 3 (M3)](https://m3.material.io/)
- **Iconos:** Material Icons Extended (`androidx.compose.material:material-icons-extended`)
- **Ciclo de Vida y Actividades:** AndroidX Core KTX, Lifecycle KTX, Activity Compose
- **Entorno de Desarrollo:** Android Studio

---

## 📂 Estructura del Proyecto

El código fuente se encuentra en el paquete `com.suarez.tecsupstore`:

```text
com.suarez.tecsupstore/
├── MainActivity.kt        # Actividad principal y punto de entrada de la UI
├── AppNavegacion.kt       # Contenedor principal con Scaffold y Navigation Drawer
├── AppDrawer.kt           # Definición del menú lateral (Destinos y cabecera de usuario)
├── PantallaInicio.kt      # Pantalla principal con lista de productos
├── Producto.kt            # Modelo de datos (Data class) para los productos
├── TarjetaProducto.kt     # Componente visual de tarjeta con menú contextual
└── ui/
    └── theme/             # Configuración de temas, colores y tipografía (Material 3)
```

---

## 🚀 Requisitos y Configuración

1. **Android Studio:** Versión recomendada (Koala, Ladybug o superior).
2. **SDK de Android:** 
   - **Compile SDK:** 37
   - **Target SDK:** 37
   - **Min SDK:** 24 (Android 7.0 o superior)
3. **Java Development Kit (JDK):** Versión 11 o superior.

---

## ▶️ Cómo Ejecutar el Proyecto

1. Clona o descarga este repositorio en tu equipo.
2. Abre **Android Studio** y selecciona **Open an Existing Project**, eligiendo la carpeta raíz del proyecto (`TecsupStore`).
3. Espera a que Gradle sincronice todas las dependencias (`Gradle Sync`).
4. Conecta un dispositivo físico Android con la *Depuración USB* activada o inicia un emulador de Android (AVD).
5. Haz clic en el botón **Run ▶️** (o presiona `Shift + F10`) en Android Studio para compilar y desplegar la aplicación.

---

## 💡 Preguntas Teóricas y de Arquitectura en Jetpack Compose

### 1. ¿Por qué el `DropdownMenu` se declara dentro de un `Box` junto al ícono que lo activa, y no en cualquier parte de la pantalla?
En Jetpack Compose, el componente `DropdownMenu` requiere un punto de anclaje (*anchor*) para calcular su posición en pantalla de manera relativa al elemento que lo despliega. Al colocar el `IconButton` y el `DropdownMenu` juntos dentro de un contenedor `Box`, el menú utiliza las coordenadas del botón como referencia espacial para desplegarse correctamente (por ejemplo, justo debajo o alineado con este). Si se declarara en cualquier otra parte de la pantalla de forma aislada, el menú carecería de un anclaje lógico y requeriría coordenadas absolutas complejas, perdiendo el comportamiento flotante y responsivo que Compose maneja automáticamente.

### 2. ¿Qué diferencia de alcance hay entre las opciones del `DropdownMenu` (afectan solo a un producto) y las del `NavigationDrawer` (afectan a toda la app)?
- **Opciones del `DropdownMenu` (Ámbito local / por ítem):** Están vinculadas exclusivamente a una instancia específica de `TarjetaProducto`. Acciones como *"Ver detalle"*, *"Compartir"* o *"Reportar"* operan de forma puntual sobre ese producto en particular sin modificar el estado general de la aplicación.
- **Opciones del `NavigationDrawer` (Ámbito global / de navegación principal):** Afectan a toda la estructura de la aplicación. Destinos como `Inicio`, `Mis pedidos`, `Favoritos`, `Perfil` o `Cerrar sesión` modifican el estado de navegación global (`rutaActual`), controlando qué pantalla principal se muestra en el contenedor raíz (`ModalNavigationDrawer`).

### 3. ¿Cómo tuviste que estructurar tu código para que el contador de favoritos del drawer "se entere" de lo que pasa en el DropdownMenu de cada producto?
Para lograr la comunicación ascendente y transversal desde un componente hijo (`TarjetaProducto`) hasta un contenedor superior o menú lateral (`AppDrawer`), se implementa el patrón de **Elevación de Estado (*State Hoisting*)** junto con **lambdas de retrollamada (*callbacks*)**:
1. El estado compartido (por ejemplo, la lista de favoritos o un contador) se define en el nivel superior (en `AppNavegacion`).
2. Se pasa una función callback (como `onToggleFavorito: (Int) -> Unit`) hacia abajo a través de los composables intermediarios (`PantallaInicio`) hasta `TarjetaProducto`.
3. Cuando el usuario interactúa con el menú del producto, se ejecuta la lambda que actualiza el estado superior, provocando la recomposición automática y reactiva del badge o contador en el `AppDrawer`.

### 4. ¿Qué tuviste que corregir del código que te generó la IA para la mejora del badge de favoritos?
Al integrar sugerencias generadas por IA para añadir contadores o badges en elementos de navegación y listas, comúnmente es necesario revisar y corregir:
- **Gestión correcta del estado reactivo:** Asegurar que los cambios de estado utilicen `remember` con `mutableStateOf` (o flujos reactivos) en el nivel adecuado para evitar pérdida de datos ante recomposiciones o cambios de configuración.
- **Aislamiento de recomposiciones:** Evitar que la actualización del estado de un producto dispare recomposiciones innecesarias en toda la pantalla o en elementos independientes del drawer.
- **Semántica y alineación:** Corregir el diseño y el contenedor visual (como `BadgedBox` o modificadores de padding) dentro del `NavigationDrawerItem` para que el badge numérico o de texto se muestre alineado correctamente sin romper la interfaz de Material 3.

---

## 👤 Autor

Desarrollado como parte de las prácticas y proyectos de Programación en Móviles en **Tecsup**.
