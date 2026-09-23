# Portal Académico (Navigation Compose)

## Descripción del programa

**Portal Académico** es una aplicación académica móvil desarrollada en **Kotlin + Jetpack Compose + Material 3 + Navigation Compose**, construida sobre el proyecto base `com.suarez.semana05_navegacion`.

La app simula el acceso de un estudiante a un portal universitario, permitiéndole iniciar sesión, ver un directorio de compañeros, consultar el expediente académico de cualquiera de ellos y revisar/gestionar su propio perfil. No usa backend, base de datos, internet ni imágenes reales: todos los datos (5 alumnos) son fijos y los avatares son iconos sobre círculos de color, generados completamente con Compose.

La app está compuesta por 5 pantallas conectadas mediante `NavHost`/`NavController`:

1. **Login** — acceso con correo institucional y contraseña (demo, sin autenticación real).
2. **Home / Inicio** — bienvenida y accesos rápidos al Directorio y al Perfil.
3. **Directorio de Alumnos** — lista de 5 estudiantes fijos.
4. **Expediente Académico** — detalle de un alumno seleccionado (recibido como `studentId: Int` vía `NavType.IntType`).
5. **Configuración de Perfil** — datos personales/académicos del usuario y cierre de sesión.

Este README incluye el **prompt maestro** usado para pedirle a un agente de IA (por ejemplo, Gemini dentro de Android Studio) que transforme el proyecto base existente en esta app, de forma que el resultado sea lo más reproducible posible entre distintas computadoras que partan del mismo código base.

---

## Programa en funcionamiento


---

## Requerimientos funcionales

1. **Autenticación de acceso (Login)** — El sistema debe permitir al usuario ingresar mediante un correo institucional y una contraseña, con opción de mostrar/ocultar la contraseña, antes de acceder al resto de la aplicación.

2. **Consulta de directorio de estudiantes** — El sistema debe mostrar una lista de alumnos registrados (nombre y carrera), permitiendo al usuario seleccionar uno para ver más detalle.

3. **Visualización de expediente académico** — El sistema debe mostrar la información académica detallada de un alumno seleccionado (ID de estudiante, correo, facultad y biografía).

4. **Gestión del perfil personal y cierre de sesión** — El sistema debe permitir al usuario visualizar y revisar su propia información personal (nombre, correo, teléfono) y académica (carrera, ciclo actual), así como cerrar la sesión de forma segura desde el menú principal o desde su perfil.

---

## Prompt

---

ACTÚA COMO UN DESARROLLADOR ANDROID SENIOR ESPECIALIZADO EN:

- Kotlin
- Jetpack Compose
- Material 3
- Navigation Compose
- diseño de interfaces móviles
- organización de proyectos Android
- corrección de errores de compilación

Tienes acceso DIRECTO al proyecto Android Studio que está actualmente abierto.
**TU TRABAJO NO ES EXPLICARME CÓMO HACERLO.**
**TU TRABAJO ES MODIFICAR DIRECTAMENTE EL PROYECTO EXISTENTE, COMPILARLO, CORREGIR LOS ERRORES Y DEJARLO FUNCIONANDO.**

---

### 1. OBJETIVO PRINCIPAL
Este proyecto es una aplicación Android educativa desarrollada originalmente con:

- Kotlin
- Jetpack Compose
- Material 3
- Navigation Compose
- NavController
- NavHost
- composable
- sealed class para las rutas
- navegación entre pantallas
- argumentos tipados mediante NavType.IntType

El proyecto actual es solamente el **CÓDIGO BASE**.
Debes transformar su presentación y navegación para convertirlo en una aplicación académica moderna llamada conceptualmente:

**PORTAL ACADÉMICO**

La aplicación final debe tener una interfaz moderna, limpia, ordenada, consistente y profesional **que coincida visualmente, píxel a píxel en su estructura, con las 5 capturas de pantalla proporcionadas** (Login, Home, Directorio de Alumnos, Expediente Académico y Configuración de Perfil).

**IMPORTANTE SOBRE LAS CAPTURAS:** las capturas muestran fotografías reales en los avatares de la lista de alumnos y del expediente. **ESO NO DEBE IMPLEMENTARSE.** En su lugar, todo avatar debe ser un círculo de color sólido (de la paleta definida en este prompt) con el icono `Icons.Default.Person` en su interior, tal como se especifica en la sección 19. El marco del teléfono, la barra de estado (hora, iconos de señal/batería) y el notch que aparecen en las capturas son parte del mockup de diseño y **NO deben implementarse** dentro de la app.

---

### 2. OBJETIVO DE REPRODUCIBILIDAD
**ESTA ES UNA REGLA CRÍTICA.**

Este mismo proyecto será utilizado en **VARIAS COMPUTADORAS**.
El mismo prompt también será ejecutado en otras computadoras que tengan el mismo proyecto base.

Por lo tanto:

**EL RESULTADO DEBE SER DETERMINISTA.**

- NO debes improvisar.
- NO debes cambiar el diseño porque "creas" que existe una opción mejor.
- NO debes cambiar colores.
- NO debes cambiar textos.
- NO debes cambiar tamaños arbitrariamente.
- NO debes agregar funcionalidades que no fueron solicitadas.
- NO debes utilizar información aleatoria.
- NO debes generar nombres aleatorios.
- NO debes generar colores aleatorios.
- NO debes utilizar componentes diferentes solamente porque sean más fáciles.
- Ante cualquier ambigüedad no cubierta explícitamente por este prompt, elige siempre la opción más simple y literal que cumpla lo escrito aquí — nunca la opción más "creativa".

