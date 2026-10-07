# 🏥 SaludPlus Citas – App Paciente

Aplicación móvil desarrollada en **Kotlin** utilizando **Jetpack Compose** y arquitectura modular basada en pantallas y repositorio en memoria, diseñada para facilitar la gestión de citas médicas para pacientes.

---

## 🚀 Características Principales

1. **Autenticación y Registro:**
   - SplashScreen de bienvenida.
   - Registro de nuevos usuarios con validación de campos.
   - Inicio de sesión con credenciales válidas y manejo de sesión actual.
   - Términos y condiciones.

2. **Pantalla Principal (HomeScreen):**
   - Saludo personalizado al usuario autenticado.
   - Tarjetas informativas de acceso rápido.
   - Carrusel horizontal (`LazyRow`) con especialidades médicas destacadas.

3. **Navegación Integral:**
   - `NavigationBar` (BottomBar) persistente con 4 destinos principales: Inicio, Especialidades, Mis Citas y Perfil.

4. **Flujo de Agendamiento de Citas:**
   - **EspecialidadesScreen:** Búsqueda en tiempo real de especialidades médicas.
   - **MedicosScreen:** Listado de profesionales médicos filtrados por el parámetro `especialidadId`.
   - **FechaHoraScreen:** Selección de fecha y horarios disponibles mediante grilla interactiva (`LazyVerticalGrid`).
   - **ConfirmarCitaScreen:** Resumen de la cita y confirmación final.
   - **CitaExitosaScreen:** Pantalla de éxito con limpieza de pila de navegación (`popUpTo` hacia HomeScreen).

5. **Gestión de Citas y Perfil:**
   - **MisCitasScreen:** Listado de citas agendadas y opción de cancelación.
   - **PerfilScreen:** Datos del usuario activo y opción de cierre de sesión.

---

## 🛠️ Tecnologías y Librerías

- **Lenguaje:** Kotlin 1.9+
- **UI Toolkit:** Jetpack Compose (Material 3)
- **Navegación:** Jetpack Navigation Compose (`androidx.navigation.compose`)
- **Gestión de Estado:** Compose State & MutableStateFlow
- **Capa de Datos:** Repositorio en memoria (`Repositorio.kt`) simulando persistencia y lógica de negocio.

---

## 📂 Estructura del Proyecto

```text
com.saludplus.citas/
├── data/
│   ├── model/         # Modelos de datos (Usuario, Especialidad, Medico, Cita)
│   └── repository/    # Repositorio central en memoria (Repositorio.kt)
├── navigation/        # Rutas (Rutas.kt) y Grafo de navegación (AppNavigation.kt)
└── ui/
    ├── components/    # Componentes reutilizables de UI (Componentes.kt)
    └── screens/       # Pantallas organizadas por módulos (auth, home, agendamiento, citas, perfil, etc.)
```

---

## 🔑 Credenciales de Acceso Demo

Puedes utilizar la cuenta precargada para probar la aplicación inmediatamente:
- **Correo:** `demo@saludplus.com`
- **Clave:** `123456`

---

## 📌 Historial de Commits Incrementales

El desarrollo del proyecto se estructuró en 8 commits incrementales y funcionales:

1. `feat(repo): implementar gestión de usuarios (registrarUsuario, iniciarSesion, cerrarSesion)`
2. **Implementar SplashScreen RegistroScreen y LoginScreen con validaciones**
3. **Implementar repositorio de especialidades y medicos con busquedas y filtros**
4. **Implementar HomeScreen con tarjetas saludo y LazyRow de especialidades destacadas**
5. **Implementar NavigationBar bottomBar con 4 destinos funcionando**
6. **Implementar EspecialidadesScreen con busqueda y MedicosScreen con parametro especialidadId**
7. **Implementar FechaHoraScreen con LazyVerticalGrid y horariosDisponibles**
8. **Implementar ConfirmarCitaScreen CitaExitosaScreen con popUpTo MisCitasScreen y PerfilScreen**

---

## ⚙️ Instrucciones de Ejecución

1. Abrir **Android Studio**.
2. Seleccionar **File > Open** y abrir la carpeta `SaludPlusCitas`.
3. Esperar a que finalice el **Gradle Sync**.
4. Ejecutar la aplicación en un emulador o dispositivo físico Android (API 24+).
