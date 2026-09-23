# TECSUP Fit

## Descripción del Programa
**TECSUP Fit** es una aplicación móvil moderna desarrollada nativamente para Android utilizando **Jetpack Compose** y **Material 3**. La aplicación está diseñada para gestionar la reserva de clases de gimnasio y rutinas de entrenamiento de forma rápida, intuitiva y fluida.

### ✨ Características Principales
- **Pantalla de Inicio:** Visualización del saludo personalizado de bienvenida y filtrado dinámico de clases disponibles por día ("Hoy" o "Esta semana").
- **Detalle y Reserva de Clases:** Información detallada de cada clase (horario, sala, duración, descripción, cupos disponibles) con un flujo de reserva instantáneo.
- **Confirmación y Navegación Flexible:** Pantalla de confirmación de reserva con opciones duales para ver las reservas realizadas o volver rápidamente al inicio (`Inicio`) para seleccionar más clases/rutinas.
- **Gestión de Reservas:** Vista centralizada de todas las clases reservadas por el usuario.
- **Rutinas de Entrenamiento:** Listado de rutinas predefinidas (Fuerza, Cardio, Movilidad).
- **Perfil de Usuario:** Visualización de información de cuenta, plan actual y estadísticas en tiempo real (clases reservadas y rachas).
- **Navegación Inferior (BottomBar):** Barra de navegación con pestañas persistentes y manejo optimizado del historial y retorno al inicio.

---

## Cómo abrirlo y ejecutarlo
1. Descomprime el proyecto.
2. Abre **Android Studio** → **Open** → selecciona la carpeta `TecsupFit`.
3. Espera a que sincronice **Gradle** (la primera vez descargará las dependencias necesarias).
   - *Nota:* Si Android Studio pide reparar el Gradle Wrapper, haz clic en **"Try Again"** o **"Sync Now"**.
4. Ejecuta la aplicación presionando el botón **▶ (Run)** en un emulador o dispositivo físico con Android API 24+.

---

## Estructura por Paquetes
```
app/src/main/java/com/tecsup/fit/
├── MainActivity.kt              (Punto de entrada limpio: onCreate + setContent)
├── TecsupFitApp.kt              (Orquestador central: Scaffold, NavHost y estado global de reservas)
├── data/
│   └── ClaseGimnasio.kt         (Modelos de datos y mock de clases)
├── navigation/
│   ├── Rutas.kt                 (Definición de rutas selladas y elementos del bottomBar)
│   └── BarraInferior.kt         (Barra de navegación inferior con soporte para popBackStack)
├── ui/theme/
│   ├── Color.kt                 (Paleta de colores oficial extraída del diseño)
│   └── Theme.kt                 (Configuración del tema Material 3)
├── ui/components/
│   ├── ChipFiltro.kt            (Componente de selección de filtro por día)
│   ├── IconoTeal.kt             (Contenedor de iconos con acento teal)
│   ├── TarjetaClase.kt          (Tarjeta de visualización de clases)
│   └── TarjetaReserva.kt        (Tarjeta de visualización de reservas)
└── ui/screens/
    ├── PantallaInicio.kt        (Pantalla principal con clases y filtros)
    ├── PantallaDetalleClase.kt  (Vista detallada de la clase seleccionada)
    ├── PantallaConfirmacion.kt  (Pantalla de éxito con opción de retorno al inicio)
    ├── PantallaReservas.kt      (Historial de reservas del usuario)
    ├── PantallaRutinas.kt       (Listado de rutinas de entrenamiento)
    └── PantallaPerfil.kt        (Perfil, plan y estadísticas del usuario)
```

---

## Diseño e Interfaz
La interfaz de usuario implementa fielmente los lineamientos visuales y la paleta de colores especificada en el diseño (Header Teal `#0F6E56`, fondos de iconos `#E1F5EE`, tarjetas en gris claro `#F0F0F0` y tipografías adaptadas a Material 3).

---

## Proyecto en funcionamiento
![img.png](img.png)