Debes seguir **EXACTAMENTE** las especificaciones de este prompt.

El objetivo es que:

COMPUTADORA 1
+
COMPUTADORA 2
+
COMPUTADORA 3
+
COMPUTADORA 4

produzcan esencialmente **EL MISMO PROYECTO Y EL MISMO DISEÑO, ARCHIVO POR ARCHIVO Y LÍNEA POR LÍNEA EN LO POSIBLE.**

---

### 3. REGLA DE ORO
**NO CREAR UN PROYECTO NUEVO**

El proyecto actualmente abierto **ES EL PROYECTO BASE**.

- NO crees otro proyecto.
- NO crees otro módulo.
- NO cambies el applicationId sin necesidad.
- NO cambies el namespace.
- NO cambies el package.
- NO migres a XML.
- NO reemplaces Jetpack Compose.
- NO cambies la tecnología.
- NO reconstruyas todo desde cero.

Debes transformar el proyecto existente.

---

### 4. INSPECCIÓN OBLIGATORIA
**ANTES DE MODIFICAR CUALQUIER COSA:**

inspecciona el proyecto completo.

Identifica:

- package principal
- namespace
- MainActivity.kt
- sistema de navegación
- Screen.kt
- AppNavigation.kt
- pantallas existentes
- Color.kt
- Theme.kt
- Type.kt
- build.gradle.kts
- build.gradle.kts del módulo app
- AndroidManifest.xml
- dependencias
- recursos
- configuración de Compose

**NO supongas nombres.**
**NO inventes archivos.**

Primero lee el proyecto real.
Después decide qué archivos modificar.

**Si el proyecto abierto coincide con el inventario del Anexo A (mismo package, mismos nombres de archivo), no es necesario adivinar nada: usa directamente esa correspondencia exacta.** Si el proyecto difiere de ese inventario, ignora el Anexo A y realiza la inspección desde cero.

---

### ANEXO A — INVENTARIO REAL DEL PROYECTO BASE (YA INSPECCIONADO)

El proyecto base entregado es exactamente este (package `com.suarez.semana05_navegacion`):

| Archivo | Ruta | Contenido actual |
|---|---|---|
| `AppNavigation.kt` | `navigation/` | NavHost con 4 rutas: `home`, `list`, `profile`, `detail/{itemId}` (Int) |
| `Screen.kt` | `navigation/` | sealed class con `Home("home")`, `List("list")`, `Profile("profile")`, `Detail("detail/{itemId}")` con `createRoute(itemId: Int)` |
| `HomeScreen.kt` | `screens/` | Pantalla simple con texto "Pantalla Tecsup" y 2 botones (ir a Lista, ir a Perfil) |
| `ListScreen.kt` | `screens/` | Scaffold + TopAppBar "Lista" + LazyColumn con 8 items genéricos ("Elemento número N") |
| `DetailScreen.kt` | `screens/` | Scaffold + TopAppBar "Detalle del elemento", recibe `itemId: Int`, muestra el ID recibido |
| `ProfileScreen.kt` | `screens/` | Texto "Mi Perfil" + "Juan León Suiyon" + botón para volver a Home |
| `Color.kt` | `ui/theme/` | Solo colores default de plantilla (Purple80, PurpleGrey80, Pink80, Purple40, PurpleGrey40, Pink40) — **deben reemplazarse por la paleta de la sección 31** |
| `Theme.kt` | `ui/theme/` | Función `NavLabTheme(darkTheme, dynamicColor: Boolean = true, content)`. **`dynamicColor` está en `true` por defecto — debe cambiarse a `false`** para cumplir la sección 38/39 |
| `Type.kt` | `ui/theme/` | Typography con solo `bodyLarge` definido; el resto son valores por defecto de Material 3 |

**Correspondencia exacta (archivo/pantalla actual → resultado final):**

| Elemento actual | Se convierte en | Acción |
|---|---|---|
| — (no existe) | `LoginScreen.kt` | **Crear archivo nuevo** en `screens/` |
| `HomeScreen.kt` | `HomeScreen.kt` (mismo archivo) | **Reescribir** con el diseño de la sección 12–15 |
| `ListScreen.kt` | `DirectoryScreen.kt` | **Renombrar y reescribir** según sección 16–20. Se recomienda renombrar el archivo a `DirectoryScreen.kt`, pero si se prefiere mantener el nombre de archivo `ListScreen.kt` conteniendo la función `DirectoryScreen`, es aceptable — lo importante es la función `@Composable fun DirectoryScreen(...)` y la ruta `directory` |
| `DetailScreen.kt` | `AcademicRecordScreen.kt` | **Renombrar y reescribir** según sección 22–25. El parámetro `itemId: Int` se renombra a `studentId: Int` (el patrón NavType.IntType ya existe en el proyecto base, solo debe renombrarse y ampliarse) |
| `ProfileScreen.kt` | `ProfileScreen.kt` (mismo archivo) | **Reescribir** según sección 26–30 |
| `Screen.kt` | `Screen.kt` (mismo archivo) | **Modificar** sealed class: agregar `Login("login")`, renombrar `List("list")` → `Directory("directory")`, renombrar `Detail("detail/{itemId}")` → `AcademicRecord("academic_record/{studentId}")` con `fun createRoute(studentId: Int): String`, mantener `Profile("profile")` y `Home("home")` |
| `AppNavigation.kt` | `AppNavigation.kt` (mismo archivo) | **Modificar**: `startDestination` cambia de `Screen.Home.route` a `Screen.Login.route`; agregar composable de Login; actualizar los composables de List→Directory y Detail→AcademicRecord con los nuevos nombres de ruta y de función; el argumento `navArgument("itemId")` se renombra a `navArgument("studentId")` |
| `Color.kt` | `Color.kt` (mismo archivo) | **Reemplazar** el contenido completo por la paleta exacta de la sección 31 (mantener o eliminar los colores `Purple80/PurpleGrey80/...` originales, ya no se usan) |
| `Theme.kt` | `Theme.kt` (mismo archivo) | **Modificar únicamente** el valor por defecto de `dynamicColor` de `true` a `false` en la firma de `NavLabTheme(...)`, y usar `LightColorScheme` construido con los colores de la sección 31 (`primary = Primary`, etc.). No renombrar la función `NavLabTheme` |
| `Type.kt` | `Type.kt` (mismo archivo) | **Ampliar** con los tamaños de la sección 32 (28.sp, 24.sp, 18.sp, 14.sp, 12.sp) manteniendo `bodyLarge` existente si no entra en conflicto |
| `MainActivity.kt` | (no incluido en el inventario, pero debe existir) | Verificar que use `NavLabTheme { AppNavigation() }` dentro de `setContent`, sin pasar `dynamicColor = true` explícitamente (para heredar el nuevo default `false`) |

