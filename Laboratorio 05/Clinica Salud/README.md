# Clínica Salud

Aplicación móvil para Android desarrollada en **Kotlin** utilizando **Jetpack Compose** y **Material 3**. Diseñada para facilitar la gestión de servicios médicos, agendamiento de citas, consulta de historiales médicos y perfiles de especialistas.

---

## Características Principales

- **Pantalla de Inicio (`InicioScreen`):** Panel principal con acceso rápido a servicios y opciones destacadas de la clínica.
- **Agendamiento de Citas (`AgendarCitaScreen`):** Selección de especialidad, médico, fecha y hora para agendar nuevas consultas médicas.
- **Confirmación de Cita (`ConfirmacionScreen`):** Vista de resumen y validación de los datos de la cita reservada.
- **Mis Citas (`MisCitasScreen`):** Listado y gestión de las citas médicas programadas por el usuario.
- **Historial Médico (`HistorialMedicoScreen`):** Consulta de registros médicos previos, diagnósticos y tratamientos.
- **Perfil de Médico (`PerfilMedicoScreen`):** Información detallada sobre los especialistas, horarios y especialidades.
- **Navegación Intuitiva:** Implementación con Navigation Compose y menú lateral (`DrawerDestino`).

---

## Tecnologías y Arquitectura

- **Lenguaje:** [Kotlin](https://kotlinlang.org/)
- **UI Toolkit:** [Jetpack Compose](https://developer.android.com/jetpack/compose) & [Material 3](https://m3.material.io/)
- **Navegación:** Jetpack Navigation Compose (`androidx.navigation.compose`)
- **Ciclo de Vida:** Lifecycle Runtime KTX & ViewModel
- **Min SDK:** 24 (Android 7.0+)
- **Target SDK / Compile SDK:** 37

---

## Estructura del Proyecto

```text
com.suarez.clinicasalud/
│
├── data/                  # Modelos de datos y fuentes de información
│   └── models.kt
│
├── ui/                    # Componentes de interfaz de usuario
│   ├── ClinicaSaludApp.kt # Contenedor principal y navegación
│   ├── components/        # Elementos reutilizables (Drawer, etc.)
│   ├── screens/           # Pantallas de la aplicación
│   │   ├── AgendarCitaScreen.kt
│   │   ├── ConfirmacionScreen.kt
│   │   ├── HistorialMedicoScreen.kt
│   │   ├── InicioScreen.kt
│   │   ├── MisCitasScreen.kt
│   │   └── PerfilMedicoScreen.kt
│   └── theme/             # Configuración de tema Material 3 (Colores, Tipografía)
│       ├── Color.kt
│       ├── Theme.kt
│       └── Type.kt
│
└── MainActivity.kt        # Actividad principal de entrada
```

---

## Cómo Ejecutar el Proyecto

1. Clonar o abrir el repositorio en **Android Studio** (Koala / Ladybug o superior).
2. Sincronizar el proyecto con Gradle (`Sync Project with Gradle Files`).
3. Conectar un dispositivo físico Android o iniciar un Emulador con **API 24 o superior**.
4. Ejecutar la aplicación (`Run 'app'`).

---

## Proyecto en Funcionamiento