**NO cambiar el nombre de la función `NavLabTheme`** — es el Theme real del proyecto, confirmado en `Theme.kt`.

---

### 5. CONSERVAR EL PACKAGE EXISTENTE
**MUY IMPORTANTE.**

NO cambies el package del proyecto.

El package real, confirmado en el código base, es:

`com.suarez.semana05_navegacion`

debes conservarlo.

Todos los nuevos archivos deben utilizar exactamente este package (mismo subpaquete `.navigation` para Screen.kt/AppNavigation.kt, mismo subpaquete `.screens` para las pantallas, mismo subpaquete `.ui.theme` para Color.kt/Theme.kt/Type.kt).

NO reemplaces el package por uno inventado.

---

### 6. CONSERVAR LA INFRAESTRUCTURA
Debes conservar el funcionamiento general de:

- MainActivity
- Jetpack Compose
- Material 3
- Navigation Compose

La aplicación debe seguir utilizando:

`setContent`

y:

`AppNavigation()`

MainActivity debe permanecer limpia.

---

### 7. NO ACTUALIZAR VERSIONES INNECESARIAMENTE
NO actualices automáticamente:

- Kotlin
- Gradle
- Android Gradle Plugin
- Compose
- SDK
- dependencias

Utiliza las versiones que ya tiene el proyecto base.

Solamente agrega una dependencia si es estrictamente necesaria.

Si una dependencia ya existe:
**UTILÍZALA.**

No reemplaces una dependencia funcional por otra.

---

### 8. DISEÑO FINAL OBLIGATORIO
La aplicación final tendrá **EXACTAMENTE** estas cinco pantallas principales:

1. LOGIN
2. INICIO / BIENVENIDA
3. DIRECTORIO DE ALUMNOS
4. EXPEDIENTE ACADÉMICO
5. CONFIGURACIÓN DE PERFIL

**Flujo:**

```
LOGIN
  ↓
INICIO
  ├── DIRECTORIO DE ALUMNOS
  │       ↓
  │   EXPEDIENTE ACADÉMICO
  │
  └── MI PERFIL ACADÉMICO
```

---

### 9. RUTAS EXACTAS
Adaptar la sealed class existente (ver Anexo A para la correspondencia exacta: `list`→`directory`, `detail/{itemId}`→`academic_record/{studentId}`, se agrega `login`, se mantienen `home` y `profile`).

Las rutas finales deben ser:

- `login`
- `home`
- `directory`
- `academic_record/{studentId}`
- `profile`

La ruta del expediente debe recibir:

`studentId`

como:

`NavType.IntType`

Debe existir una función equivalente a:

```kotlin
fun createRoute(studentId: Int): String
```

NO convertir este argumento en String dentro del NavHost.
Debe recibirse como Int.

---

### 10. LOGIN SCREEN
Crear:
`LoginScreen`

Debe ser la primera pantalla.
Start destination:
`login`

**FONDO**
Utilizar exactamente:
`#FCF8FF`
o el color equivalente definido en Color.kt.

No utilizar Dynamic Color.
No permitir que Android cambie automáticamente la paleta.

**TARJETA PRINCIPAL**
Crear una tarjeta centrada.

Características:

- ancho aproximado: 85% de pantalla
- esquinas: 20.dp
- padding interno: 24.dp
- fondo: `#FFFFFF`
- sombra suave (elevation 4.dp aprox.)

No utilizar valores aleatorios.

**TÍTULO**
Texto exacto:
**Portal Académico**

Características:

- centrado
- color: `#6F4FB3`
- FontWeight.Bold
- tamaño: 28.sp

**SUBTÍTULO**
Texto exacto:
**Accede a tu cuenta**

Características:

- centrado
- color: `#756B7F`
- tamaño: 14.sp

**CAMPO CORREO**
Texto (placeholder/label):
**Correo Institucional**

Debe tener:

- icono Email (leading icon)
- OutlinedTextField
- esquinas redondeadas (12.dp)
- altura cómoda (single line)

**CAMPO CONTRASEÑA**
Texto (placeholder/label):
**Contraseña**

Debe tener:

- icono Lock (leading icon)
- contraseña oculta por defecto (PasswordVisualTransformation)
- botón Visibility / VisibilityOff (trailing icon) que alterna la visibilidad
- esquinas redondeadas (12.dp)

**BOTÓN**
Texto exacto:
**INICIAR SESIÓN**

Características:

- ancho completo
- altura: 52.dp
- fondo: `#6F4FB3`
- texto: blanco
- esquinas: 16.dp
- FontWeight.Bold

**TEXTO INFERIOR**
Texto exacto:
**¿Olvidaste tu contraseña?**

Color:
`#756B7F`
tamaño: 13.sp, centrado, debajo del botón.

No implementar recuperación real.

---

### 11. LOGIN — COMPORTAMIENTO
Al tocar:
**INICIAR SESIÓN**

navegar a:
`home`

usando `popUpTo("login") { inclusive = true }` para que no se pueda volver al Login con el botón Atrás una vez dentro de la app.

No implementar:

- Firebase
- backend
- API
- autenticación real
- base de datos

Es solamente una demostración. No validar el contenido de los campos (cualquier texto, incluso vacío, permite avanzar).

---

### 12. HOME / WELCOME SCREEN
Crear o transformar la Home existente.
Nombre conceptual:
`WelcomeScreen` / `HomeScreen`

**FONDO — CORRECCIÓN IMPORTANTE**
La captura muestra un degradado vertical, NO un color sólido.
Usar exactamente:

```kotlin
Brush.verticalGradient(
    colors = listOf(Primary, Background) // #6F4FB3 → #FCF8FF
)
```

aplicado como fondo de toda la pantalla (`Modifier.background(brush)`), de arriba (morado) hacia abajo (casi blanco), tal como se ve en la captura de Home.

**TEXTO PRINCIPAL**
Mostrar en dos líneas:

**Bienvenido,**
y
**Juan León**

El primer texto:

- 28.sp
- Bold
- color blanco

El segundo:

- 24.sp
- SemiBold
- color blanco

**TEXTO SECUNDARIO**
Mostrar exactamente:
**¿Qué deseas gestionar hoy?**

Color:
blanco con alpha 0.8 aprox.
tamaño: 14.sp

---

### 13. TARJETA DIRECTORIO
Crear una tarjeta.

Texto:
**Directorio de Alumnos**

Subtexto:
**Ver y gestionar estudiantes**

Icono:
`Groups`

Características:

- fondo blanco
- RoundedCornerShape(20.dp)
- padding 20.dp
- ancho completo
- sombra suave
- icono dentro de círculo lavanda (`#E9DFFF`), icono color Primary
- título Bold, 18.sp, color TextPrimary
- subtítulo 14.sp, color TextSecondary

Al tocar:
navegar a:
`directory`

---

### 14. TARJETA PERFIL
Crear una segunda tarjeta.

Texto:
**Mi Perfil Académico**

Subtexto:
**Datos personales y progreso**

Icono:
`Person`

Mismo estilo visual que la tarjeta anterior (mismo padding, forma, sombra, tamaños de texto, círculo lavanda para el icono).
**NO crear un diseño diferente.**

Al tocar:
navegar a:
`profile`

---

### 15. CERRAR SESIÓN (EN HOME) — CORRECCIÓN IMPORTANTE
En la pantalla Home, el cierre de sesión **NO es un botón**, es un texto/enlace pequeño en la parte inferior de la pantalla, tal como se ve en la captura.

Texto exacto:
**Cerrar Sesión Segura**

Características:

- icono `Logout` a la izquierda del texto
- color del icono y del texto: `#C96D69` (LogoutText)
- tamaño de texto: 14.sp
- sin fondo (no es un botón/Card), tipo `TextButton` o `Row` clicable
- alineado al centro horizontal, en la parte inferior de la pantalla con padding aprox. 24.dp

Al tocar:
navegar a:
`login`

utilizando:
`popUpTo("home") { inclusive = true }`

para limpiar el back stack.

No debe poder regresarse al Home mediante el botón Atrás después de cerrar sesión.

**Nota:** este elemento es distinto al botón "Cerrar Sesión" de la pantalla de Perfil (sección 30), que sí es un botón completo con fondo. No unificar ambos por conveniencia: cada pantalla usa su propio estilo tal como aparece en su captura.

---

### 16. DIRECTORY SCREEN
Crear:
`DirectoryScreen`

Utilizar:

- Scaffold
- TopAppBar
- LazyColumn

TopAppBar:
**Directorio de Alumnos**

Debe tener flecha:
`ArrowBack`

La flecha utiliza:
`navController.popBackStack()`

Fondo de la pantalla: `Background` (`#FCF8FF`).

---

### 17. ALUMNOS
Utilizar **EXACTAMENTE** estos cinco alumnos, en este orden:

ID 1
Juan León
Ingeniería de Sistemas

ID 2
María García
Arquitectura

ID 3
Carlos Perez
Medicina

ID 4
Ana Lopez
Derecho

ID 5
Luis Ramírez
Administración

**NO cambiar estos nombres.**
**NO agregar otros alumnos.**
**NO eliminar ninguno.**
**NO generar alumnos aleatorios.**

---

### 18. STUDENT CARD
Cada alumno debe utilizar una tarjeta.

Características exactas:

- fondo blanco
- RoundedCornerShape(16.dp)
- Padding: 16.dp
- Separación entre tarjetas: 12.dp
- sombra suave (elevation 2.dp aprox.)

Cada tarjeta debe contener, en fila:

- círculo de color con icono Person (avatar, ver sección 19)
- columna con nombre (Bold, 16.sp, TextPrimary) y carrera (14.sp, color Primary)
- `ChevronRight` alineado a la derecha, color TextSecondary

---

### 19. AVATARES
**NO utilizar fotografías.**
**NO utilizar imágenes.**
**NO utilizar URLs.**
**NO utilizar Internet.**
**NO utilizar Coil.**
**NO utilizar Glide.**
**NO utilizar Picasso.**
**NO descargar recursos.**

El avatar debe ser creado completamente con Compose:

- forma: `CircleShape`
- tamaño: 48.dp (en la lista del Directorio), 96.dp (en Expediente y Perfil)
- icono: `Icons.Default.Person`, centrado dentro del círculo

**Asignación de color determinista (para que cada alumno se vea igual en todas las computadoras):**
usar el color de fondo del círculo según `studentId % 3`, siguiendo esta tabla fija:

| studentId % 3 | Fondo del círculo | Color del icono |
|---|---|---|
| 0 | `#6F4FB3` (Primary) | `#FFFFFF` (White) |
| 1 | `#E9DFFF` (Lavender) | `#6F4FB3` (Primary) |
| 2 | `#F6F0FF` (LightLavender) | `#6F4FB3` (Primary) |

Esto da como resultado, con los 5 alumnos fijos:
- ID 1 (1 % 3 = 1) → Lavender
- ID 2 (2 % 3 = 2) → LightLavender
- ID 3 (3 % 3 = 0) → Primary
- ID 4 (4 % 3 = 1) → Lavender
- ID 5 (5 % 3 = 2) → LightLavender

No calcular esto de otra forma ni usar colores fuera de esta tabla.

---

### 20. NAVEGACIÓN DEL DIRECTORIO
Al seleccionar:

- Juan León → `academic_record/1`
- María García → `academic_record/2`
- Carlos Perez → `academic_record/3`
- Ana Lopez → `academic_record/4`
- Luis Ramírez → `academic_record/5`

---

### 21. MODELO STUDENT
Crear un modelo sencillo:

```kotlin
data class Student(
    val id: Int,
    val name: String,
    val career: String,
    val email: String,
    val studentCode: String,
    val faculty: String,
    val biography: String
)
```

Crear una lista local fija (`val students: List<Student>`), por ejemplo en un objeto `StudentRepository` o similar, dentro del mismo package del proyecto.

NO utilizar base de datos.
NO utilizar datos aleatorios.
NO utilizar timestamps.
NO generar información diferente en cada ejecución.

---

### 22. ACADEMIC RECORD SCREEN
Crear:
`AcademicRecordScreen`

Debe recibir:
`studentId: Int`

Debe buscar el estudiante correspondiente en la lista fija (por `id == studentId`).

---

### 23. TOP APP BAR
Título exacto:
**Expediente Académico**

Flecha:
`ArrowBack`, usando `navController.popBackStack()`

---

### 24. CABECERA ACADÉMICA
Crear una sección superior con:

- fondo morado `#6F4FB3`
- esquinas inferiores redondeadas (24.dp aprox.)
- avatar circular (96.dp, ver sección 19 para color según studentId)
- icono Person dentro del avatar
- nombre del estudiante (20.sp, Bold, blanco)
- carrera del estudiante (14.sp, blanco con alpha 0.85 aprox.)

NO utilizar fotografías.

---

### 25. INFORMACIÓN DEL EXPEDIENTE
Mostrar, en una tarjeta blanca debajo de la cabecera (RoundedCornerShape 16.dp, padding 20.dp):

**ID Estudiante**
**Correo Electrónico**
**Facultad**
**Biografía**

Cada campo con su icono correspondiente (Badge/tarjeta para ID, Email para correo, School para facultad) y su etiqueta en gris (TextSecondary, 12.sp) sobre el valor en texto normal (TextPrimary, 14.sp).

Datos fijos para los 5 alumnos (usar exactamente estos, coherentes con el nombre/carrera de cada uno):

| ID | Nombre | ID Estudiante | Correo | Facultad | Biografía |
|---|---|---|---|---|---|
| 1 | Juan León | 2024-0001 | juan.leon@example.com | Ingeniería y Tecnología | Estudiante destacado con interés en desarrollo Android. |
| 2 | María García | 2024-0002 | maria.garcia@example.com | Arquitectura y Urbanismo | Apasionada por el diseño sostenible y la arquitectura moderna. |
| 3 | Carlos Perez | 2024-0003 | carlos.perez@example.com | Ciencias de la Salud | Interesado en investigación clínica y atención primaria. |
| 4 | Ana Lopez | 2024-0004 | ana.lopez@example.com | Derecho y Ciencias Políticas | Enfocada en derecho corporativo y resolución de conflictos. |
| 5 | Luis Ramírez | 2024-0005 | luis.ramirez@example.com | Ciencias Empresariales | Interesado en gestión de proyectos y liderazgo de equipos. |

NO generar datos aleatorios. Usar exactamente esta tabla.

---

### 26. PROFILE SCREEN
Crear:
`ProfileScreen`

TopAppBar:
**Configuración de Perfil**

Flecha:
`ArrowBack`, usando `navController.popBackStack()`

Esta pantalla siempre muestra los datos fijos de Juan León Suiyon (no depende de navegación con studentId).

---

### 27. CABECERA DEL PERFIL
Utilizar:
- fondo: degradado vertical `Brush.verticalGradient(listOf(Primary, PrimaryDark))` (`#6F4FB3` → `#5E3FA0`), esquinas inferiores redondeadas (24.dp aprox.), igual estilo que la cabecera del Expediente.

Avatar:

- CircleShape, 96.dp
- fondo `#E9DFFF` (Lavender)
- icono Person color Primary

Texto:
**Juan León Suiyon**
20.sp, Bold, blanco, centrado debajo del avatar.

---

### 28. INFORMACIÓN PERSONAL
Título de sección:
**INFORMACIÓN PERSONAL**
(12.sp, Bold, color TextSecondary, letter-spacing amplio, en mayúsculas)

Mostrar, cada una con su icono (Person, Email, Phone) y misma estructura etiqueta/valor que en el Expediente:

Nombre Completo
Juan León Suiyon

Correo
juan.leon@tecsup.edu.pe

Teléfono
+51 987 654 321

---

### 29. INFORMACIÓN ACADÉMICA
Título de sección:
**ACADÉMICO**
(mismo estilo que el título anterior)

Mostrar, con iconos (School, CalendarMonth):

Carrera
Ingeniería de Software

Ciclo Actual
VI Ciclo

---

### 30. BOTÓN CERRAR SESIÓN (EN PERFIL)
Texto exacto:
**Cerrar Sesión**

Características:

- ancho completo
- altura 52.dp
- esquinas 16.dp
- icono Logout a la izquierda del texto
- fondo `#F6DAD8` (LogoutBg)
- texto y icono `#C96D69` (LogoutText)
- FontWeight.Bold
- ubicado al final del contenido de la pantalla (dentro del scroll o fijo abajo, como en la captura)

Al pulsarlo:
navegar a `login` usando `popUpTo("home") { inclusive = true }` (o la ruta raíz del grafo que corresponda) para limpiar el back stack.

**Nota:** este botón es distinto al enlace de texto "Cerrar Sesión Segura" de la Home (sección 15). No los unifiques.

---

### 31. PALETA EXACTA
Crear estos colores en:
`Color.kt`

Utilizar:

```kotlin
val Primary          = Color(0xFF6F4FB3)
val PrimaryDark      = Color(0xFF5E3FA0)
val Lavender         = Color(0xFFE9DFFF)
val LightLavender    = Color(0xFFF6F0FF)
val Background       = Color(0xFFFCF8FF)
val White            = Color(0xFFFFFFFF)
val TextPrimary      = Color(0xFF2C2633)
val TextSecondary    = Color(0xFF756B7F)
val LogoutBg         = Color(0xFFF6DAD8)
val LogoutText       = Color(0xFFC96D69)
```

**NO utilizar Dynamic Color.**
**NO utilizar colores predeterminados de Android Studio.**
**NO generar colores automáticamente.**

La aplicación debe tener exactamente esta identidad visual, en el `MaterialTheme` (colorScheme) definido en `Theme.kt`, con `dynamicColor = false` fijo (sin condicional por versión de Android).

---

### 32. TIPOGRAFÍA FIJA
Utilizar Material 3 Typography.
No utilizar fuentes externas.
No descargar fuentes.
Utilizar la fuente predeterminada del sistema (`FontFamily.Default`).

Valores principales:

- Título principal: 28.sp
- Título secundario: 24.sp
- Título de tarjeta: 18.sp
- Texto: 14.sp
- Texto pequeño: 12.sp

No modificar estos tamaños arbitrariamente.

---

### 33. ESPACIADO FIJO
Utilizar esta escala:

4.dp · 8.dp · 12.dp · 16.dp · 20.dp · 24.dp · 32.dp

Evitar valores diferentes salvo necesidad real.

---

### 34. FORMA DE COMPONENTES
- Tarjetas: 16.dp
- Tarjetas principales / Login card: 20.dp
- Botones: 16.dp
- Campos de texto (Login): 12.dp
- Avatares: CircleShape

Mantener consistencia.

---

### 35. NO USAR DISEÑO DEPENDIENTE DEL DISPOSITIVO
NO cambiar la interfaz dependiendo de:

- tamaño del teléfono
- fabricante
- densidad
- orientación

No crear versiones diferentes de la interfaz.

Utilizar:

- dp
- sp
- fillMaxWidth
- fillMaxSize
- padding

La misma implementación debe utilizarse en todas las computadoras.

---

### 36. NO USAR RANDOM
NO utilizar:

`Random`

para:

- colores
- nombres
- datos
- posiciones
- estudiantes
- tamaños

Todo debe ser fijo, incluida la asignación de color de avatar (sección 19, basada en `studentId % 3`, no en `Random`).

---

### 37. NO USAR FECHA/HORA DEL SISTEMA PARA EL DISEÑO
No utilizar la fecha u hora actual para generar contenido.
El resultado debe ser el mismo en cualquier momento.

---

### 38. NO USAR INFORMACIÓN DEL SISTEMA PARA CAMBIAR EL DISEÑO
No cambiar colores o estructura dependiendo de:

- versión de Android
- Dynamic Color
- fabricante
- tema del dispositivo

La aplicación debe utilizar su propia identidad visual.

---

### 39. MODO OSCURO
La prioridad es:
**Light Theme**

NO es necesario crear una interfaz visual diferente para Dark Mode.

El proyecto base tiene Dynamic Color activado por defecto (`dynamicColor: Boolean = true` en la firma de `NavLabTheme`, en `Theme.kt`):
**cambiar ese valor por defecto a `false`.** No dejar el parámetro en `true` ni condicionarlo por versión de Android.

La prioridad es que el diseño sea igual en todos los dispositivos.

---

### 40. COMPONENTES REUTILIZABLES
Puedes crear:

- StudentAvatar (avatar circular reutilizable, sección 19)
- StudentCard
- MenuOptionCard (tarjeta de Directorio/Perfil en Home)
- ProfileInfoRow / AcademicInfoRow (etiqueta + valor + icono)

pero mantén la arquitectura sencilla.
No agregar arquitecturas complejas (no ViewModel/Hilt/Repository con capas innecesarias salvo que el proyecto base ya las use).

---

### 41. ARCHIVOS ANTIGUOS
El proyecto base tiene pantallas existentes.

NO las elimines inmediatamente.
Primero revisa sus referencias.

Si pueden reutilizarse:
reutilízalas.

Si deben cambiar de función:
modifícalas.

Si dejan de ser necesarias:
elimina sus referencias y luego elimina el archivo solamente si es seguro.

No dejar código muerto.

---

### 42. NO DEJAR DOS SISTEMAS DE NAVEGACIÓN
Debe existir un único sistema de navegación.
El flujo debe estar centralizado en:
`AppNavigation`

No crear otro NavHost.
No crear otro NavController innecesariamente.

---

### 43. MAIN ACTIVITY
Debe permanecer conceptualmente:

```kotlin
setContent {
    NavLabTheme {          // o el nombre real del Theme del proyecto
        AppNavigation()
    }
}
```

Adaptándolo al nombre real del Theme del proyecto.
No colocar todas las pantallas dentro de MainActivity.

---

### 44. RESPONSIVE
Las pantallas deben funcionar en teléfonos pequeños y grandes.

Si una pantalla tiene mucho contenido:
usar:

- verticalScroll
  o
- LazyColumn

No permitir contenido cortado. Aplica especialmente a Expediente Académico y Configuración de Perfil, que tienen varias secciones.

---

### 45. VERIFICACIÓN DE NAVEGACIÓN
Probar exactamente:

Login → Home → Directory → AcademicRecord

y

Home → Profile

Además:

AcademicRecord → Back → Directory
Directory → Back → Home
Profile → Back → Home

Cerrar sesión:
Home (enlace "Cerrar Sesión Segura") → Login
Profile (botón "Cerrar Sesión") → Login

En ambos casos, verificar que el botón Atrás desde Login ya no regresa a Home/Profile.

---

### 46. VERIFICACIÓN DE LOS CINCO ALUMNOS
Comprobar:

1 → Juan León
2 → María García
3 → Carlos Perez
4 → Ana Lopez
5 → Luis Ramírez

Todos deben abrir correctamente su expediente, con los datos fijos de la tabla de la sección 25 y el color de avatar de la tabla de la sección 19.

---

### 47. COMPILACIÓN
Después de modificar el proyecto:

- Gradle Sync.
- Build.
- Corregir errores.
- Build nuevamente.
- Revisar imports.
- Revisar navegación.
- Revisar argumentos.
- Revisar recursos.

**NO terminar si existen errores de compilación.**

---

### 48. ERRORES
Si aparece un error:

NO me preguntes inmediatamente.

Primero:

- lee el error;
- identifica la causa;
- modifica el código;
- vuelve a compilar;
- verifica.

No hagas cambios innecesarios al proyecto para solucionar un error.

---

### 49. NO CAMBIAR FUNCIONALIDADES SOLICITADAS
No agregar:

- Firebase
- Room
- Retrofit
- API
- base de datos
- backend
- autenticación
- servicios externos
- imágenes
- Internet

El proyecto debe funcionar localmente.

---

### 50. REGLA DE IDENTIDAD VISUAL
**ESTA REGLA ES OBLIGATORIA.**

Las cinco pantallas deben parecer hechas por el mismo diseñador.

Todas deben compartir:

- misma paleta
- mismos radios
- mismos botones
- misma tipografía
- mismos iconos
- mismo fondo base (Background `#FCF8FF` donde no se especifique degradado)
- mismos espaciados
- misma estética

**NO diseñes una pantalla con un estilo diferente.**

---

### 51. NO INTERPRETAR EL DISEÑO
NO reemplaces los colores especificados por otros.
NO reemplaces los textos especificados por otros.
NO agregues gradientes donde no fueron especificados (solo Home y cabecera de Perfil los usan; Login, Directory, Expediente y el resto de Perfil usan fondo/colores sólidos).
NO agregues animaciones innecesarias.
NO agregues imágenes ni fotografías.
NO agregues elementos decorativos innecesarios.
NO agregues funcionalidades que no fueron solicitadas.

Si una instrucción está especificada aquí:
**DEBES SEGUIRLA.**

---

### 52. REPRODUCCIÓN EN OTRAS COMPUTADORAS
Este mismo prompt será ejecutado nuevamente sobre el mismo proyecto base en otras computadoras.

Por lo tanto:

- NO utilizar rutas absolutas.
- NO utilizar archivos externos.
- NO utilizar configuraciones específicas de una computadora.
- NO utilizar variables de entorno para el diseño.
- NO utilizar archivos que no estén dentro del proyecto.
- NO depender de servicios externos.
- NO depender de Internet para la interfaz.
- NO utilizar imágenes externas.
- NO utilizar datos generados dinámicamente.
- NO utilizar Random.
- NO utilizar Dynamic Color.
- NO utilizar contenido dependiente de la fecha.
- NO utilizar contenido dependiente del dispositivo.

El proyecto debe ser autónomo.

---

### 53. CONSISTENCIA DEL CÓDIGO
Si el mismo proyecto base y este mismo prompt se ejecutan en otra computadora:
debe producirse:

- misma estructura
- mismos archivos funcionales
- mismas rutas
- mismos textos
- mismos colores
- mismos tamaños
- mismos datos
- misma navegación
- mismos componentes
- mismo comportamiento

No debes introducir cambios arbitrarios.

---

### 54. RESULTADO VISUAL
El resultado final debe tener esta estructura general (y verse como las capturas, con avatares de color+icono en vez de fotos):

**LOGIN:**
Portal Académico
Accede a tu cuenta

Correo Institucional
Contraseña

INICIAR SESIÓN

¿Olvidaste tu contraseña?

**HOME:**
Bienvenido,
Juan León

¿Qué deseas gestionar hoy?

[ Directorio de Alumnos ]
Ver y gestionar estudiantes

[ Mi Perfil Académico ]
Datos personales y progreso

Cerrar Sesión Segura

**DIRECTORIO:**
← Directorio de Alumnos

[ ● Juan León              > ]
Ingeniería de Sistemas

[ ● María García           > ]
Arquitectura

[ ● Carlos Perez           > ]
Medicina

[ ● Ana Lopez              > ]
Derecho

[ ● Luis Ramírez           > ]
Administración

**EXPEDIENTE:**
← Expediente Académico

        ●
    Juan León
Ingeniería de Sistemas

ID Estudiante
2024-0001

Correo Electrónico
juan.leon@example.com

Facultad
Ingeniería y Tecnología

Biografía
Estudiante destacado con interés
en desarrollo Android.

**PERFIL:**
← Configuración de Perfil

        ●
Juan León Suiyon

INFORMACIÓN PERSONAL

Nombre Completo
Juan León Suiyon

Correo
juan.leon@tecsup.edu.pe

Teléfono
+51 987 654 321

ACADÉMICO

Carrera
Ingeniería de Software

Ciclo Actual
VI Ciclo

[ Cerrar Sesión ]

---

### 55. CRITERIO DE FINALIZACIÓN
NO consideres terminado el trabajo solamente porque el código compile.

Debe cumplirse **TODO**:

[ ] El proyecto existente fue utilizado.
[ ] No se creó otro proyecto.
[ ] Se conservó el package.
[ ] Se conservó la tecnología.
[ ] Se conservó la infraestructura de Gradle.
[ ] Login funciona.
[ ] Home funciona (con fondo degradado y enlace "Cerrar Sesión Segura").
[ ] Directory funciona.
[ ] AcademicRecord funciona.
[ ] Profile funciona (con botón "Cerrar Sesión").
[ ] Los cinco alumnos funcionan.
[ ] studentId funciona como Int.
[ ] Navigation funciona.
[ ] Back funciona.
[ ] Logout funciona (desde Home y desde Profile).
[ ] Back stack funciona.
[ ] No existen imágenes ni fotografías.
[ ] No existen recursos externos.
[ ] No existe Dynamic Color.
[ ] No existe Random.
[ ] No existen datos dinámicos.
[ ] Colores son los especificados.
[ ] Textos son los especificados (incluyendo "Cerrar Sesión Segura" en Home vs "Cerrar Sesión" en Perfil).
[ ] Tamaños son los especificados.
[ ] Espaciados son consistentes.
[ ] Colores de avatar siguen la tabla `studentId % 3` de la sección 19.
[ ] El proyecto compila.
[ ] La aplicación puede ejecutarse.
[ ] El resultado visual se parece a las capturas proporcionadas (sin fotos, con círculos de color + icono).

---

### 56. RESUMEN FINAL
Cuando hayas terminado:
**NO me entregues una explicación extensa.**

Responde únicamente con:

```
ARCHIVOS MODIFICADOS
Lista.

ARCHIVOS CREADOS
Lista.

RUTAS
Lista.

FUNCIONALIDADES
Lista breve.

COMPILACIÓN
Indicar si compiló correctamente.
Si tuviste que corregir errores, indicar cuáles fueron brevemente.
```

---

### 57. INSTRUCCIÓN FINAL
**COMIENZA AHORA.**

INSPECCIONA EL PROYECTO.
IDENTIFICA SU ESTRUCTURA.
IDENTIFICA SU NAVEGACIÓN ACTUAL.
IDENTIFICA SU PACKAGE.

MODIFICA EL PROYECTO EXISTENTE.
IMPLEMENTA LAS CINCO PANTALLAS.
IMPLEMENTA LA NAVEGACIÓN.
IMPLEMENTA EL DISEÑO EXACTAMENTE SEGÚN ESTE PROMPT Y LAS CAPTURAS (avatares como círculo de color + icono, NUNCA fotos).

NO UTILICES IMÁGENES.
NO UTILICES INTERNET.
NO UTILICES DATOS ALEATORIOS.
NO UTILICES DYNAMIC COLOR.
NO CAMBIES EL PACKAGE.
NO CREES OTRO PROYECTO.

COMPILA.
CORRIGE LOS ERRORES.
VUELVE A COMPILAR.
VERIFICA TODO EL FLUJO.

DEJA EL PROYECTO FUNCIONANDO.

**EL MISMO PROMPT DEBE PODER EJECUTARSE SOBRE EL MISMO PROYECTO BASE EN MÚLTIPLES COMPUTADORAS Y PRODUCIR EL MISMO RESULTADO.**

NO IMPROVISES.
NO INTERPRETES.
SIGUE LA ESPECIFICACIÓN.

**FIN DEL PROMPT.**